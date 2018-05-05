package com.rberenguel.sbtifttt

import sbt._

trait IFTTTKeys {
  lazy val iftttKey = settingKey[String]("User key")
  lazy val iftttEvent = settingKey[String]("Event to trigger")
  lazy val iftttPrintOnly = settingKey[Boolean]("Only print, for scripted run")
  lazy val nCompile = taskKey[Unit]("Compile and notify")
  lazy val nTest = taskKey[Unit]("Test and notify")
  lazy val nAssembly = taskKey[Unit]("Assembly and notify")
}
