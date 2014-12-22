package com.gigaspaces.sbp

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 8/11/14
 * Time: 11:12 AM
 */
@deprecated("code was copy-pasted in from https://github.com/jasonnerothin/gs-rebalance and is hanging around only for reference purposes.")
object Global{
  object Implicits {
    implicit val settings: RebalanceSettings = HardCodedSettings
  }
}
