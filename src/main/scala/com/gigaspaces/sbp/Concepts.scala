package com.gigaspaces.sbp

import com.gigaspaces.cluster.activeelection.SpaceMode

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 8/10/14
 * Time: 8:53 AM
 */
@deprecated("code was copy-pasted in from https://github.com/jasonnerothin/gs-rebalance and is hanging around only for reference purposes.")
case class Host(name: String, numGscs: Int, primaryIds: Set[Int], backupIds: Set[Int]){
  val numBackups = backupIds.size
  val numPrimaries = primaryIds.size
  val hasBackup = numBackups > 0
  def hasPrimary(spaceInstanceId: Int): Boolean = primaryIds.contains(spaceInstanceId)
  def hasBackup(spaceInstanceId: Int): Boolean = backupIds.contains(spaceInstanceId)
}

@deprecated("code was copy-pasted in from https://github.com/jasonnerothin/gs-rebalance and is hanging around only for reference purposes.")
case class RelocationOperation(toMove: SpaceInstanceInfo, targetHost: Host)

@deprecated("code was copy-pasted in from https://github.com/jasonnerothin/gs-rebalance and is hanging around only for reference purposes.")
case class SpaceInstanceInfo(puName: String, puServiceId: String, puInstanceName: String,
                             spaceInstanceId: Int, spaceInstanceName: String, spaceMode: SpaceMode,
                             partitionId: Option[Int], backupId: Option[Int],
                             hostname: String, hostAddress: String)