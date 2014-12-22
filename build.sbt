// set the name of the project
name := "gs-cheater"

//version := "1.0"

organization := "com.gigaspaces.sbp"

// set the Scala version used for the project
scalaVersion := "2.11.1"

// set the main Scala source directory to be <base>/src
//scalaSource in Compile := baseDirectory.value / "src"

// set the Scala test source directory to be <base>/test
//scalaSource in Test := baseDirectory.value / "test"

// add a test dependency on ScalaCheck
libraryDependencies += "org.scalacheck" % "scalacheck_2.11" % "1.11.5"

// add compile dependencies on some dispatch modules
libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  "com.jasonnerothin" % "testing" % "0.01" % Test,
  "com.gigaspaces" % "gs-openspaces" % "10.0.1-11800-RELEASE",
  "com.gigaspaces" % "gs-runtime" % "10.0.1-11800-RELEASE",
  "com.typesafe.akka" %% "akka-actor" % "2.3.7",
  "com.typesafe" % "config" % "1.2.1",
  "org.scalatest" %% "scalatest" % "2.2.3" % Test,
  "org.slf4s" %% "slf4s-api" % "1.7.7"
)

// Set a dependency based partially on a val.
//{
//  val libosmVersion = "2.5.2-RC1"
//  libraryDependencies += ("net.sf.travelingsales" % "osmlib" % libosmVersion from
//    "http://downloads.sourceforge.net/project/travelingsales/libosm/"+libosmVersion+"/libosm-"+libosmVersion+".jar")
//}

// reduce the maximum number of errors shown by the Scala compiler
maxErrors := 50

// increase the time between polling for file changes when using continuous execution
pollInterval := 1000

// append several options to the list of options passed to the Java compiler
javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

// append -deprecation to the options passed to the Scala compiler
scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

//oneJarSettings

//libraryDependencies += "commons-lang" % "commons-lang" % "2.6"

//mainClass in oneJar := Some("com.gigaspaces.sbp.RebalanceRunner")

// define the statements initially evaluated when entering 'console', 'consoleQuick', or 'consoleProject'
//initialCommands := """
//  import System.{currentTimeMillis => now}
//  def time[T](f: => T): T = {
//    val start = now
//    try { f } finally { println("Elapsed: " + (now - start)/1000.0 + " s") }
//  }
//                   """

// set the initial commands when entering 'console' or 'consoleQuick', but not 'consoleProject'
//initialCommands in console := "import gs-rebalance._"

// set the main class for packaging the main jar
// 'run' will still auto-detect and prompt
// change Compile to Test to set it for the test jar
//mainClass in (Compile, packageBin) := Some("myproject.MyMain")

// set the main class for the main 'run' task
// change Compile to Test to set it for 'test:run'
//mainClass in (Compile, run) := Some("myproject.MyMain")

// add <base>/input to the files that '~' triggers on
//watchSources += baseDirectory.value / "input"

// add a maven-style repository
//resolvers += "name" at "url"
resolvers += Resolver.mavenLocal

// add a sequence of maven-style repositories
resolvers += "sbt-assembly-resolver-0" at "http://repo.scala-sbt.org/scalasbt/sbt-plugin-releases"

resolvers += "cloudbees-jpn" at "http://repository-jasonnerothin.forge.cloudbees.com/release/"

// define the repository to publish to
//publishTo := Some("name" at "url")

// set Ivy logging to be at the highest level
//ivyLoggingLevel := UpdateLogging.Full

// disable updating dynamic revisions (including -SNAPSHOT versions)
//offline := true

// set the prompt (for this build) to include the project id.
//shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }

// set the prompt (for the current project) to include the username
//shellPrompt := { state => System.getProperty("user.name") + "> " }

// disable printing timing information, but still print [success]
//showTiming := false

// disable printing a message indicating the success or failure of running a task
//showSuccess := false

// change the format used for printing task completion time
//timingFormat := {
//  import java.text.DateFormat
//  DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
//}

// disable using the Scala version in output paths and artifacts
//crossPaths := false

// fork a new JVM for 'run' and 'test:run'
fork := true

// fork a new JVM for 'test:run', but not 'run'
fork in Test := true

// add a JVM option to use when forking a JVM for 'run'
javaOptions += "-Xmx2g"

// only use a single thread for building
//parallelExecution := true
//   Tests from other projects may still run concurrently.
//parallelExecution in Test := true

// set the location of the JDK to use for compiling Java code.
// if 'fork' is true, this is used for 'run' as well
//javaHome := Some(file("/usr/lib/jvm/sun-jdk-1.6"))

// Use Scala from a directory on the filesystem instead of retrieving from a repository
//scalaHome := Some(file("/home/user/scala/trunk/"))

// only show 10 lines of stack traces
traceLevel := 10

// only show stack traces up to the first sbt stack frame
traceLevel := 0
