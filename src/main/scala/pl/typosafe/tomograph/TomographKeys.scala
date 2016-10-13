package pl.typosafe.tomograph

import sbt.TaskKey

object TomographKeys {
  val tomoghraphGraph = TaskKey[Unit]("tomoghraphGraph", "Launch tomograph and display graph!")
}
