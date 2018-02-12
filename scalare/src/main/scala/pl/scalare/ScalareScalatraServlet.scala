package pl.scalare

import org.scalatra._

class ScalareScalatraServlet extends ScalatraServlet {

  get("/") {
    views.html.hello()
  }

}
