package com.example

import org.scalatest.FlatSpec
import spray.testkit.ScalatestRouteTest
import org.scalatest.matchers.ShouldMatchers
import spray.http.StatusCodes

class InlineHtmlTest extends FlatSpec with ScalatestRouteTest with ShouldMatchers with InlineHtml {
  def actorRefFactory = system

  behavior of "InlineHtml routing trait"

  it should "return an inline page on /" in {
    Get("/") ~> inlineHtml ~> check {
      handled should be(true)
      status should be(StatusCodes.OK)
      entityAs[String] should include("Say hello to")
    }
  }
}
