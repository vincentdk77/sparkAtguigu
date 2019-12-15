import kafka.serializer.StringDecoder
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object KafkaSparkStreaming {
  def main(args: Array[String]): Unit = {
    //1.创建 SparkConf 并初始化 SSC
    val sparkConf: SparkConf = new
        SparkConf().setMaster("local[*]").setAppName("KafkaSparkStreaming")
    val ssc = new StreamingContext(sparkConf, Seconds(5))
    //2.定义 kafka 参数
    val zookeeper = "hadoop102:2181,hadoop103:2181,hadoop104:2181"
    val topic = "source"
    val consumerGroup = "spark"
    //3.将 kafka 参数映射为 map
    val kafkaParam: Map[String, String] = Map[String, String](
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG ->"org.apache.kafka.common.serialization.StringDeserializer",
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG ->"org.apache.kafka.common.serialization.StringDeserializer",
      ConsumerConfig.GROUP_ID_CONFIG -> consumerGroup,
      "zookeeper.connect" -> zookeeper
    )
    //4.通过 KafkaUtil 创建 kafkaDSteam
    val kafkaDSteam: ReceiverInputDStream[(String, String)]
                    = KafkaUtils.createStream[String, String,StringDecoder, StringDecoder](
      ssc,
      kafkaParam,
      Map[String, Int](topic -> 3),
      StorageLevel.MEMORY_ONLY
    )
    //5.对 kafkaDSteam 做计算（WordCount）
    kafkaDSteam.foreachRDD {
      rdd => {
        val word: RDD[String] = rdd.flatMap(_._2.split(" "))
        val wordAndOne: RDD[(String, Int)] = word.map((_, 1))
        val wordAndCount: RDD[(String, Int)] = wordAndOne.reduceByKey(_ + _)
        wordAndCount.collect().foreach(println)
      } }
    //6.启动 SparkStreaming
    ssc.start()
    ssc.awaitTermination()
  }
}
