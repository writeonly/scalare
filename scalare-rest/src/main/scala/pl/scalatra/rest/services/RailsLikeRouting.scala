package pl.scalare

import org.scalatra.{RailsPathPatternParser, ScalatraFilter}

class RailsLikeRouting extends ScalatraFilter {
  implicit override def string2RouteMatcher(path: String) =
    RailsPathPatternParser(path)

  get("/:file(.:ext)") {
    // matched Rails-style
  }
}
