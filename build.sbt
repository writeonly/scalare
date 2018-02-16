import sbt.Keys.scalaVersion

val ScalatraVersion = "2.6.2"

organization := "pl.scalare"

name := "scalare"

val scalaVersionLazy = "2.11.11"


scalaVersion := scalaVersionLazy

resolvers += Classpaths.typesafeReleases

lazy val versionSnapshot = "2.8.3-SNAPSHOT"


lazy val commonSettings = Seq(
  organization := "pl.writeonly.scalare",
  scalaVersion := scalaVersionLazy,
  version := versionSnapshot
)

lazy val root = (project in file("."))
  .dependsOn(main)
  //  .configs(FunTest, FeatureTest)
  .settings(
  name := "scalare",
      commonSettings,
  libraryDependencies ++= Seq(
    "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
    "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
    "org.eclipse.jetty" % "jetty-webapp" % "9.4.8.v20171121" % "container",
  )
)

lazy val main = (project in file("scalare-main"))
  .dependsOn(rest)
//  .configs(FunTest, FeatureTest)
  .settings(
    name := "scalare-main",
    commonSettings,
    libraryDependencies ++= Seq(
      "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
      "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
    )
  )

lazy val rest = (project in file("scalare-rest"))
//  .dependsOn(japi, impl)
//  .configs(FunTest, FeatureTest)
  .settings(
    name := "scalare-rest",
    commonSettings,
    libraryDependencies ++= Seq(
      "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
      "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
      "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime",

      "org.scalatra" %% "scalatra" % ScalatraVersion,
    )
  )

enablePlugins(SbtTwirl)
enablePlugins(ScalatraPlugin)
