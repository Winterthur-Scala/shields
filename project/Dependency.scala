import sbt._

object Version {
  val akkaHttp = "1.0"
}

object Artifact {
  val akkaHttpCore =   "com.typesafe.akka" % "akka-http-core-experimental_2.11" % Version.akkaHttp
  val akkaHttpDsl =   "com.typesafe.akka" % "akka-http-experimental_2.11" % Version.akkaHttp
}

object Dependency {
  import Artifact._
  val shields = Seq(
    akkaHttpCore,
    akkaHttpDsl
  )
}