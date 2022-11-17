enablePlugins(JavaAppPackaging)

enablePlugins(SbtTwirl)

name := "pwprinter"

scalaVersion := "2.12.17"

updateOptions := updateOptions.value.withCachedResolution(true)

version := "0.2"

organization := "org.stingray.contester"

ThisBuild / useCoursier := false


scalacOptions ++= Seq(
  "-Xfatal-warnings",  // New lines for each options
  "-deprecation",
  "-Xasync",
  "-unchecked",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:existentials",
  "-language:postfixOps",
  "-opt:l:method",
  "-opt:l:inline",
  "-opt-inline-from:<sources>",
  "-Xno-uescape"
)

updateOptions := updateOptions.value.withCachedResolution(true)

resolvers ++= Seq(
  Resolver.mavenLocal,
  Resolver.jcenterRepo,
  Resolver.sonatypeRepo("snapshots"),
  Resolver.typesafeRepo("releases")
)

val opRabbitVersion = "1.0.0-M11"

libraryDependencies ++= Seq(
  "org.stingray.contester" %% "contester-dbmodel" % "2022.0.1-SNAPSHOT",
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
  "com.typesafe" % "config" % "1.3.1",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "commons-io" % "commons-io" % "2.7"
).map(_.exclude("org.slf4j", "slf4j-jdk14"))

TwirlKeys.templateFormats += ("tex" -> "org.stingray.contester.pwprinter.TexFormat")
