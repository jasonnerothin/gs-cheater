package com.gigaspaces.sbp

import java.util.Date

import org.slf4j.{LoggerFactory, Logger}

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import Global.Implicits._
import scala.language.postfixOps

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 8/7/14
 * Time: 10:04 PM
 * Provides a rebalance script, as defined in the README file.
 */
class Rebalance(gridStatus: CheckGridStatus) {

  private[Rebalance] val logger: Logger = LoggerFactory.getLogger(classOf[Rebalance])

  val successfulRebalanceMessage = "System has been successfully rebalanced."
  val cannotRebalanceMessages = Array(
    "ERROR: Cannot calculate a strategy to move processing units - safely - from host to host.",
    ">>>>>: Administrative action must be taken to forcefully fail-over primary partitions. :<<<<<"
  )

  var primariesAndBackups: (Seq[SpaceInstanceInfo], Seq[SpaceInstanceInfo]) = null
  var hosts: Seq[Host] = null
  var rebalanceCount = 1

  def addAndGetRebalanceCount(): Int = {
    rebalanceCount = rebalanceCount + 1
    rebalanceCount
  }

  /**
   * Updates local state by connecting to grid through Admin API
   */
  def check(): Unit = {
    hosts = gridStatus.checkHosts()
    primariesAndBackups = gridStatus.primariesAndBackups()
  }

  def minimumNumberOfPrimariesOnAGridHost(): Int = hosts.map(_.numPrimaries).reduceLeft(_ min _)

  def maximumNumberOfPrimariesOnAGridHost(): Int = hosts.map(_.numPrimaries).reduceLeft(_ max _)

  /**
   * @return hosts that are running with the maximum number of primaries
   */
  def maxHosts(): Traversable[Host] = hosts.filter(h => h.numPrimaries == maximumNumberOfPrimariesOnAGridHost())

  /**
   * @return hosts that are running with the minimum number of primaries
   */
  def minHosts(): Traversable[Host] = hosts.filter(h => h.numPrimaries == maximumNumberOfPrimariesOnAGridHost())

  /**
   * @return hosts that are running with neither the minimum, nor the maximum number of primaries
   */
  def aHosts(): Traversable[Host] = hosts.filter(h => h.numPrimaries < maximumNumberOfPrimariesOnAGridHost() && h.numPrimaries > minimumNumberOfPrimariesOnAGridHost())

  implicit class Crossable[X](xs: Traversable[X]) {
    def cross[Y](ys: Traversable[Y]) = for {x <- xs; y <- ys} yield (x, y)
  }

  /**
   * Computes all possible PU moves that will move a primary from the hosts
   * that have the MOST primaries to ones that have the least primaries (and
   * do not already host a conflicting backup.
   * @return such a group
   */
  def rebalanceOperations(): Traversable[RelocationOperation] = {
    for {
    // from all primary/backup pairs
      primary <- primariesAndBackups._1
      backup <- primariesAndBackups._2 if backup.partitionId == primary.partitionId
      // move from a max instance to a min instance
      max <- maxHosts() if max.hasPrimary(primary.spaceInstanceId)
      min <- minHosts() if !min.hasBackup(backup.spaceInstanceId)
    } yield RelocationOperation(primary, min)
  }

  /**
   * Computes all possible PU moves that will move a primary from the hosts
   * that have NEITHER the nor the least primaries to ones that have the least
   * primaries (and do not already host a conflicting backup.
   * @return such a group
   */
  def puMoveOperations(): Traversable[RelocationOperation] = {
    for {
    // from all primary/backup pairs
      primary <- primariesAndBackups._1
      backup <- primariesAndBackups._2 if backup.partitionId == primary.partitionId
      // move from any (non-max, non-min) host to a min host
      host <- aHosts() if host.hasPrimary(primary.spaceInstanceId)
      min <- minHosts() if !min.hasBackup(backup.spaceInstanceId)
    } yield RelocationOperation(primary, min)
  }

  /**
   * @return if the current state of the grid is unbalanced:
   */
  def unbalanced(): Boolean = {
    val max = maximumNumberOfPrimariesOnAGridHost()
    val min = minimumNumberOfPrimariesOnAGridHost()
    max > min + 1
  }

  /**
   * It is not, in general, possible that an "unbalanced" grid can always be "safely" rebalanced.
   * Assume a 2,1 topology where max-instances-per-vm is 1 and that both primaries are on one host
   * and both backups are on another host.
   *
   * In this case, the number of possible rebalance operations is zero and the number of possible
   * move operations is zero.
   *
   * @param rebalanceOps all possible rebalance operations for the current grid
   * @param moveOps all possible move operations for the current grid
   * @return
   */
  def canSafelyRebalance(rebalanceOps: Traversable[RelocationOperation], moveOps: Traversable[RelocationOperation]): Boolean = {
    rebalanceOps.size + moveOps.size > 0
  }

  def rebalance(rebalanceOperations: Traversable[RelocationOperation], moveOperations: Traversable[RelocationOperation])(implicit settings: RebalanceSettings) = {
    val now = settings.dateFormat.format(new Date(System.currentTimeMillis))
    val rc = addAndGetRebalanceCount()
    s"Beginning rebalance #%{$rc}d at $now."
    for (i <- 0 to 10) timeout()
    s"Rebalance #%{$rebalanceCount}d completed at $now."
  }

  /**
   * Sleep for the interval specified in settings
   * @param settings app settings
   * @return not much
   */
  def timeout()(implicit settings: RebalanceSettings): Unit = {
    val timeoutMs = settings.checkPeriodMillis()
    val f = Future {
      Thread.sleep(timeoutMs)
    }
    Await.result(f, timeoutMs millis)
  }

  /**
   * Emit error messaging.
   */
  def cannotRebalance(): Unit = {
    println(lineOfX)
    logger.error(lineOfX)
    cannotRebalanceMessages foreach println
    cannotRebalanceMessages foreach logger.error
    println(lineOfX)
    logger.error(lineOfX)
  }

}