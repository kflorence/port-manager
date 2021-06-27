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

package com.kflorence.portManager

import java.util.concurrent.atomic.AtomicReference

/** Manages static port reservations within a port bounds.
  * @param bounds The port bounds.
  */
case class StaticPortManager(reserved: Set[Int]) extends PortManager {
  private val available = new AtomicReference[Set[Int]](reserved)

  def check(ports: Set[Int]): Set[Int] = ports.diff(getAvailable)

  def free(ports: Set[Int]): Set[Int] = synchronized {
    val invalid = ports.diff(reserved)
    if (invalid.nonEmpty)
      throw new IllegalArgumentException(s"The following ports are invalid: ${invalid.mkString(", ")}")
    val updated = getAvailable ++ ports
    available.set(updated)
    updated
  }

  /** @return The available ports within the bounds. */
  def getAvailable: Set[Int] = available.get()

  /** Gets and reserves the next available port within the port bounds. */
  @throws[NoSuchElementException]("If there are no ports available.")
  def getAndReserveNextAvailable: Int = synchronized {
    val currentlyAvailable = getAvailable
    if (currentlyAvailable.isEmpty) throw new NoSuchElementException("There are no ports available.")
    val next = currentlyAvailable.head
    available.set(currentlyAvailable.drop(1))
    next
  }

  @throws[IllegalArgumentException]("If the set of ports contains an already reserved port.")
  def reserve(ports: Set[Int]): PortManager = synchronized {
    val invalid = check(ports)
    if (invalid.nonEmpty)
      throw new IllegalArgumentException(s"The following ports are invalid: ${invalid.mkString(", ")}")
    available.set(getAvailable.diff(ports))
    StaticPortManager(ports)
  }
}

object StaticPortManager {
  def apply(range: Range): StaticPortManager = StaticPortManager(range.toSet)
}

object EphemeralStaticPortManager extends StaticPortManager(PortBounds.ephemeral.toSet)
