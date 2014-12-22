package com.gigaspaces

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 8/10/14
 * Time: 8:47 AM
 */
package object sbp {

  @deprecated("code was copy-pasted in from https://github.com/jasonnerothin/gs-rebalance and is hanging around only for reference purposes.")
  val lineOfX: String = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"

  @deprecated("code was copy-pasted in from https://github.com/jasonnerothin/gs-rebalance and is hanging around only for reference purposes.")
  private val magicIntsStopAt = 0

  /**
   * Turns numbers that fail a magic number test (i.e. <0) into None.
   * For usage against legacy APIs that may or may not use primitive
   * ints to represent Natural numbers.
   *
   * @param i to test
   * @return an Option[Int]
   */
  @deprecated("code was copy-pasted in from https://github.com/jasonnerothin/gs-rebalance and is hanging around only for reference purposes.")
  def magicIntToOption(i: Int): Option[Int] = {
    if (i >= magicIntsStopAt) Some(i)
    else None
  }

  /**
   * Turns nulls into None, others into Some(T)
   * @param obj a T
   * @tparam T any old Object
   * @return an Option[T]
   */
  @deprecated("code was copy-pasted in from https://github.com/jasonnerothin/gs-rebalance and is hanging around only for reference purposes.")
  def noneIfNull[T](obj: T): Option[T] = {
    if (obj == null) None
    else Some(obj)
  }

}