lazy val root = (project in file("."))
  .settings(
    name := "sbt-ifttt",
    organization := "com.rberenguel",
    version := "0.1-SNAPSHOT",
    sbtPlugin := true,
    libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.4.0",
    scriptedLaunchOpts += ("-Dplugin.version=" + version.value),
    scriptedLaunchOpts ++= sys.process.javaVmArguments.filter(
      a => Seq("-Xmx", "-Xms", "-XX", "-Dsbt.log.noformat").exists(a.startsWith)
    ),
    scriptedBufferLog := false,
    addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.6")
  )
