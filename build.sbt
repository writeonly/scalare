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

lazy val IntegrationTest = config("it") extend(Test)
lazy val End2EndTest = config("et") extend(Test)

lazy val integrationInConfig = inConfig(IntegrationTest)(Defaults.testTasks)
lazy val end2endInConfig = inConfig(End2EndTest)(Defaults.testTasks)

def whiteFilter(name: String): Boolean = name endsWith "AssertSpec"
def grayFilter(name: String): Boolean = (name endsWith "ScalarSpec") || (name endsWith "VectorSpec")
def blackFilter(name: String): Boolean = (name endsWith "FunSpec") || (name endsWith "FeatureSpec")

lazy val whiteSetting = testOptions in Test := Seq(Tests.Filter(whiteFilter))
lazy val graySetting = testOptions in IntegrationTest := Seq(Tests.Filter(grayFilter))
lazy val blackSetting = testOptions in End2EndTest := Seq(Tests.Filter(blackFilter))

lazy val inConfigs = Seq(integrationInConfig, end2endInConfig)
lazy val settings = Seq(whiteSetting, graySetting, blackSetting)

lazy val root = (project in file("."))
  .dependsOn(main)
    .configs(IntegrationTest, End2EndTest)
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
  .configs(IntegrationTest, End2EndTest)
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
  .configs(IntegrationTest, End2EndTest)
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


lazy val spec = (project in file("son2/son2-spec"))
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



