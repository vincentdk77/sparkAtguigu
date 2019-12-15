import org.apache.spark._
import org.apache.spark.streaming._
object WordCountStreaming {
  def main(args: Array[String]) {
    val sparkConf = new SparkConf().setAppName("WordCountStreaming").setMaster("local[2]")  //����Ϊ��������ģʽ��2���̣߳�һ����������һ����������
    val ssc = new StreamingContext(sparkConf, Seconds(2))   // ʱ����Ϊ2��
    val lines = ssc.textFileStream("file:///usr/local/spark/mycode/streaming/logfile")  //������ñ����ļ�����ȻҲ���Բ���HDFS�ļ�
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)
    wordCounts.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
