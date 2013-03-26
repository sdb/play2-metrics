import sbt._
import Keys._
import sbtrelease._
import ReleasePlugin._
import ReleaseKeys._
import ReleaseStateTransformations._

object MetricsBuild extends Build {
  lazy val root = Project("play2-metrics", file(".")).settings(releaseSettings:_*).settings(
    releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      runTest,
      gitFlowReleaseStart,
      setReleaseVersion,
      commitReleaseVersion,
      publishArtifacts,
      gitFlowReleaseFinish,
      setNextVersion,
      commitNextVersion,
      pushChanges
    )
  )

  lazy val gitFlowReleaseStart: ReleaseStep = ReleaseStep(gitFlowReleaseAction(v => Seq("start", v)))
  lazy val gitFlowReleaseFinish: ReleaseStep = ReleaseStep(gitFlowReleaseAction(v => Seq("finish", "-m", v, v)))

  lazy val gitFlowReleaseAction : (String => Seq[String]) => (State => State) = (args) => { st: State =>
    val v = st.get(versions)
    val cmd = Process(Seq("git", "flow", "release") ++: args(v.get._1))
    cmd ! st.log
    st
  }
}