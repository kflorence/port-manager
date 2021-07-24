# Port Manager
![Release](https://github.com/kflorence/port-manager/actions/workflows/release.yaml/badge.svg)
![Maven Central](https://img.shields.io/maven-central/v/com.kflorence/port-manager-scaladsl_2.13)

Thread-safe management of ports within a port bounds. The primary use-case is the reservation of static ports during integration testing. It allows the ports used by applications to be known and prevents ports from colliding across test suites.

## Usage
Include the library as a dependency of your project (the `Test` scope is optional).

```sbt
val version = "0.1.0"

// For Java
val portManagerJavadsl = "com.kflorence" %% "port-manager-javadsl" % version % Test
libraryDependencies ++= Seq(portManagerJavadsl)

// For Scala
val portManagerScaladsl = "com.kflorence" %% "port-manager-scaladsl" % version % Test
libraryDependencies ++= Seq(portManagerScaladsl)
```

You will then want to create a `PortManager` instance that is responsible for a range of ports. Generally, you can just use `StaticPortManager.Ephemeral`, which will manage the [IANA suggested ephemeral ports](https://www.iana.org/assignments/service-names-port-numbers/service-names-port-numbers.xhtml) in the range of `49152` to `65535`. Any time your tests need a port, you can simply invoke `StaticPortManager.Ephemeral.claim()`, which will claim and return the next available port. If there are no ports left to claim, an exception will be thrown. If for some reason you need to unclaim a port, you can invoke `StaticPortManager.Ephemeral.unclaim(port)` where `port` is the port you wish to unclaim.

You can also create your own `StaticPortManager` for a specific set of ports:
```scala
// Using specific ports
val portManagerSet = StaticPortManager(Set(49152, 49153, 49154, 49155))

// Using a range of ports
val portManagerRange = StaticPortManager(49155 to 50000)
```

Note that each port manager is only aware of the ports it manages. So if you use multiple, care should be taken to ensure the ports do not overlap.

For more use cases, see the [StaticPortManagerTests](./scaladsl/src/test/scala/com/kflorence/portManager/scaladsl/StaticPortManagerTests.scala).
