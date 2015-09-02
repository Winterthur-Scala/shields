import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport._
import akka.http.scaladsl.marshalling.PredefinedToEntityMarshallers
import akka.http.scaladsl.model.MediaTypes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer


trait ShieldsRestService {
  def system: ActorSystem

  def materializer: ActorMaterializer

  def marshaller = PredefinedToEntityMarshallers.stringMarshaller(`text/html`)

  def shields: Route = {
    implicit val systemVal = system
    implicit val materializerVal = materializer
    implicit val marshallerVal = marshaller

    get {
      pathSingleSlash {
        complete {
          /**
           * Here we match a top level / route and return html. Scala allows inlining of XML, but it is feature that
           * is deprecated and shouldn't be used. It comes with an external library.
           */

          <html>
            <body>Hello World!</body>
          </html>
        }
      } ~
        /**
         * The ~ method combines two paths together. If the first one doesn't match, the processing is done by the second.
         */
        path("ping") {
          complete("PONG")
        } ~
        path("crash") {
          sys.error("Boom!!!!!")
        } ~
        path("badge" / Segment) { (badgeDefinition) =>

          /**
           * Here we match the /badge/A-B-C.svg urls. The Segment directive is responsible for retrieving of the whole
           * content between two / or end of the path.
           * The path directive will return a directive that can accept a function as parameter.
           * The number and types of parameters taken by the function depends on the parameters passed
           * to path directive.
           */
          complete {
            /**
             * Definition of a reqular expression.
             * The r method is added in StringLike trait to String class. The object created by this method contains
             * an unapply/unapplySeq method. Thanks to that methods, it can be used in pattern matching below.
             */
            val pathRegex = "(.*)-(.*)-(.*)\\.svg".r

            /**
             * Pattern matching on the badge definition against regex. The groups from regex expression are extracted
             * to subject, status and color variables if the badge definition matches the regex.
             * In this case the svg is generated from extracted values.
             * The _ is used for values that we don't care about. When the regex didn't match, just simple text is returned.
             */
            badgeDefinition match {
              case pathRegex(subject, status, color) => SvgGenerator.generate(subject, status, Option(color))
              case _ => "Nothing"
            }
          }
        }
    }
  }
}

case class ShieldsRestActor(system: ActorSystem, materializer: ActorMaterializer) extends ShieldsRestService
