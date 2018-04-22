import pl.scalare._
import org.scalatra._
import javax.servlet.ServletContext

import pl.scalare.view.ScalareScalatraServlet

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext): Unit = {
    context.mount(new ScalareScalatraServlet, "/*")
    context.mount(new RailsLikeRouting, "/*")
    context.mount(new Articles, "/*")
  }
}
