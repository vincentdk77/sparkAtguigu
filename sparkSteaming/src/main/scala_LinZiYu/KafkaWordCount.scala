package org.apache.spark.examples.streaming
import org.apache.spark._
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.kafka.KafkaUtils

object KafkaWordCount{
  def main(args:Array[String]){
//    StreamingExamples.setStreamingLogLevels()
    val sc = new SparkConf().setAppName("KafkaWordCount").setMaster("local[2]")
    val ssc = new StreamingContext(sc,Seconds(10))
    //设置检查点，如果存放在HDFS上面，则写成类似ssc.checkpoint("/user/hadoop/checkpoint")这种形式，但是，要启动hadoop
    ssc.checkpoint("file:///usr/local/spark/mycode/kafka/checkpoint")
    val zkQuorum = "localhost:2181" //Zookeeper服务器地址
    val group = "1"  //topic所在的group，可以设置为自己想要的名称，比如不用1，而是val group = "test-consumer-group"
    //如果有多个topic用逗号隔开，如"aaa,bbb"
    val topics = "wordsender"  //topics的名称
    val numThreads = 1  //每个topic的分区数
    //转化成map，传递给工具类
    val topicMap =topics.split(",").map((_,numThreads.toInt)).toMap
///**producer的shell执行命令
//  * $ cd /usr/local/spark $ /usr/local/spark/bin/spark-submit \ >
//  * --driver-class-path /usr/local/spark/jars/*:/usr/local/spark/jars/kafka/* \ >
//  * --class "org.apache.spark.examples.streaming.KafkaWordProducer" \ >
//  * /usr/local/spark/mycode/kafka/target/scala-2.11/simple-project_2.11-1.0.jar \ >
//  * localhost:9092 wordsender 3 5
//  */
    val lineMap = KafkaUtils.createStream(ssc,zkQuorum,group,topicMap)
    //将key去掉。因为key是null，我们只要value
    val lines = lineMap.map(_._2)
    val words = lines.flatMap(_.split(" "))
    val pair = words.map(x => (x,1))
    //这行代码的含义在下一节的窗口转换操作中会有介绍
    val wordCounts = pair.reduceByKeyAndWindow(_ + _,_ - _,Minutes(2),Seconds(10),2)
    wordCounts.print
    ssc.start
    ssc.awaitTermination
  }
}
