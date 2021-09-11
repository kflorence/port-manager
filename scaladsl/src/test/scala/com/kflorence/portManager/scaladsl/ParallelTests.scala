package com.kflorence.portManager.scaladsl

import org.scalatest.{BeforeAndAfterAll, ParallelTestExecution}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable

class ParallelTests extends AnyFlatSpec with BeforeAndAfterAll with ParallelTestExecution with Matchers {
  val portManager: StaticPortManager.Ephemeral.type = StaticPortManager.Ephemeral

  var claimed: mutable.Seq[Int] = mutable.Seq[Int]()

  (1 to 100).foreach { n =>
    "port manager" should s"take port $n" in {
      val _ = claimed :+ portManager.claim()
      succeed
    }
  }

  override def afterAll(): Unit = {
    super.afterAll()
    if (claimed.distinct.length != claimed.length) fail()
  }
}
