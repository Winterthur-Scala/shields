import akka.actor.{Props, Actor}

case class CreateShieldForJenkinsJob(protocol: String, domain: String, job: String)

class JenkinsStatusActor extends Actor {
  val svgGeneratorActor = context.actorOf(Props[SvgGeneratorActor], name = "SvgGeneratorActor")

  override def receive: Receive = {
    case CreateShieldForJenkinsJob(protocol, domain, job) =>
      // JenkinsStatus.jsonUrl(protocol, domain, job)
      val colorFromJson = "blue"
      val (status, color) = JenkinsStatus.matchStatusAndColor(colorFromJson)

      svgGeneratorActor forward CreateShield("build", status, Option(color))
  }
}

object JenkinsStatus {
  type StatusString = String
  type RgbString = String

  def jsonUrl(protocol: String, domain: String, job: String): String =
    protocol + "://" + domain + "/job/" + "/api/json?tree=color"

  def matchStatusAndColor(colorName: String): (StatusString, RgbString) = colorName match {
    case "blue" => ("passing", "0f0")
    case "red" => ("failing", "f00")
    case "yellow" => ("unstable", "ff0")
    case "grey" | "disabled" | "aborted" | "notbuilt" => ("not built", "ccc")
    case _ => ("building", "ccc")
  }
}
