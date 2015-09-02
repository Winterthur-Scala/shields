import akka.actor.ActorSystem
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

  /**
   * Start the server with the above route definition.
   */
  Http().bindAndHandle(ShieldsRestActor(system, materializer).shields, "localhost", 8080)
}
