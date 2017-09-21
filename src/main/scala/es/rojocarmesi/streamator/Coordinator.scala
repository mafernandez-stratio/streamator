package es.rojocarmesi.streamator

import java.util.logging.Logger

object Coordinator {
  def apply(
             coordinatorConfig: CoordinatorConfig,
             sourceConfig: Map[String, String],
             sinkConfig: Map[String, String]): Coordinator =
    new Coordinator(coordinatorConfig, sourceConfig, sinkConfig)
}

class Coordinator(
                   coordinatorConfig: CoordinatorConfig,
                   sourceConfig: Map[String, String],
                   sinkConfig: Map[String, String]) {

  val r = scala.util.Random

  val source = new CsvSource(sourceConfig)
  val sink = new KafkaSink(sinkConfig)


  def producePause() = if(coordinatorConfig.random){
    val min = coordinatorConfig.pause(0).toMillis
    val max = coordinatorConfig.pause(1).toMillis
    val interval = max - min
    val millis = (r.nextDouble()*interval).toLong+min
    Thread.sleep(millis)
  } else {
    Thread.sleep(coordinatorConfig.pause.head.toMillis)
  }

  def start(): Unit ={
    producePause()
    val delivery = source.get.map(sink.send(_))
    delivery.foreach(_ => start)
  }

  def close() = {
    source.close
    sink.close
  }

}
