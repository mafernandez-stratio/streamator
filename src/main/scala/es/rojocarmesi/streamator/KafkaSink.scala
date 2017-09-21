package es.rojocarmesi.streamator

import java.util.{Properties, UUID}

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

class KafkaSink(config: Map[String, String]) extends Sink(config) {

  val topic = config.getOrElse("topic", "data")

  val props = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)

  implicit def convert_UUID_To_String(uuid: UUID): String = String.valueOf(uuid)

  override def send(row: String): Unit = {
    val record = new ProducerRecord[String, String](topic, UUID.randomUUID(), row)
    producer.send(record)
  }

  override def sendBatch(rows: Seq[String]) = rows.foreach(send(_))

  override def close() = producer.close
}