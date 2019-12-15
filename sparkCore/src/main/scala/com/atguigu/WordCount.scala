package com.atguigu

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(args: Array[String]): Unit = {
//    1.创建配置信息
    val conf = new SparkConf().setAppName("wc").setMaster("local[*]")//setMaster可以省略，在spark submit的时候临时加参数即可
//    2.创建sparkContext
    val sc = new SparkContext(conf)


      val spark = SparkSession.builder().appName("aa").master("local").getOrCreate()
//    3.处理
    //读取数据
    val lines = sc.textFile("D:\\JavaRelation\\Workpaces\\myproject\\sparkAtguigu\\sparkCore\\dongkun.txt")
    //压平数据
    val words = lines.flatMap(_.split(" "))
    //map(word,1)
    val k2v = words.map((_,1))
    //reduceByKey(word,x)
    val  result = k2v.reduceByKey(_+_)
    //展示输出
//    result.collect()
      result.foreach(println)
//    result.saveAsTextFile(args(1))
//    4.关闭连接
    sc.stop()
  }
}
