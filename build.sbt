enablePlugins(SbtTwirl)
enablePlugins(ScalatraPlugin)

resolvers += Classpaths.typesafeReleases

val ScalaVersion = "2.12.4"
val ScalatraVersion = "2.6.2"
val VersionSnapshot = "2.8.3-SNAPSHOT"
val DropwizardVersion = "1.1.4"
val JacksonVersion = "2.8.3"
val VaadinVersion = "8.1.2"
val ScalaLibraryVersion = ScalaVersion

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

lazy val drop = (project in file("scalare-drop"))
  .dependsOn(spec, core, adin)
  .configs(IntegrationTest, End2EndTest)
  .settings(
    name := "son2/son2-drop",
    commonSettings,
    integrationInConfig, end2endInConfig,
    whiteSetting, graySetting, blackSetting,
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-library" % ScalaLibraryVersion,
      "io.dropwizard" % "dropwizard-core" % DropwizardVersion,
      "io.dropwizard" % "dropwizard-assets" % DropwizardVersion
    ),
    mainClass in assembly := Some("pl.writeonly.son2.drop.AppSon2")
  )

lazy val adin = (project in file("scalare-adin"))
  .dependsOn(spec, core, json, impl)
  .configs(IntegrationTest, End2EndTest)
  .settings(
    name := "son2/son2-adin",
    commonSettings,
    integrationInConfig, end2endInConfig,
    whiteSetting, graySetting, blackSetting,
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-library" % ScalaLibraryVersion,
      "com.vaadin" % "vaadin-themes" % VaadinVersion,
      "com.vaadin" % "vaadin-client-compiled" % VaadinVersion,
      "com.vaadin" % "vaadin-server" % VaadinVersion,
      "javax.servlet" % "javax.servlet-api" % "4.0.0" % "provided"
    )
  )

lazy val impl = (project in file("son2/son2-impl"))
  .aggregate(text, jack, path, patch, diff, json)
  .dependsOn(spec, core, text, jack, path, patch, diff, json)
  .configs(IntegrationTest, End2EndTest)
  .settings(
    name := "son2/son2-impl",
    commonSettings,
    integrationInConfig, end2endInConfig,
    whiteSetting, graySetting, blackSetting,
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-library" % ScalaLibraryVersion
    )
  )

lazy val json = (project in file("son2/son2-impl/son2-json"))
  .dependsOn(spec, core, jack, path)
  .configs(IntegrationTest, End2EndTest)
  .settings(
    name := "son2/son2-json",
    commonSettings,
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-library" % ScalaLibraryVersion
    )
  )

lazy val path = (project in file("son2/son2-impl/son2-path"))
  .dependsOn(spec, core, jack)
  .configs(IntegrationTest, End2EndTest)
  .settings(
    name := "son2/son2-path",
    commonSettings,
    integrationInConfig, end2endInConfig,
    whiteSetting, graySetting, blackSetting,
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-library" % ScalaLibraryVersion,
      "com.jayway.jsonpath" % "json-path" % "2.4.0",
      "net.minidev" % "json-smart" % "2.3",
      "com.google.code.gson" % "gson" % "2.8.1",
      "org.apache.tapestry" % "tapestry-json" % "5.4.3",
      "org.codehaus.jettison" % "jettison" % "1.3.8",
      //      "org.json" % "json" % "20170516"
      "com.vaadin.external.google" % "android-json" % "0.0.20131108.vaadin1"
    )
  )

lazy val jack = (project in file("son2/son2-impl/son2-jack"))
  .dependsOn(spec, core)
  .configs(IntegrationTest, End2EndTest)
  .settings(
    name := "son2/son2-jack",
    commonSettings,
    integrationInConfig, end2endInConfig,
    whiteSetting, graySetting, blackSetting,
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-library" % ScalaLibraryVersion,
      "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % JacksonVersion,
      "com.fasterxml.jackson.dataformat" % "jackson-dataformat-xml" % JacksonVersion,
      "com.fasterxml.jackson.dataformat" % "jackson-dataformat-properties" % JacksonVersion,
      "com.fasterxml.jackson.dataformat" % "jackson-dataformat-csv" % JacksonVersion,
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % JacksonVersion,
      "ninja.leaping.configurate" % "configurate-hocon" % "3.2"
    )
  )

lazy val text = (project in file("son2/son2-impl/son2-text"))
  .dependsOn(spec, core)
  .configs(IntegrationTest, End2EndTest)
  .settings(
    name := "son2/son2-text",
    commonSettings,
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-library" % ScalaLibraryVersion,
      "org.apache.commons" % "commons-text" % "1.1",
      "org.unbescape" % "unbescape" % "1.1.5.RELEASE"
    )
  )

lazy val patch = (project in file("son2/son2-impl/son2-patch"))
  .dependsOn(spec, core, jack)
  .configs(IntegrationTest, End2EndTest)
  .settings(
    name := "son2/son2-patch",
    commonSettings,
    integrationInConfig, end2endInConfig,
    whiteSetting, graySetting, blackSetting,
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-library" % ScalaLibraryVersion,
      "com.github.fge" % "json-patch" % "1.9"
    )
  )

lazy val diff = (project in file("son2/son2-impl/son2-diff"))
  .dependsOn(spec, core)
  .configs(IntegrationTest, End2EndTest)
  .settings(
    name := "son2/son2-diff",
    commonSettings,
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-library" % ScalaLibraryVersion,
    )
  )

lazy val core = (project in file("son2/son2-core"))
  .dependsOn(spec)
  .configs(IntegrationTest, End2EndTest)
  .settings(
    name := "son2/son2-core",
    commonSettings,
    integrationInConfig, end2endInConfig,
    whiteSetting, graySetting, blackSetting,
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-library" % ScalaLibraryVersion,
      "org.scalactic" %% "scalactic" % "3.0.4",
      "com.google.guava" % "guava" % "23.0",
      "org.skyscreamer" % "jsonassert" % "1.5.0"
    )
  )


lazy val spec = (project in file("son2/son2-spec"))
  .settings(
    name := "scalare-spec",
    commonSettings,
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-library" % ScalaLibraryVersion,
      "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
      "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0",
      "org.scalacheck" %% "scalacheck" % "1.13.5",
      "org.seleniumhq.selenium" % "selenium-java" % "2.35.0",
      "org.pegdown" % "pegdown" % "1.6.0",
      "ch.qos.logback" % "logback-classic" % "1.2.3"
    )
  )



