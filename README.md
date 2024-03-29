# sbt-ifttt

[ ![Download](https://api.bintray.com/packages/rberenguel/sbt-plugins/sbt-ifttt/images/download.svg) ](https://bintray.com/rberenguel/sbt-plugins/sbt-ifttt/_latestVersion)

---

I don't plan on maintaining or re-releasing for newer versions of sbt after bintray deprecation. Feel free to fork if you need something similar 😃

---


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

Install by adding 

```
resolvers += Resolver.bintrayIvyRepo("rberenguel", "sbt-plugins")
```
and
```
addSbtPlugin("com.rberenguel" % "sbt-ifttt" % "0.1.0")
```
either at project or global level (more details below).

The actions defined above will trigger the webhook passing:
* _Value 1: sbt_ (it's used as title)
* _Value 2: message_ (task has finished or task has failed)
* _Value 3: sbt icon_ (can appear in notifications)

Assuming you have an account (it's free), you can create a webhook applet in
IFTTT [here](https://ifttt.com/create):

1. Click on _this_ and search for _webhook_
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
publishLocal`.

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

# Why

This was inspired by the following tweet by Rúnar Bjarnason:

<blockquote class="twitter-tweet" data-lang="en"><p lang="en" dir="ltr">Just another day at the office. <a src="https://t.co/1iaPzIjQYj">pic.twitter.com/1iaPzIjQYj</a></p>&mdash; Rúnar (@runarorama) <a href="https://twitter.com/runarorama/status/988460928298569728?ref_src=twsrc%5Etfw">April 23, 2018</a></blockquote>

At the time I was running very long computations with Spark, and wanted to be
notified on my watch when the cluster was finished and ready for the next step.
Long `sbt` builds/assembly/test only seemed a natural extension once I got IFTTT
working, and since I had never written a plugin, this was as good a first time
as any.

# Caveats

This is the first time I tried to write a plugin, so it can probably be improved
in a million ways. Help me with a PR!
