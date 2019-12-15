import java.io.{BufferedReader, InputStreamReader}
import java.net.{ConnectException, Socket}

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver

class CustomerReceiver(host:String,port:Int) extends Receiver[String](StorageLevel.MEMORY_ONLY){
  //接收器启动时候调用
  override def onStart(): Unit = {
    new Thread("receiver"){
      override def run(): Unit = {
        //接收数据并提交给框架
        reveive()
      }
    }.start()
  }
  def reveive(): Unit = {
    var socket:Socket = null
    var input:String = null
    try{
      socket = new Socket(host,port)
      val reader = new BufferedReader(new InputStreamReader(socket.getInputStream))
      //读取数据
      input = reader.readLine()
      //当 receiver 没有关闭并且输入数据不为空，则循环发送数据给 Spark
      while (!isStopped() && input != null) {
        store(input)
        input = reader.readLine()
      }
      //跳出循环则关闭资源
      reader.close()
      socket.close()
      //重启任务
      restart("restart")

    }catch{
      case e:ConnectException => restart("restart")
      case t:Throwable => restart("restart")
    }
  }
  //接收器关闭的时候调用
  override def onStop(): Unit = {}
}
