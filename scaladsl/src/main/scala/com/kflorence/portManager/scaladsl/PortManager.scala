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

import com.kflorence.portManager.javadsl

/** Provides a more scala-friendly syntax for [[PortManager]]. */
trait PortManager extends javadsl.PortManager {
  /** Given a set of ports to claim, returns those which were successfully claimed.
    *
    * @param ports The set of ports to claim.
    * @return The ports that were successfully claimed.
    */
  def claim(ports: Set[Int]): Set[Int] =
    Conversions.asScalaSet(claim(Conversions.asJavaSet(ports)))

  /** All claimed ports. */
  def claimed: Set[Int] =
    Conversions.asScalaSet(getClaimed())

  /** Given a set of ports, returns those which are claimed.
    *
    * @param ports The ports to check.
    * @return The ports that are claimed.
    */
  def claimed(ports: Set[Int]): Set[Int] =
    Conversions.asScalaSet(getClaimed(Conversions.asJavaSet(ports)))

  /** All reserved ports. */
  def reserved: Set[Int] =
    Conversions.asScalaSet(getReserved())

  /** Given a set of ports, returns those which are reserved.
    *
    * @param ports The ports to check.
    * @return The ports that are reserved.
    */
  def reserved(ports: Set[Int]): Set[Int] =
    Conversions.asScalaSet(getReserved(Conversions.asJavaSet(ports)))

  /** Given a set of ports to unclaim, returns those which were successfully unclaimed.
    *
    * @param ports The ports to unclaim.
    * @return The ports that were successfully unclaimed.
    */
  def unclaim(ports: Set[Int]): Set[Int] =
    Conversions.asScalaSet(unclaim(Conversions.asJavaSet(ports)))

  /** All unclaimed ports. */
  def unclaimed: Set[Int] =
    Conversions.asScalaSet(getUnclaimed())

  /** Given a set of ports, returns those which are unclaimed.
    *
    * @param ports The ports to check.
    * @return From the given ports, those which are unclaimed.
    */
  def unclaimed(ports: Set[Int]): Set[Int] =
    Conversions.asScalaSet(getUnclaimed(Conversions.asJavaSet(ports)))
}

object PortManager {
  val ephemeralPorts: Range = 49152 to 65535
}
