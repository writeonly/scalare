enablePlugins(SbtTwirl)
enablePlugins(ScalatraPlugin)

resolvers += Classpaths.typesafeReleases

val ScalaVersion = "2.11.11"
val ScalatraVersion = "2.6.2"
val VersionSnapshot = "2.8.3-SNAPSHOT"

lazy val commonSettings = Seq(
  organization := "pl.writeonly.scalare",
  scalaVersion := ScalaVersion,
  version := VersionSnapshot
)

lazy val FunTest = config("fun") extend(Test)
lazy val FeatureTest = config("feature") extend(Test)

lazy val funInConfig = inConfig(FunTest)(Defaults.testTasks)
lazy val featureInConfig = inConfig(FeatureTest)(Defaults.testTasks)

def whiteFilter(name: String): Boolean = (name endsWith "ResultSpec") || (name endsWith "ThrowsSpec")
def grayFilter(name: String): Boolean = (name endsWith "ScalarSpec") || (name endsWith "VectorSpec ")
def blackFilter(name: String): Boolean = (name endsWith "FeatureSpec")

lazy val whiteSetting = testOptions in Test := Seq(Tests.Filter(whiteFilter))
lazy val graySetting = testOptions in FunTest := Seq(Tests.Filter(grayFilter))
lazy val blackSetting = testOptions in FeatureTest := Seq(Tests.Filter(blackFilter))

lazy val inConfigs = Seq(funInConfig, featureInConfig)
lazy val settings = Seq(whiteSetting, graySetting, blackSetting)

lazy val root = (project in file("."))
  .dependsOn(main)
    .configs(FunTest, FeatureTest)
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
  .configs(FunTest, FeatureTest)
  .settings(
    name := "scalare-main",
    commonSettings,
    libraryDependencies ++= Seq(
      "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
      "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
    )
  )

lazy val rest = (project in file("scalare-rest"))
    .dependsOn(spec)
//  .dependsOn(japi, impl)
  .configs(FunTest, FeatureTest)
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


lazy val spec = (project in file("scalare-spec"))
  .settings(
    name := "scalare-spec",
    commonSettings,
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-library" % "2.11.11",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
      "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0",
      "org.scalacheck" %% "scalacheck" % "1.13.5",
      "org.seleniumhq.selenium" % "selenium-java" % "2.35.0",
      "org.pegdown" % "pegdown" % "1.6.0",
      "ch.qos.logback" % "logback-classic" % "1.2.3"
    )
  )



