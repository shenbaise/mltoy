package com.example

import spray.testkit.ScalatestRouteTest
import spray.http._
import StatusCodes._
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers


class MyServiceTest extends FlatSpec with ScalatestRouteTest with ShouldMatchers with MyService {
  def actorRefFactory = system

  behavior of "MyService"

  it should "return a greeting for GET requests to the root path" in {
    Get() ~> myRoute ~> check {
      entityAs[String] should include("Say hello")
    }
  }

  it should "leave GET requests to other paths unhandled" in {
    Get("/kermit") ~> myRoute ~> check {
      handled should be(false)
    }
  }

  it should "return a MethodNotAllowed error for PUT requests to the root path" in {
    Put() ~> sealRoute(myRoute) ~> check {
      status should be(MethodNotAllowed)
      entityAs[String] should be("HTTP method not allowed, supported methods: GET")
    }
  }
}