package com.gigaspaces.sbp

import java.util.concurrent.TimeUnit

import org.openspaces.admin.gsc.GridServiceContainers
import org.openspaces.admin.machine.Machines
import org.openspaces.admin.space.Spaces
import org.openspaces.admin.{Admin, AdminFactory}
import org.slf4s.Logging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 8/8/14
 * Time: 1:44 PM
 */
@deprecated("code was copy-pasted in from https://github.com/jasonnerothin/gs-rebalance and is hanging around only for reference purposes.")
trait AdminCreation extends Logging{

  private val startupFailureMessage = "ERROR starting up admin api."

  private val gscWaitPatienceMs = 500L
  private val numGscsToWaitFor = 1
  private val numMachinesToWaitFor = 1

  implicit val settings: RebalanceSettings

  def createAdmin(): Admin = {

    val factory = new AdminFactory
    factory.addLocators(settings.lookupLocators())
    factory.addLocator(settings.lookupLocators())
    factory.addGroups(settings.lookupGroups())

    factory.discoverUnmanagedSpaces
    val admin = factory.create

    val startup = for {
      m <- machines(admin)
      g <- gscs(admin)
      s <- space(admin)
    } yield (m, g, s)

    startup onFailure {
      case exc: Exception =>
        exc.printStackTrace()
        squawkAndDie()
      case _ =>
        squawkAndDie()
    }

    admin

  }

  private[this] def printAndError(msg: String): Unit = {
    println(msg)
    log.error(msg)
  }

  private def squawkAndDie(): Unit = {

    printAndError(lineOfX)
    printAndError(startupFailureMessage)
    printAndError(settings.toString)
    printAndError(lineOfX)

    if (settings.terminateOnAdminCreationError()) {
      printAndError("ERROR: Terminating...")
      System.exit(666)
    }

  }

  private def machines(admin: Admin) = Future[Machines] {
    val m = admin.getMachines
    m.waitFor(numMachinesToWaitFor)
    m
  }

  private def gscs(admin: Admin) = Future[GridServiceContainers] {
    val containers = admin.getGridServiceContainers
    containers.waitFor(numGscsToWaitFor, gscWaitPatienceMs, TimeUnit.MILLISECONDS)
    containers
  }

  private def space(admin: Admin): Future[Spaces] = {
    Future {
      val spaces = admin.getSpaces
      spaces.waitFor(settings.spaceName())
      spaces
    }
  }

}