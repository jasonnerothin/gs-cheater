import sbt._

lazy val root = (project in file(".")).dependsOn(assemblyPlugin)

lazy val assemblyPlugin = uri("https://github.com/sbt/sbt-onejar.git")

//resolvers += Resolver.SonatypeRepositoryRoot("artifactory", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.defaultPatterns)
//resolvers += Resolver.JavaNet2RepositoryRoot("artifactory", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.defaultPatterns)
//resolvers += Resolver.url("artifactory", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)

addSbtPlugin("org.scala-sbt.plugins" % "sbt-onejar" % "0.9-SNAPSHOT")  // for sbt-0.13.x or higher
