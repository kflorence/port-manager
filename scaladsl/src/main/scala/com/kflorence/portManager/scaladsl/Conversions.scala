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

import scala.jdk.CollectionConverters._

object Conversions {
  def asJavaSet(scalaSet: Set[Int]): java.util.Set[Integer] = scalaSet.map(Integer.valueOf).asJava
  def asScalaSet(javaSet: java.util.Set[Integer]): Set[Int] = javaSet.asScala.map(_.toInt).toSet
}
