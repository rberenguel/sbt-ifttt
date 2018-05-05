lazy val root = (project in file("."))
  .enablePlugins(SbtIFTTT)
  .settings(
    scalaVersion := "2.11.7",
    version := "0.1",
    iftttPrintOnly := true,
    iftttEvent := "",
    iftttKey := ""
  )
