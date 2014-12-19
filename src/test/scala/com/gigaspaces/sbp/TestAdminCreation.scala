package com.gigaspaces.sbp

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 8/9/14
 * Time: 3:11 PM
 *
 * Provides a tool to do an end-to-end integration (connection) test against
 * a running grid.
 */
class TestAdminCreation extends App {

  val justAboutForever = Long.MaxValue

  val settings: RebalanceSettings = new Object with RebalanceSettings

  val creation = new Object with AdminCreation{
    override val settings:RebalanceSettings = TestAdminCreation.this.settings
  }

  def now():Long = System.currentTimeMillis()

  val t0 = now()
  println("T0: 0")

  val admin = creation.createAdmin()

  val t1 = now() - t0
  println("T1: $t1")

  val f = Future{
    Thread.sleep(justAboutForever)
  }

  val t2 = now() - t0
  println("T2: $t2")

  println("done")

}
