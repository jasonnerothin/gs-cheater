package com.gigaspaces.sbp

import Global.Implicits._
import scala.language.postfixOps

// this class is excessive, can be substituted by main method in Rebalance
object RebalanceRunner {

  def main(args: Array[String]) {
    println("REBALANCE STARTED")
    val main = new Rebalance(new CheckGridStatus)
    main.check()
    while (main.unbalanced()) {

      val rebalanceOps = main.rebalanceOperations()
      val puMoveOps = main.puMoveOperations()

      main.canSafelyRebalance(rebalanceOps, puMoveOps) match {
        case true => main.rebalance(rebalanceOps, puMoveOps)
        case false => main.cannotRebalance()
      }

      main.timeout()
      main.check()
    }
    println(main.successfulRebalanceMessage)
  }

}
