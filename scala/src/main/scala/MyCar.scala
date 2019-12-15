//�����ļ�Ϊ/usr/local/scala/mycode/MyCar.scala
abstract class Car(val name:String) {
	   val carBrand:String //һ�������ֶ�
var age:Int=0
    def info() //���󷽷�
    def greeting() {
        println("Welcome to my car!")
    }
    def this(name:String,age:Int) {
        this(name)
        this.age=age
    }
}
//�����࣬�������캯�������˸���������캯��
// ����name�Ǹ������������Ĳ��������Ҳ����override
class BMWCar(override val name:String) extends Car(name) {
    override val carBrand = "BMW"  //���ظ�������ֶΣ�override��ѡ
    def info() {//���ظ�����󷽷���override�ؼ��ֿ�ѡ
        printf("This is a %s car. It has been used for %d year.\n", carBrand,age)
    }
  override def greeting() {//���ظ���ǳ��󷽷���override��ѡ
        println("Welcome to my BMW car!")
    }
}
//�����࣬�������캯�������˸���ĸ������캯��
class BYDCar(name:String,age:Int) extends Car(name,age) {
    val carBrand = "BYD" //���ظ�������ֶΣ�override��ѡ
    override def info() {//���ظ�����󷽷���override�ؼ��ֿ�ѡ
        printf("This is a %s car.It has been used for %d year.\n", carBrand,age)
    }
}
object MyCar{
    def main(args:Array[String]) {
        val car1 = new BMWCar("Bob's Car")
        val car2 = new BYDCar("Tom's Car",3)
        show(car1)
        show(car2)
}
//��������Ϊ�������ͣ����ݴ�������ľ����������ͣ�������Ӧ������ʵ�ֶ�̬
    def show(thecar:Car)={thecar.greeting; thecar.info()}
}
