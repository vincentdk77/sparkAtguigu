//�����ļ�Ϊ/usr/local/scala/mycode/Bird2.scala
trait Flyable {
		var maxFlyHeight:Int  //�����ֶ�
     def fly() //���󷽷�
def breathe(){ //����ķ���
         println("I can breathe")
     }
 }
trait HasLegs {
		val legs:Int   //�����ֶ�
     def move(){printf("I can walk with %d legs",legs)}
   }
class Animal(val category:String){
    def info(){println("This is a "+category)}
}
class Bird(flyHeight:Int) extends Animal("Bird") with Flyable with HasLegs{
var maxFlyHeight:Int = flyHeight //�������ʵĳ����ֶ�
val legs=2 //�������ʵĳ����ֶ�
     def fly(){
printf("I can fly at the height of %d",maxFlyHeight)
}//�������ʵĳ��󷽷�
}
