//�����ļ�Ϊ/usr/local/scala/mycode/Box.scala
import scala.collection.mutable.Stack
class Box[T]{
	val elems:Stack[T]=Stack()
	def remove:Option[T]={ //���صĶ��������Option���ͽ��а�װ
		if (elems.isEmpty) None else Some(elems.pop) 
	}
	def append(a1:T){elems.push(a1)}
}
