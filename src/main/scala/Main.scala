import akka.actor.ActorSystem
import akka.http.scaladsl.marshalling.PredefinedToEntityMarshallers
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport._
import akka.http.scaladsl.model.MediaTypes._


object Main extends App{
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val marshaller = PredefinedToEntityMarshallers.stringMarshaller(`text/html`)
  
  val route = get {
    pathSingleSlash {
      complete {
        <html>
          <body>Hello World!</body>
        </html>
      }
    } ~
    path("ping") {
      complete("PONG")
    } ~
    path("crash") {
      sys.error("Boom!!!!!")

    } ~
    path("badge" / Segment) { (path) =>
      complete {
        val pathRegex = "(.*)-(.*)-(.*)\\.svg".r
        path match {
          case pathRegex(subject, status, color) => SvgGenerator.generate(subject, status, Option(color))
          case _ => "Nothing"
        }
      }
    }
  }

  Http().bindAndHandle(route, "localhost", 8080)
}
