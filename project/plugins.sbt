resolvers += "Spray repo" at "http://repo.spray.io"
resolvers += Resolver.url("artifactory", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)
resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.4.0")
addSbtPlugin("io.spray" % "sbt-revolver" % "0.7.2")
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.13.0")

