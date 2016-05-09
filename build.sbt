name := "akka-cluster-chat"
version := "1.0"
scalaVersion := "2.11.8"

val akkaVersion = "2.4.4"

libraryDependencies ++= List(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion
)