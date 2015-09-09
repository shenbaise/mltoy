package com.example

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import spray.testkit.ScalatestRouteTest
import spray.http.StatusCodes._

class TwirlPagesTest extends FlatSpec with ScalatestRouteTest with ShouldMatchers with TwirlPages {
  def actorRefFactory = system

  behavior of "TwirlPages routing trait"

  it should "return a simple page on /index" in {
    Get("/index") ~> twirlPages ~> check {
      handled should be(true)
      status should be(OK)
      entityAs[String] should include("Hello, world!")
    }
  }

  it should "return a twitter bootstrap template on /index2" in {
    Get("/index2") ~> twirlPages ~> check {
      handled should be(true)
      status should be(OK)
      entityAs[String] should include("Hello Twirl served by Spray")
    }
  }
}
