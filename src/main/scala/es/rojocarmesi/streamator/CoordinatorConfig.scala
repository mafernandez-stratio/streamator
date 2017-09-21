package es.rojocarmesi.streamator

import scala.concurrent.duration.Duration

case class CoordinatorConfig(random: Boolean, pause: Seq[Duration])
