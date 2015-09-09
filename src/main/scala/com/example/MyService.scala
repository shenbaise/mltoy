package com.example

import akka.actor.Actor
import spray.routing._
import directives.LogEntry
import spray.http._
import MediaTypes._
import akka.event.Logging
import java.io.File
import shapeless._


// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class MyServiceActor extends Actor with MyService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}

// this trait defines our service behavior independently from the service actor
trait MyService extends HttpService with StaticResources with TwirlPages with InlineHtml {

  def showPath(req: HttpRequest) = LogEntry("Method = %s, Path = %s" format(req.method, req.uri), Logging.InfoLevel)

  val myRoute =
    logRequest(showPath _) {
      staticResources ~ inlineHtml ~ twirlPages
    }
}

// Trait for serving static resources
// Sends 404 for 'favicon.icon' requests and serves static resources in 'bootstrap' folder.
trait StaticResources extends HttpService {

  val staticResources =
    get {
      path("favicon.ico") {
        complete(StatusCodes.NotFound)
      } ~
      path(Rest) { path =>
        getFromResource("bootstrap/%s" format path)
      } ~
      path("file") {
        getFromResource("application.conf")
      }
    }
}

// Trait for serving some dynamic Twirl pages
trait TwirlPages extends HttpService {

  val twirlPages =
    get {
      path("index") {
        respondWithMediaType(`text/html`) {
          complete(html.index().toString)
        }
      } ~
      path("index2") {
        respondWithMediaType(`text/html`) {
          complete(html.index2("Spraying some Bootstrap", "Hello Twirl served by Spray").toString)
        }
      }
    }
}

// Trait for serving a page with inline html
trait InlineHtml extends HttpService {

  val inlineHtml =
    get {
      path("") {
        respondWithMediaType(`text/html`) {
          // XML is marshalled to `text/xml` by default, so we simply override here
          complete {
            <html>
              <body>
                <h1>Say hello to
                  <i>spray-routing</i>
                  on
                  <i>spray-can</i>
                  !</h1>
                <p>
                  <h2>Some Twirl and static pages:</h2>
                  <a href="/index">Simple index page using Twirl</a><br/>
                  <a href="/index2">Another Twirl page using a Twitter Bootstrap template with dynamic data and using static css and js resources</a><br/>
                  <a href="/file">Getting content from a static file</a><br/>
                </p>
              </body>
            </html>
          }
        }
      }
    }
}
