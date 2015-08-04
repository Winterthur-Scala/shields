import sbt._

object Version {
  val akkaHttp = "1.0"
  val akkaActor = "2.3.12"
}

object Artifact {
  val akkaHttpCore =   "com.typesafe.akka" % "akka-http-core-experimental_2.11" % Version.akkaHttp
  val akkaHttpDsl =   "com.typesafe.akka" % "akka-http-experimental_2.11" % Version.akkaHttp
  val akkaHttpXml =   "com.typesafe.akka" % "akka-http-xml-experimental_2.11" % Version.akkaHttp
  val akkaActor =   "com.typesafe.akka" % "akka-actor_2.11" % Version.akkaActor
}

object Dependency {
  import Artifact._
  val shields = Seq(
    akkaHttpCore,
    akkaHttpDsl,
    akkaHttpXml,
    akkaActor
  )
}