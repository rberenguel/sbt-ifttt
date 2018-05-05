lazy val commonSettings = Seq(
  version in ThisBuild := "0.1.0",
  organization in ThisBuild := "com.rberenguel"
)

lazy val root = (project in file("."))
  .settings(
    commonSettings,
    name := "sbt-ifttt",
    description := "sbt plugin to trigger IFTTT webhook events",
    licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
    sbtPlugin := true,
    libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.4.0",
    scriptedLaunchOpts += ("-Dplugin.version=" + version.value),
    scriptedLaunchOpts ++= sys.process.javaVmArguments.filter(
      a => Seq("-Xmx", "-Xms", "-XX", "-Dsbt.log.noformat").exists(a.startsWith)
    ),
    scriptedBufferLog := false,
    addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.6"),
    publishMavenStyle := false,
    bintrayRepository := "sbt-plugins",
    bintrayPackage := "sbt-ifttt"
  )
