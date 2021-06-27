package com.kflorence.portManager.scaladsl

import scala.jdk.CollectionConverters._

object Conversions {
  def asJavaSet(scalaSet: Set[Int]): java.util.Set[Integer] = scalaSet.map(Integer.valueOf).asJava
  def asScalaSet(javaSet: java.util.Set[Integer]): Set[Int] = javaSet.asScala.map(_.toInt).toSet
}
