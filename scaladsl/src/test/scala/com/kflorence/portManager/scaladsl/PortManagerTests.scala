package com.kflorence.portManager.scaladsl

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PortManagerTests extends AnyWordSpec with Matchers {
  "ephemeralPorts" should {
    "match IANA ephemeral port range" in {
      PortManager.ephemeralPorts should be(49152 to 65535)
    }
  }
}
