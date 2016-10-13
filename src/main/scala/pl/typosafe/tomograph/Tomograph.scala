package pl.typosafe.tomograph

import sbt._
import Keys._

object Tomograph {
  def implementGraph = TomographKeys.tomoghraphGraph := {
    val s = streams.value
    //val compilationResults = (compileIncremental in Compile).value

    (compile in Compile).value // Run compilation
    val prev = (previousCompile in Compile).value

    val projName = name.value

    s.log.info(s"Creating graph for $projName")
    val location = s.cacheDirectory

    val clazz = Tomograph.getClass

    val pageFile +: _ = Seq("index.html", "scripts/sigma.js", "scripts/tomograph.js").map {
      name =>
       // val stream = clazz.getResourceAsStream(name)
        val to =  location / name
       // IO.write(to, IO.readBytes(stream))
        to
    }

    DataProducer.produceInput(prev.analysis, location / "data.js")

    s.log.info(s"Graph for $projName created in ${pageFile.getAbsolutePath}")
  }

  def settings = Seq(implementGraph)
}
