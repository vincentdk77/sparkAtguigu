import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object WordCount_withStatus {
  def main(args: Array[String]) {
    // 定义更新状态方法，参数 values 为当前批次单词频度，state 为以往批次单词频度
    val updateFunc = (values: Seq[Int], state: Option[Int]) => {
//      val currentCount = values.foldLeft(0)(_ + _)
      val previousCount = state.getOrElse(0)
      Some(previousCount + values.sum)
    }
    val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
    val ssc = new StreamingContext(conf, Seconds(3))
    ssc.checkpoint(".")//有状态的sparkstreaming必须要有checkpoint！！！
    val lines = ssc.socketTextStream("dk100", 9999)
    val words = lines.flatMap(_.split(" "))
    val pairs = words.map(word => (word, 1))
    // 使用 updateStateByKey 来更新状态，统计从运行开始以来单词总的次数
    val stateDstream = pairs.updateStateByKey[Int](updateFunc)
    stateDstream.print()
    //val wordCounts = pairs.reduceByKey(_ + _)
    //wordCounts.print()
    ssc.start() // Start the computation
    ssc.awaitTermination() // Wait for the computation to terminate
  }
}
