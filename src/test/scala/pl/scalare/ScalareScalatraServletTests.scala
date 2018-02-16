package pl.scalare

import org.scalatra.test.scalatest._

class ScalareScalatraServletTests extends ScalatraFunSuite {

  addServlet(classOf[ScalareScalatraServlet], "/*")

  test("GET / on ScalareScalatraServlet should return status 200") {
    get("/") {
      status should equal(200)
    }
  }

}
