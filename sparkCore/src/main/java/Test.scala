object Test {
  def main(args: Array[String]): Unit = {
    // 获取对象类型
    println(classOf[String])
    val s = "zhangsan"
    println(s.getClass.getName) //这种是Java中反射方式得到类型
    println(s.isInstanceOf[String])
    println(s.asInstanceOf[String])

    val arr = new Array(10)
    val arr2 = Array(10)
    println("============")
    println(arr.length)
    println(arr2.length)
    1111

  }

}
