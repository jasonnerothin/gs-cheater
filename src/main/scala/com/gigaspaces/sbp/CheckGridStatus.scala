package com.gigaspaces.sbp

import com.gigaspaces.cluster.activeelection.SpaceMode
import net.jini.core.lookup.ServiceID
import org.openspaces.admin.Admin
import org.openspaces.admin.machine.Machine
import org.openspaces.admin.pu.{ProcessingUnit, ProcessingUnitInstance, ProcessingUnitPartition, ProcessingUnitType}
import org.openspaces.admin.space.SpaceInstance

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 8/10/14
 * Time: 6:14 AM
 */
class CheckGridStatus extends AdminCreation {

  override implicit val settings: RebalanceSettings = new Object with RebalanceSettings
  lazy val admin: Admin = createAdmin()

  /**
   * @return contacts the grid for metadata about [[ProcessingUnit]]s that
   *         have the [[ProcessingUnitType.STATEFUL]] type
   */
  def checkStatefulPus(): Seq[SpaceInstanceInfo] = {

    val units = admin.getProcessingUnits
    val pus = units.getProcessingUnits

    statefulPuInstances(pus).map {
      statefulPuInfo
    }

  }

  private def statefulPuInstances(pus: Array[ProcessingUnit]): Seq[ProcessingUnitInstance] = {
    pus.flatMap { pu =>
      pu.getType match {
        case ProcessingUnitType.STATEFUL => pu.getInstances
        case _ => List.empty[ProcessingUnitInstance]
      }
    }
  }

  /**
   * @return two groups of meta-data about [[ProcessingUnitType.STATEFUL]] [[ProcessingUnit]]s:
   *         those with [[SpaceMode.PRIMARY]] are on the left, all others are on the right
   */
  def primariesAndBackups(): (Seq[SpaceInstanceInfo], Seq[SpaceInstanceInfo]) = {

    checkStatefulPus partition {
      _.spaceMode == SpaceMode.PRIMARY
    }

  }

  def statefulPuInfo(inst: ProcessingUnitInstance): SpaceInstanceInfo = {

    val spaceInstance: SpaceInstance = inst.getSpaceInstance
    val spaceInstanceId = spaceInstance.getInstanceId
    val spaceInstanceName = spaceInstance.getSpaceInstanceName

    val backupId = magicIntToOption(spaceInstance.getBackupId)
    val spaceMode: SpaceMode = spaceInstance.getMode

    val machine: Machine = spaceInstance.getMachine
    val hostname = machine.getHostName
    val hostAddress = machine.getHostAddress

    val details = inst.getEmbeddedSpaceDetails
    val puInstanceName = inst.getProcessingUnitInstanceName
    val puName = details.getName
    val puServiceId = details.getServiceID match {
      case null => "N/A"
      case s: ServiceID => s.toString
    }

    val partitionId: Option[Int] = noneIfNull(inst.getPartition) match {
      case Some(p: ProcessingUnitPartition) => magicIntToOption(p.getPartitionId)
      case None => Some(0)
    }

    val info = SpaceInstanceInfo(
      puName, puServiceId, puInstanceName,
      spaceInstanceId, spaceInstanceName, spaceMode,
      partitionId, backupId,
      hostname, hostAddress
    )

    info.spaceMode match {
      case SpaceMode.NONE =>
        println(lineOfX)
        val msg = s"Stateful PU is not a primary or a backup:\n\n $info "
        println(msg)
        logger.error(msg)
        println(lineOfX)
      case _ =>
    }

    info

  }

  /**
   * @return metadata about Hosts, including primary and backup information
   */
  def checkHosts(): Seq[Host] = {
    val machines = admin.getMachines.getMachines
    val puInstances = machines flatMap {
      _.getProcessingUnitInstances
    }
    val spaceInstances = puInstances flatMap {
      _.getSpaceInstances
    }
    machines.map(machine =>
      new Host(
        name = machine.getHostName,
        numGscs = machine.getGridServiceContainers.getSize,
        primaryAndBackupSpaceInstanceIds(machine)._1,
        primaryAndBackupSpaceInstanceIds(machine)._2
      )
    )

  }

  private def primaryAndBackupSpaceInstanceIds(machine: Machine): (Set[Int], Set[Int]) = {

    val pus = machine.getProcessingUnitInstances.map(_.getProcessingUnit)
    val statefulSpaceInstances = statefulPuInstances(pus).flatMap(spu=>spu.getSpaceInstances)
    val primaries = statefulSpaceInstances.filter(si=>si.getMode == SpaceMode.PRIMARY).map(_.getInstanceId)
    val backups = statefulSpaceInstances.filter(si=>si.getMode == SpaceMode.BACKUP).map(_.getInstanceId)

    (primaries.toSet, backups.toSet)
  }

}