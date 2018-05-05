# sbt-ifttt

sbt-ifttt is an [sbt](http://www.scala-sbt.org/) plugin to trigger an
[IFTTT](https://ifttt.com/maker_webhooks)
[webhook](https://ifttt.com/maker_webhooks) after the tasks

* `compile` (via running `nCompile`)
* `test` (via `nTest`)
* `assembly` (via `nAssembly`)

# Requirements

* `sbt` (version >= 0.13.5 should do it, although I have only tried with
  1.1.4)
* [sbt-assembly](https://github.com/sbt/sbt-assembly) plugin (to make
  `nAssembly` work)

# Usage

The actions defined above will trigger the webhook passing:
* _Value 1: sbt_ (it's used as title)
* _Value 2: message_ (task has finished or task has failed)
* _Value 3: sbt icon_ (can appear in notifications)

Assuming you have an account (it's free), you can create a webhook applet in
IFTTT [here](https://ifttt.com/create):

1. Click on _this_ and search for __webhook_
2. Choose _Receive a web request_
3. Choose the event name (I have _command\_line\_notify_ since I use it for
   other events)
4. Choose the action service. In my case, I have a _notification_, that triggers
   my watch

You can choose whatever available as a service. Switch your lights' colour, send
you an email... Anything available via IFTTT as an action service.

To configure the plugin, you will need the event name (in step 3 above) and your
access key. You can find your access key in the webhooks page by pressing
_Documentation_ (top right).

You can build the plugin locally by cloning this repository and running `sbt
publishLocal`. As soon as I figured how to publish it I will do it :)

Then, add it to a project by adding

```
addSbtPlugin("com.rberenguel" % "sbt-ifttt" % "0.1-SNAPSHOT")
```

to `project/plugins.sbt` and activate it by adding 

```scala
project
  .enablePlugins(SbtIFTTT)
  .settings(iftttPrintOnly := false,
            iftttKey := "YOUR_KEY",
            iftttEvent := "YOUR_EVENT")
```

Alternatively you can add it as a global plugin by adding

```
addSbtPlugin("com.rberenguel" % "sbt-ifttt" % "0.1-SNAPSHOT")
```
to any file ending in `.sbt` in `~/.sbt/1.0/plugins/` and adding the configuration

```
iftttPrintOnly := false
iftttKey := "YOUR_KEY"
iftttEvent := "YOUR_EVENT"
```
to any file ending in `.sbt` (custom is `global.sbt`) in `~/.sbt/1.0/`.

# Caveats

This is the first time I tried to write a plugin, so it can probably be improved
in a million ways. Help me with a PR!
