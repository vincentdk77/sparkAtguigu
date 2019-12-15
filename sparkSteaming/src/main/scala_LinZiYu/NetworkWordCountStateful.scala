package org.apache.spark.examples.streaming
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.storage.StorageLevel
object NetworkWordCountStateful {
  def main(args: Array[String]) {
    //定义状态更新函数  values为当前的词频统计，state为先前的词频统计
    //Option如果没有值则为None，有值则为Some
    val updateFunc = (values: Seq[Int], state: Option[Int]) => {
      val currentCount = values.foldLeft(0)(_ + _)
      val previousCount = state.getOrElse(0)
      Some(currentCount + previousCount)
    }
//    StreamingExamples.setStreamingLogLevels()  //设置log4j日志级别
    val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCountStateful")
    val sc = new StreamingContext(conf, Seconds(5))
    //设置检查点，检查点具有容错机制   会生成很多文件
    sc.checkpoint("file:///usr/local/spark/mycode/streaming/stateful/")
    val lines = sc.socketTextStream("localhost", 9999)
    val words = lines.flatMap(_.split(" "))
    val wordDstream = words.map(x => (x, 1))
    val stateDstream = wordDstream.updateStateByKey[Int](updateFunc)
    stateDstream.print()
    sc.start()
    sc.awaitTermination()
  }
}

