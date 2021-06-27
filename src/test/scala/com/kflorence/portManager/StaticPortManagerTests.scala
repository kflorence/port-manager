package com.kflorence.portManager

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class StaticPortManagerTests extends AnyWordSpec with Matchers {
  "getAvailable" should {
    "return the available ports" in {
      val portManager = StaticPortManager(1 to 3)
      portManager.getAvailable should be(Set(1, 2, 3))
      portManager.getAndReserveNextAvailable
      portManager.getAvailable should be(Set(2, 3))
    }
  }
  "isAvailable" should {
    "throw IndexOutOfBoundsException if range starts outside of bounds" in {
      an[IndexOutOfBoundsException] should be thrownBy StaticPortManager(1 to 3).isAvailable(0 to 1)
    }
    "throw IndexOutOfBoundsException if range ends outside of bounds" in {
      an[IndexOutOfBoundsException] should be thrownBy StaticPortManager(1 to 3).isAvailable(3 to 4)
    }
    "return false if range includes port that is already taken" in {
      val portManager = StaticPortManager(1 to 3)
      portManager.reserve(1 to 1)
      portManager.isAvailable(1 to 2) should be(false)
    }
    "return true if entire range is available" in {
      StaticPortManager(1 to 3).isAvailable(1 to 3) should be(true)
    }
  }
  "getAndReserveNextAvailable" should {
    "choose the lowest still-available port number within bounds" in {
      val portManager = StaticPortManager(1 to 3)
      portManager.getAndReserveNextAvailable should be(1)
      portManager.reserve(2 to 2)
      portManager.getAndReserveNextAvailable should be(3)
    }
    "throw IndexOutOfBoundsException if there are no available ports" in {
      val portManager = StaticPortManager(1 to 1)
      portManager.reserve(1 to 1)
      an[IndexOutOfBoundsException] should be thrownBy portManager.getAndReserveNextAvailable
    }
  }
  "reserve" should {
    "throw an IllegalArgumentException if the range includes an already reserved port" in {
      val portManager = StaticPortManager(1 to 3)
      portManager.reserve(2 to 2)
      an[IllegalArgumentException] should be thrownBy portManager.reserve(1 to 3)
    }
    "throw an IndexOutOfBoundsException if the range is out of bounds" in {
      val portManager = StaticPortManager(1 to 3)
      an[IndexOutOfBoundsException] should be thrownBy portManager.reserve(0 to 4)
    }
    "reserve the requested range, if valid, and return a PortManager for it" in {
      val portManager = StaticPortManager(1 to 3)
      val otherPortManager = portManager.reserve(1 to 2)
      portManager.bounds should be(1 to 3)
      portManager.getAvailable should be(Set(3))
      otherPortManager.bounds should be(1 to 2)
      otherPortManager.getAvailable should be(Set(1, 2))
    }
  }
}
