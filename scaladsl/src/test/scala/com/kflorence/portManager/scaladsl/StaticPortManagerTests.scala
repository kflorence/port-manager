/*
 * Copyright 2021 Kyle Florence
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kflorence.portManager.scaladsl

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class StaticPortManagerTests extends AnyWordSpec with Matchers {
  "apply" should {
    "create an instance from an unordered set of ports" in {
      StaticPortManager(Set(1, 3, 2)).reserved should be(Set(1, 3, 2))
    }
    "create an instance from a range" in {
      StaticPortManager(1 to 3).reserved should be(Set(1, 2, 3))
    }
  }

  "claim" should {
    "claim and return the next reserved but unclaimed port" in {
      val portManager = StaticPortManager(2 to 4)
      portManager.claim() should be(2)
      portManager.claimNext() should be(3)

      // "next" does not sort
      StaticPortManager(Set(3, 4, 1)).claim() should be(3)
    }
    "claim and return true when claiming a single unclaimed port" in {
      StaticPortManager(1 to 3).claim(2).booleanValue() should be(true)
    }
    "claim and return false when claiming a single claimed port" in {
      val portManager = StaticPortManager(1 to 3)
      portManager.claim(1).booleanValue() should be(true)
      portManager.claim(1).booleanValue() should be(false)
    }
    "return successfully claimed ports" in {
      StaticPortManager(1 to 3).claim(Set(0, 2, 3, 4)) should be(Set(2, 3))
    }
  }

  "claimed" should {
    "return all claimed ports" in {
      val portManager = StaticPortManager(1 to 3)
      portManager.claimed should be(empty)
      portManager.claim()
      portManager.claimed should be(Set(1))
    }
    "return the ports that are claimed" in {
      val portManager = StaticPortManager(1 to 3)
      portManager.claimed(Set(1, 2, 3)) should be(empty)
      portManager.claim(2)
      portManager.claimed(Set(1, 2, 3)) should be(Set(2))
    }
  }

  "isClaimed" should {
    val portManager = StaticPortManager(1 to 3)
    portManager.claim(2)
    "return true if the port is claimed" in {
      portManager.isClaimed(2).booleanValue() should be(true)
    }
    "return false if the port is not claimed" in {
      portManager.isClaimed(1).booleanValue() should be(false)
      portManager.isClaimed(0).booleanValue() should be(false)
    }
  }

  "isReserved" should {
    val portManager = StaticPortManager(1 to 3)
    portManager.claim(2)
    "return true if the port is reserved" in {
      portManager.isReserved(1).booleanValue() should be(true)
      portManager.isReserved(2).booleanValue() should be(true)
    }
    "return false if the port is not reserved" in {
      portManager.isReserved(0).booleanValue() should be(false)
      portManager.isReserved(4).booleanValue() should be(false)
    }
  }

  "isUnclaimed" should {
    val portManager = StaticPortManager(1 to 3)
    portManager.claim(2)
    "return true if the port is unclaimed" in {
      portManager.isUnclaimed(0).booleanValue() should be(true)
      portManager.isUnclaimed(1).booleanValue() should be(true)
    }
    "return false if the port is not unclaimed" in {
      portManager.isUnclaimed(2).booleanValue() should be(false)
    }
  }

  "reserved" should {
    "get all reserved ports" in {
      val portManager = StaticPortManager(1 to 3)
      portManager.reserved should be(Set(1, 2, 3))
      portManager.claim()
      portManager.reserved should be(Set(1, 2, 3))
      StaticPortManager(Set(4, 5, 6)).reserved should be(Set(4, 5, 6))
    }
  }

  "unclaim" should {
    "unclaim and return true when un-claiming a single claimed port" in {
      val portManager = StaticPortManager(1 to 3)
      portManager.claim(2)
      portManager.unclaim(2).booleanValue() should be(true)

      // "unclaim" does not sort
      portManager.unclaimed should be(Set(1, 3, 2))
    }
    "return false when un-claiming a single un-claimed port" in {
      StaticPortManager(1 to 3).unclaim(2).booleanValue() should be(false)
    }
    "return successfully unclaimed ports" in {
      val portManager = StaticPortManager(1 to 3)
      portManager.claim(Set(2, 3))
      portManager.unclaim(Set(0, 2, 3, 4)) should be(Set(2, 3))
    }
  }

  "unclaimed" should {
    "return all unclaimed ports" in {
      val portManager = StaticPortManager(1 to 3)
      portManager.unclaimed should be(Set(1, 2, 3))
      portManager.claimNext()
      portManager.unclaimed should be(Set(2, 3))
    }
  }

  "Ephemeral" should {
    "create a StaticPortManager for PortManager.ephemeralPorts" in {
      StaticPortManager.Ephemeral.reserved should be(PortManager.ephemeralPorts.toSet)
    }
  }
}
