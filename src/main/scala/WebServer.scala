/**
  * Created by wong on 28/03/17.
  */

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import ch.megard.akka.http.cors.CorsSettings
import spray.json.DefaultJsonProtocol
import ch.megard.akka.http.cors.CorsDirectives._

import scala.io.StdIn


trait Route extends DefaultJsonProtocol with SprayJsonSupport {

  /**
    * route for get the list with input prefix
    */
  protected val autocomplete = path("autocomplete") {
    get {
      parameter("word".as[String]) { input =>
        complete {
          CsTest.getWords(input)
        }
      }
    }
  }

}

/**
  * Webserver
  */
object WebServer extends Route with App {

  implicit val system = ActorSystem("simple-complete-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
  val seq = scala.collection.immutable.Seq(GET, POST, HEAD, OPTIONS, PUT)
  val settings = CorsSettings.defaultSettings.copy(allowedMethods = seq)

  val route = cors(settings) {
    autocomplete
  }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
  println("server online")
  StdIn.readLine()
  bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())


}
