import java.util.HashMap
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
object KafkaWordProducer {
  def main(args: Array[String]) {
    if (args.length < 4) {
      System.err.println("Usage: KafkaWordCountProducer <metadataBrokerList> <topic> " +
        "<messagesPerSec> <wordsPerMessage>")
      System.exit(1)
    }
    //messagesPerSec一秒钟发多少条消息，wordsPerMessage一条消息多少单词
    //localhost:9092 wordsender 3 5
    val Array(brokers, topic, messagesPerSec, wordsPerMessage) = args
    // Zookeeper connection properties
    val props = new HashMap[String, Object]()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)//提供kafka broker的地址
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")//指定value的序列化类
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")//指定key的序列化类
    val producer = new KafkaProducer[String, String](props)
   // Send some messages
    //每秒钟发三条消息，每条消息包含五个单词
    while(true) {
      (1 to messagesPerSec.toInt).foreach { messageNum =>
        val str = (1 to wordsPerMessage.toInt).map(x => scala.util.Random.nextInt(10).toString)
          .mkString(" ")
                    print(str)
                    println()
        val message = new ProducerRecord[String, String](topic, null, str)//key=null,value=str
        producer.send(message)
      }
     Thread.sleep(1000)
    }
  }
}
