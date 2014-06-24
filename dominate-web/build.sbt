name := "dominate-web"

version := "1.0.0"

scalaVersion := "2.10.4"

scalacOptions ++= Seq("-deprecation", "-feature")

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.2.2",
  "com.typesafe.play" %% "play-slick" % "0.7.0-M1"
)

fork in Test := false

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.2.0" % "test"
