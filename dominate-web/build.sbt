name := "dominate-web"

version := "1.0.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.2.2",
  "com.typesafe.play" %% "play-slick" % "0.7.0",
  "org.scalatestplus" % "play_2.10" % "1.2.0"
)

fork in Test := false

lazy val root = (project in file(".")).enablePlugins(PlayScala)
