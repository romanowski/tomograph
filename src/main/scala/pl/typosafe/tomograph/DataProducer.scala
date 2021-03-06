package pl.typosafe.tomograph

import java.io.File

import sbt.IO
import sbt.inc.Analysis

object DataProducer {

  def produceInput(from: Analysis, to: File): Unit ={

    val all = Math.sqrt(from.stamps.sources.size)
    var nr = 0

    def nodes = from.stamps.sources.keys.map{
      file =>
        nr += 1
        val x = nr % all
        val y = nr / all
        s"""{"label": "${file.getName}", "id": "${file.getAbsolutePath}", "size": 2, "x": $x, "y": $y}"""
    }

    var id = 0

    def edges = from.relations.internalSrcDep.all.map{
      case (from, to) =>
        id += 1
        s"""{"source": "${from.getAbsolutePath}", "target": "${to.getAbsolutePath}", "id": "$id"}"""
    }


    val data = s"""tomographJsonData = {
      |   "edges":[${edges.mkString(", ")}],
      |   "nodes":[${nodes.mkString(",")}]
      |}""".stripMargin

    IO.write(to, data)
  }

}
