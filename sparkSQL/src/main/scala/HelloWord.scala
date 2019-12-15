import org.apache.spark.sql.SparkSession

object HelloWord {
  def main(args: Array[String]): Unit = {
    //1.获取sparksession
    val spark = SparkSession.builder().master("local[*]").appName("helloWorld").getOrCreate()

    //2.生成dataFrame
    val df = spark.read.json("sparkSQL/people.json")
    //导入隐式转换
    import spark.implicits._
    //3.SQ语法
      //创建表
    df.createTempView("people")
      //执行sql
    spark.sql("select * from people where age>20").show()
    //3.DSL 风格语法
    df.show()
    df.filter($"age" < 20).show()

    spark.close()
  }
}
