//import AssemblyKeys._ // put this at the top of the file

//assemblySettings

organization  := "org.mltoy"

version       := "0.1"

scalaVersion  := "2.10.5"

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource
 
javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

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
    "org.apache.spark" %% "spark-streaming" % sparkV % "provided",
    "org.apache.spark" %% "spark-core" % sparkV % "provided",
    "org.apache.spark" %% "spark-sql" % sparkV % "provided",
    "org.apache.spark" %% "spark-mllib" % sparkV % "provided",
    "org.apache.spark" % "spark-hive_2.10" % sparkV % "provided",
    "org.apache.spark" % "spark-streaming-flume_2.10" % sparkV % "provided",
    "org.apache.spark" % "spark-streaming-mqtt_2.10" % sparkV % "provided",
    "org.apache.spark" % "spark-streaming-kafka_2.10" % sparkV % "provided",
    //	"org.apache.spark" % "hbase-common" % hbaseV % "provided",
    "org.apache.hbase" % "hbase-common" % "1.1.1",
    "org.apache.hbase" % "hbase-protocol" % hbaseV % "provided",
    "org.apache.hbase" % "hbase-client" % hbaseV % "provided",
    "org.scalanlp" % "breeze_2.10" % "0.11.2",
    "com.github.scopt"  %% "scopt"  % "3.2.0",

    "com.alibaba" % "fastjson" % "1.2.4" % "provided"


  )
}


mainClass in Compile := Some("main.MergePartition")
 
assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)

Revolver.settings

resolvers ++= Seq(
  // HTTPS is unavailable for Maven Central
  "Maven Repository"     at "http://repo.maven.apache.org/maven2",
  "Apache Repository"    at "https://repository.apache.org/content/repositories/releases",
  "JBoss Repository"     at "https://repository.jboss.org/nexus/content/repositories/releases/",
  "MQTT Repository"	at "https://repo.eclipse.org/content/repositories/paho-releases/",
  "Cloudera Repository"  at "http://repository.cloudera.com/artifactory/cloudera-repos/",
  "MapR Repository"		at "http://repository.mapr.com/maven",
  "Spring Release Repository"	at "https://repo.spring.io/libs-release",

  // For Sonatype publishing
  // "sonatype-snapshots"   at "https://oss.sonatype.org/content/repositories/snapshots",
  // "sonatype-staging"     at "https://oss.sonatype.org/service/local/staging/deploy/maven2/",
  // also check the local Maven repository ~/.m2
  Resolver.mavenLocal
)
