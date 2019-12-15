import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object WordCount {
  def main(args: Array[String]): Unit = {
    val sparkConf  = new SparkConf().setMaster("local[*]").setAppName("testSparkStreaming")
    //可以通过SparkConf或者sparkContext来初始化ssc
//    val sc = new SparkContext(sparkConf)
//    val ssc = new StreamingContext(sc,Seconds(5))
    val ssc = new StreamingContext(sparkConf,Seconds(5))
    //该方法就是一个接收器，可以自定义接收器！
//    val lineDStream = ssc.socketTextStream("dk100",9999)
    val lineDStream = ssc.receiverStream(new CustomerReceiver("dk100",9999) )
    val wordDStream = lineDStream.flatMap(a=>a.split(" "))
    val mapDStream = wordDStream.map(a=>(a,1))
    val reduceDStream = mapDStream.reduceByKey((x,y)=>x+y)
    //打印
    reduceDStream.print()
    //启动 SparkStreamingContext
    ssc.start()
    ssc.awaitTermination()

  }

}
