name := "KafkaWatcher"

version := "1.0"
mainClass := Some("consumer1.main")

scalaVersion := "2.10.5"

libraryDependencies ++=
  Seq("org.apache.kafka" % "kafka-clients" % "0.10.2.0",
    "com.typesafe.akka" % "akka-http_2.11" % "10.0.9",
    "org.apache.spark" % "spark-core_2.10" % "2.1.0",
    "org.apache.spark" % "spark-sql_2.10" % "2.1.0",
    "org.apache.spark" % "spark-mllib_2.10" % "2.1.0",
    "org.apache.spark" % "spark-streaming_2.10" % "2.1.0",
    "org.apache.spark" % "spark-hive_2.10" % "2.1.0",
      "org.apache.avro"  %  "avro"  %  "1.7.7"
  )

unmanagedJars in Compile += file("Lib/analytics_events.jar")

resolvers += Resolver.mavenLocal


enablePlugins(JavaAppPackaging)
enablePlugins(RpmPlugin)

rpmVendor := "jamcity"
