package com.gigaspaces.sbp

import java.text.SimpleDateFormat

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 8/8/14
 * Time: 1:45 PM
 */
@deprecated("code was copy-pasted in from https://github.com/jasonnerothin/gs-rebalance and is hanging around only for reference purposes.")
trait RebalanceSettings {

  val defaultSettings = DefaultSettings

  def terminateOnAdminCreationError(): Boolean = defaultSettings.terminateOnAdminCreationError

  def spaceName(): String = defaultSettings.spaceName

  def useSecurity(): Boolean = defaultSettings.useSecurity

  def user(): Future[String] = Future{""}

  def password(): Future[String] = Future{""}

  def lookupGroups(): String = defaultSettings.lookupGroups

  def lookupLocators(): String = defaultSettings.lookupLocators

  def deployFolderPath() : String = defaultSettings.deployFolderPath

  def checkPeriodMillis(): Long = defaultSettings.checkPeriodMillis

  def dateFormat:SimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")

}

@deprecated("code was copy-pasted in from https://github.com/jasonnerothin/gs-rebalance and is hanging around only for reference purposes.")
object HardCodedSettings extends RebalanceSettings

@deprecated("code was copy-pasted in from https://github.com/jasonnerothin/gs-rebalance and is hanging around only for reference purposes.")
object DefaultSettings extends DefaultSettings

@deprecated("code was copy-pasted in from https://github.com/jasonnerothin/gs-rebalance and is hanging around only for reference purposes.")
trait DefaultSettings{

  val checkPeriodMillis = 2500L
  val terminateOnAdminCreationError = true
  val spaceName = "space"
  val useSecurity = false
  val lookupGroups: String = ""
  val lookupLocators = "localhost:4174"
  val deployFolderPath = "/tmp/"

}