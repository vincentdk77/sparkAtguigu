//�����ļ�Ϊ/usr/local/scala/mycode/Bird.scala
trait Flyable {
		var maxFlyHeight:Int  //�����ֶ�
     def fly() //���󷽷�
def breathe(){ //����ķ���
         println("I can breathe")
     }
 }
class Bird(flyHeight:Int) extends Flyable{
var maxFlyHeight:Int = flyHeight  //�������ʵĳ����ֶ�
     def fly(){
printf("I can fly at the height of %d",maxFlyHeight)
} //�������ʵĳ��󷽷�
}
