name := "streamator"

version := "0.1"

organization := "es.rojocarmesi"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.0",
  "org.scalacheck" %% "scalacheck" % "1.13.2" % "test",
  "com.storm-enroute" %% "scalameter" % "0.7",
  "com.github.scopt" %% "scopt" % "3.7.0",
  "org.apache.kafka" % "kafka-clients" % "0.10.2.1",
  "org.apache.flink" %% "flink-connector-kafka-0.10" % "1.3.2"
)

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")
