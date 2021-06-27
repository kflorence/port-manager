# Port Manager
Thread-safe management of ports within a port bounds. The primary use-case is the reservation of static ports during integration testing. It allows the ports used by applications to be known and prevents ports from colliding across test suites.

## Usage
First, include the library as a dependency of your project (the `Test` scope is optional):

```sbt
val portManager = "com.kflorence" %% "port-manager" % "0.0.1"
libraryDependencies ++= Seq(portManager % Test)
```
