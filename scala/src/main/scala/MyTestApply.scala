//�����ļ�Ϊ/usr/local/scala/mycode/MyTestApply.scala
class Car(name: String) {
    def info() {
        println("Car name is "+ name)
    }
}
object Car {
    def apply(name: String) = new Car(name) //���ð�����Car�Ĺ��췽��
}
object MyTestApply{
    def main (args: Array[String]) {
    val mycar = Car("BMW") //���ð��������е�apply����		 mycar.info() //������Ϊ��Car name is BMW��
    }
}
