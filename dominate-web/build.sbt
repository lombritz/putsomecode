name := "dominate-web"

version := "1.0.0"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.3.0",
  "com.typesafe.play" %% "play-slick" % "0.8.0-RC2",
  "org.scalatestplus" %% "play" % "1.2.0"
)

fork in Test := false

lazy val root = (project in file(".")).enablePlugins(PlayScala)
