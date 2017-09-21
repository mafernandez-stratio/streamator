package es.rojocarmesi.streamator

class CsvSource(config: Map[String, String]) extends Source(config) {

  val path = config.getOrElse("path", "/tmp/data.csv")
  val source = io.Source.fromFile(path)
  val iter = source.getLines()
  val separator = config.getOrElse("separator", ",")

  override def get = {
    if (iter.hasNext)
      Some(iter.next())
    else
      None
  }

  override def getBatch(n: Int) = {
    Some(iter.take(n).toSeq)
  }

  override def close(): Unit = source.close
}
