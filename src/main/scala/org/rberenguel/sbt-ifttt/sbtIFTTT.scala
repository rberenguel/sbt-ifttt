package com.rberenguel.sbtifttt

import sbt._
import sbt.Keys.{sLog, target, compile, test}
import sbt.util.Logger
import sbtassembly.AssemblyKeys.assembly
import scala.util.{Try, Success, Failure}
import scalaj.http._


object SbtIFTTT extends AutoPlugin {
  override def trigger = allRequirements
  override val requires = plugins.JvmPlugin

  object autoImport extends IFTTTKeys

  import autoImport._
  override lazy val projectSettings: Seq[Setting[_]] = Seq(
    nCompile := notifyTask(compile in Compile, iftttPrintOnly, iftttKey, iftttEvent).value,
    nTest := notifyTask(test in Test, iftttPrintOnly, iftttKey, iftttEvent).value,
    nAssembly := notifyTask(assembly, iftttPrintOnly, iftttKey, iftttEvent).value
  )

  private def notifyTask[T](t: TaskKey[T], print: SettingKey[Boolean],
                            key: SettingKey[String], event: SettingKey[String]) = {
    Def.taskDyn {
      val c = (t).result.value
      val onlyPrint = print.value
      val url = s"https://maker.ifttt.com/trigger/${event.value}/with/key/${key.value}"
      val log = sLog.value
      Def.task {
        c match {
          case Inc(inc: Incomplete) =>
            triggerNotification(s"${t.key} has failed", onlyPrint, url, log)
            throw inc
          case Value(v) =>
            triggerNotification(s"${t.key} has succeeded", onlyPrint, url, log)
            v
        }
      }
    }
  }

  private def triggerNotification(msg: String, onlyPrint: Boolean, url: String, log: Logger) = {
    if(onlyPrint)
      log.info(scala.Console.BOLD + s"Test: $msg" + scala.Console.RESET)
    else
      Try {
        Http(url)
          .header("content-type", "application/json")
          .postData(s"""{"value1":"Sbt",
                      | "value2":"Task $msg",
                      | "value3": "https://www.scala-sbt.org/assets/sbt-logo.svg"}""".stripMargin)
          .asString
      } match {
        case Success(s) =>
          log.info(s"Notification executed (response: ${s.body})")
        case Failure(f) =>
          log.warn(s"Cannot connect to IFTTT (error: $f)")
      }
  }
}
