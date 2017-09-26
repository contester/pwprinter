enablePlugins(JavaAppPackaging)

enablePlugins(SbtTwirl)

name := "pwprinter"

scalaVersion := "2.11.11"

updateOptions := updateOptions.value.withCachedResolution(true)

version := "0.1"

organization := "org.stingray.contester"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-optimise", "-explaintypes", "-Xcheckinit",
  "-Xlint", "-Xno-uescape")

resolvers ++= Seq(
    "twitter.com" at "http://maven.twttr.com/",
    "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases",
    "scala tools" at "http://scala-tools.org/repo-releases/",
    "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
    "typesafe artefactory" at "http://typesafe.artifactoryonline.com/typesafe/repo",
    "SpinGo OSS" at "http://spingo-oss.s3.amazonaws.com/repositories/releases",
    "stingr.net" at "http://stingr.net/maven"
)

val opRabbitVersion = "1.0.0-M11"

libraryDependencies ++= Seq(
  "org.mariadb.jdbc" % "mariadb-java-client" % "1.6.5",
  "com.typesafe" % "config" % "1.3.1",
  "com.typesafe.slick" %% "slick" % "3.2.1",
  "com.zaxxer" % "HikariCP" % "2.6.3",
  "commons-io" % "commons-io" % "2.5",
  "org.scalatest" %% "scalatest" % "2.2.3" % "test"
).map(_.exclude("org.slf4j", "slf4j-jdk14"))

TwirlKeys.templateFormats += ("tex" -> "org.stingray.contester.pwprinter.TexFormat")
