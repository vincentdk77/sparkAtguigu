//�����ļ�Ϊ/usr/local/scala/mycode/TestBreak.scala
import util.control.Breaks._ //����Breaks������з���
val array = Array(1,3,10,5,4)
breakable{
for(i<- array){
	   if(i>5) break //����breakable����ֹforѭ�����൱��Java�е�break
println(i)
	}
}
// �����for��佫���1��3

for(i<- array){
	breakable{
		if(i>5) break //����breakable����ֹ����ѭ�����൱��Java��continue		println(i)
	}
}
// �����for��佫���1��3��5��4
