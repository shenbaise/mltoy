//import AssemblyKeys._ // put this at the top of the file

//assemblySettings

organization  := "org.mltoy"

version       := "0.1"

scalaVersion  := "2.10.5"

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource
 
javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

unmanagedResourceDirectories in Compile <++= baseDirectory { base =>
    Seq( base / "src/main/webapp" )
}

unmanagedBase := baseDirectory.value / "lib"

libraryDependencies ++= {
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  val sparkV = "1.4.0"
  val hbaseV = "0.98.7-hadoop2"
  Seq(
    "io.spray"            %%  "spray-can"     % sprayV,
    "io.spray"            %%  "spray-routing" % sprayV,
    "io.spray" % "spray-caching_2.10" % sprayV,
    "io.spray"            %%  "spray-testkit" % sprayV  % "test",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
    "org.specs2"          %%  "specs2-core"   % "2.3.7" % "test",
    "org.scalatest" % "scalatest_2.10" % "2.2.5" % "test",
    "org.seleniumhq.selenium" % "selenium-java" % "2.44.0" % "test",
    "org.apache.spark" %% "spark-streaming" % sparkV,
    "org.apache.spark" %% "spark-core" % sparkV,
    "org.apache.spark" %% "spark-sql" % sparkV,
    "org.apache.spark" %% "spark-mllib" % sparkV,
    "org.apache.spark" % "spark-hive_2.10" % sparkV,
    "org.apache.spark" % "spark-streaming-flume_2.10" % sparkV,
    "org.apache.spark" % "spark-streaming-mqtt_2.10" % sparkV,
    "org.apache.spark" % "spark-streaming-kafka_2.10" % sparkV,
    "org.apache.hbase" % "hbase-common" % hbaseV,
    "org.apache.hbase" % "hbase-protocol" % hbaseV,
    "org.apache.hbase" % "hbase-client" % hbaseV,
    "org.scalanlp" % "breeze_2.10" % "0.11.2",
    "com.googlecode.netlib-java" % "netlib-java" % "1.1",
    "com.github.scopt"  %% "scopt"  % "3.2.0",
    "com.alibaba" % "fastjson" % "1.2.4",
    "com.google.guava" % "guava" % "14.0",
    //"org.scala-lang.modules" % "scala-xml_2.11" % "1.0.3",
    //"org.scala-lang.modules" % "scala-parser-combinators_2.11" % "1.0.3",
    "org.scala-lang.modules" % "scala-swing_2.10" % "2.0.0-M2",
    "org.sorm-framework" % "sorm" % "0.3.18",
    "com.h2database" % "h2" % "1.3.168"
  )
}


//mainClass in Compile := Some("org.mltoy.boot.BootStrap")
 
assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = true)

Revolver.settings
seq(Revolver.settings: _*)
seq(Twirl.settings: _*)

resolvers := Seq(
  // HTTPS is unavailable for Maven Central
  "Local Maven Repo" at "file:///E://repo",
  "osc"	at "http://maven.oschina.net/content/groups/public/",
  //"Maven Repository"     at "http://repo.maven.apache.org/maven2",
  //"Apache Repository"    at "https://repository.apache.org/content/repositories/releases",
  //"JBoss Repository"     at "https://repository.jboss.org/nexus/content/repositories/releases/",
  //"MQTT Repository"	at "https://repo.eclipse.org/content/repositories/paho-releases/",
  //"Cloudera Repository"  at "http://repository.cloudera.com/artifactory/cloudera-repos/",
  //"MapR Repository"		at "http://repository.mapr.com/maven",
  //"Spring Release Repository"	at "https://repo.spring.io/libs-release",

  // For Sonatype publishing
  // "sonatype-snapshots"   at "https://oss.sonatype.org/content/repositories/snapshots",
  // "sonatype-staging"     at "https://oss.sonatype.org/service/local/staging/deploy/maven2/",
  // also check the local Maven repository ~/.m2
  Resolver.mavenLocal
)
