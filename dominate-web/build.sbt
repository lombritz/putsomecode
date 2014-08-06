name := "dominate-web"

version := "1.0.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.3.0",
  "com.typesafe.play" %% "play-slick" % "0.7.0-M1",
  "org.scalatestplus" %% "play" % "1.2.0"
)

fork in Test := false

lazy val root = (project in file(".")).enablePlugins(PlayScala)
