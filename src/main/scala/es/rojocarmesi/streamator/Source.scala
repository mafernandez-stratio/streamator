package es.rojocarmesi.streamator

abstract class Source(config: Map[String, String]){

  def get: Option[String]

  def getBatch(n: Int = 1): Option[Seq[String]]

  def close()

}

