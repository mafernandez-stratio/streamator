package es.rojocarmesi.streamator

abstract class Sink(config: Map[String, String]){

  def send(row: String)

  def sendBatch(rows: Seq[String])

  def close()

}

