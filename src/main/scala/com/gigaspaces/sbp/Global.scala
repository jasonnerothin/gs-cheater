package com.gigaspaces.sbp

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 8/11/14
 * Time: 11:12 AM
 */
object Global{
  object Implicits {
    implicit val settings: RebalanceSettings = HardCodedSettings
  }
}
