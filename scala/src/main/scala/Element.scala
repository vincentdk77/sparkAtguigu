//�����ļ�Ϊ/usr/local/scala/mycode/Element.scala
abstract class Element{
type T //��������ͳ�Ա
	   var value:T //������ֶΣ�����ΪT
	   def show:Unit //���󷽷�����Ҫ���ݾ��������T����ʵ��
}
class IntEle(var value:Int) extends Element{
type T = Int
	   def show{printf("My value is %d.\n",value)} //T��Int��ʱ�����
}
class StringEle(var value:String) extends Element{
type T = String
	   def show{printf("My value is %s.\n",value)}//T��String��ʱ�����
}
