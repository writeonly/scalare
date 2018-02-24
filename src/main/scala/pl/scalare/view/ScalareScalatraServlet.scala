package pl.scalare.view

import org.scalatra._

class ScalareScalatraServlet extends ScalatraServlet {

  get("/") {
    views.html.hello()
  }

}
