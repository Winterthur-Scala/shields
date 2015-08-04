import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport._


object Main extends App{
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  
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
    path("badge" / Segment ~ "-" ~ Segment ~ "-" ~ Segment ~ ".svg")  { (subject, status, color) =>
      complete {
        s"Hello $subject, $status, $color"
      }
    }
  }

  Http().bindAndHandle(route, "localhost", 8080)
}
