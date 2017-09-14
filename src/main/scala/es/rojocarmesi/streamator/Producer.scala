package es.rojocarmesi.streamator

import org.apache.kafka.clients.producer._
import scala.concurrent.duration._

// TODO: Allow chunck of data
class Producer extends App {

  case class CommandLineOpts(
    inputFile: String = "/tmp/data.csv",
    random: Boolean = true,
    pause: Seq[Duration] = Seq(1 seconds, 2 seconds),
    outoutTopic: String = "data")

  val parser = new scopt.OptionParser[CommandLineOpts]("streamator") {
    head("streamator")

    opt[String]('i', "inputFile").action((x, c) =>
      c.copy(inputFile = x)).text("Input file")

    opt[Boolean]('r', "random").action((x, c) =>
      c.copy(random = x)).text("Whether to use a random or fixed time between every chunck of data")

    opt[Seq[Duration]]('p', "pause").action((x, c) =>
      c.copy(pause = x)).text("Pause time between every production. For fixed time, only one value is required. For random time, two values are required as an interval")

    opt[String]('o', "outoutTopic").action((x, c) =>
      c.copy(outoutTopic = x)).text("Output Kafka topic")
  }

  parser.parse(args, CommandLineOpts()) match {
    case Some(clo) =>
    case None =>
  }

}

