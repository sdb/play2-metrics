name := "play2-metrics"

organization := "com.github.sdb"

scalaVersion := "2.10.0"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "play" %% "play" % "2.1.0" % "provided",
  "com.yammer.metrics" % "metrics-core" % "2.2.0" % "provided"
)

scalaSource in Compile <<= baseDirectory { (base) => base / "app" }

resourceDirectory in Compile <<= baseDirectory { (base) => base / "conf" }

scalaSource in Test <<= baseDirectory { (base) => base / "test" }

licenses := Seq("Apache License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))

homepage := Some(url("http://sdb.github.com/play2-metrics/"))

publishTo <<= version { v: String =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { x => false }

pomExtra := (
  <scm>
    <url>git@github.com:sdb/play2-metrics.git</url>
    <connection>scm:git:git@github.com:sdb/play2-metrics.git</connection>
  </scm>
  <developers>
    <developer>
      <id>sdb</id>
      <name>Stefan De Boey</name>
      <url>https://github.com/sdb</url>
    </developer>
  </developers>
)
