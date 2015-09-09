logLevel := Level.Warn
resolvers += "Spray repo" at "http://repo.spray.io"
resolvers += Resolver.url("artifactory", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)
resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("io.spray" % "sbt-revolver" % "0.7.2")
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.13.0")
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.4.0")
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.1.2")
addSbtPlugin("io.spray" %% "sbt-twirl" % "0.7.0")
resolvers += Classpaths.sbtPluginSnapshots
