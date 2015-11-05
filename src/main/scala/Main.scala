import akka.actor.{Props, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport._
import akka.http.scaladsl.marshalling.PredefinedToEntityMarshallers
import akka.http.scaladsl.model.MediaTypes._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer


/**
 * The object keyword indicates a singleton. Extending the App trait provides the main method and additionally all
 * the statements executed in the body of the object will be executed as part of the main method.
 */
object Main extends App {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val marshaller = PredefinedToEntityMarshallers.stringMarshaller(`text/html`)

  val svgGeneratorActor = system.actorOf(Props[SvgGeneratorActor], name = "SvgGeneratorActor")
  val jenkinsStatusActor = system.actorOf(Props[JenkinsStatusActor], name = "JenkinsStatusActor")

  /**
   * Start the server with the above route definition.
   */
  Http().bindAndHandle(ShieldsRestActor(system, materializer, svgGeneratorActor, jenkinsStatusActor).shields, "localhost", 8080)
}
