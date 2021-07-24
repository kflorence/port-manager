# Port Manager
Thread-safe management of ports within a port bounds. The primary use-case is the reservation of static ports during integration testing. It allows the ports used by applications to be known and prevents ports from colliding across test suites.

## Usage
Include the library as a dependency of your project (the `Test` scope is optional).

For Java:
```sbt
val portManager = "com.kflorence" %% "port-manager-javadsl" % "0.1.0"
libraryDependencies ++= Seq(portManager % Test)
```

For Scala:
```sbt
val portManager = "com.kflorence" %% "port-manager-scaladsl" % "0.1.0"
libraryDependencies ++= Seq(portManager % Test)
```
