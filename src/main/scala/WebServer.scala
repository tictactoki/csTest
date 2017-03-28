/**
  * Created by wong on 28/03/17.
  */

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.headers.{HttpOrigin, HttpOriginRange}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import ch.megard.akka.http.cors.{CorsSettings, HttpHeaderRange}
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

  protected val assetRoute = pathPrefix("assets") {
    getFromDirectory("src/main/front")
  }

}

/**
  * Webserver
  */
object WebServer extends Route with App {

  implicit val system = ActorSystem("simple-complete-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
  val settings = CorsSettings.defaultSettings


  val route = cors(settings) {
    assetRoute ~ autocomplete
  }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
  println("server online")
  StdIn.readLine()
  bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())


}
