package es.rojocarmesi.streamator

import java.util.logging.Logger

import scala.concurrent.duration._

object Application extends App {

  case class CommandLineOpts(
                              inputFile: String = "/tmp/data.csv",
                              random: Boolean = true,
                              pause: Seq[Duration] = Seq(1 seconds, 2 seconds),
                              outputTopic: String = "data")

  val parser = new scopt.OptionParser[CommandLineOpts]("streamator") {
    head("streamator")

    opt[String]('i', "inputFile").required.valueName("<file>").action((x, c) =>
      c.copy(inputFile = x)).text("Input file")

    opt[Boolean]('r', "random").valueName("<boolean>").optional.action((x, c) =>
      c.copy(random = x)).text("Whether to use a random or fixed time between every chunck of data")

    opt[Seq[Duration]]('p', "pause").valueName("<duration>[,<duration>]").optional.action((x, c) =>
      c.copy(pause = x)).validate( x =>
        if (x.size <= 2) success
        else failure("One or two duration times are expected")
    ).text("Pause time between every production. For fixed time, only one value is required. For random time, two values are required as an interval")

    opt[String]('o', "outputTopic").valueName("<topic>").required.action((x, c) =>
      c.copy(outputTopic = x)).text("Output Kafka topic")

    checkConfig( c =>
      if (c.random && c.pause.size != 2) failure("For random pauses, 2 duration times are required")
      else success
    )

  }

  parser.parse(args, CommandLineOpts()) match {
    case Some(config) =>
      println(
        s"""=== CONFIG ===
          |Input: ${config.inputFile}
          |Random: ${config.random}
          |Pause: ${config.pause.mkString(",")}
          |Output: ${config.outputTopic}
        """.stripMargin)

      val coordinator = Coordinator(
        CoordinatorConfig(config.random, config.pause),
        Map("path" -> config.inputFile),
        Map("topic" -> config.outputTopic))

      Logger.getGlobal.info("Starting process")
      coordinator.start()

      Logger.getGlobal.info("Stoping process")
      coordinator.close()

    case None =>
  }

}

