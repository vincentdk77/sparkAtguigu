import java.util
import java.util.Properties

import org.apache.spark.sql.SparkSession

object JDBCDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("JDBCDemo")
      .master("local[*]")
      .getOrCreate()
    //1.读
      //读操作 方式一
    val result = spark.read.format("jdbc")
      .option("url", "jdbc:mysql://localhost:3306/ssm-test?serverTimezone=GMT%2b8")
      .option("driver", "com.mysql.cj.jdbc.Driver")
      .option("user", "root")
      .option("password", "root")
      .option("dbTable", " (select emp_name ,email from tbl_emp ) t")
      .load
      //读操作 方式二
    val connectionProperties = new Properties()
    connectionProperties.put("user", "root")
    connectionProperties.put("password", "hive")
    val jdbcDF2 = spark.read.jdbc("jdbc:mysql://hadoop102:3306/rdd", "rddtable", connectionProperties)



    //2.写
    /**mode("")
      * Specifies the behavior when data or table already exists. Options include:
      *   - `overwrite`: overwrite the existing data.
      *   - `append`: append the data.
      *   - `ignore`: ignore the operation (i.e. no-op).
      *   - `error`: default option, throw an exception at runtime.
      */
      //写操作方式1
    result.write.mode("append").format("jdbc")
      .option("url", "jdbc:mysql://hadoop102:3306/rdd")
      .option("dbtable", "rddtable2")
      .option("user", "root")
      .option("password", "hive")
      .save()
      //写操作方式2
    jdbcDF2.write.jdbc("jdbc:mysql://hadoop102:3306/mysql", "db", connectionProperties)

      // Specifying create table column data types on write
    jdbcDF2.write
      .option("createTableColumnTypes", "name CHAR(64), comments VARCHAR(1024)")
      .jdbc("jdbc:mysql://hadoop102:3306/mysql", "db", connectionProperties)
  }
}
