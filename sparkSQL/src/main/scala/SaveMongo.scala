import java.util

import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.WriteConfig

object SaveMongo {
  def main(args: Array[String]): Unit = {
    val map = new util.HashMap[String,String]()
    map.put("uri","mongodb://10.139.8.236/coordinates.dk_tfXl")
    map.put("replaceDocument","true")
    map.put("forceInsert","true")
    val writeConfig = WriteConfig.create(map)
    //1.将rdd转化为以document为元素的rdd
    val dbObjectRDD = xlTfResultBeanRdd.map(xlTfResultBean=>{
      val dbObject = xlTfResultBean.getDocument()
      dbObject
    })
    //    val collection = new MongoClient().getDatabase("").getCollection("")
    //2.存mongoDB
    MongoSpark.save(dbObjectRDD,writeConfig)
  }

}
