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

trait PortManager {
  def check(range: Range): Set[Int] = check(range.toSet)
  def check(ports: Set[Int]): Set[Int]
  def free(range: Range): Set[Int] = free(range.toSet)
  def free(ports: Set[Int]): Set[Int]
  def getAvailable: Set[Int]
  def getAndReserveNextAvailable: Int
  def reserve(count: Int): PortManager = reserve(getAvailable.take(count))
  def reserve(range: Range): PortManager = reserve(range.toSet)
  def reserve(ports: Set[Int]): PortManager
  def reserved: Set[Int]
}

object PortBounds {
  val ephemeral: Range = 49152 to 65535
}
