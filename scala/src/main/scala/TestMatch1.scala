//�����ļ�Ϊ/usr/local/scala/mycode/TestMatch1.scala
import scala.io.StdIn._
println("Please input a country:")
val country=readLine()
country match{
		case "China" => println("�й�")
		case "America" => println("����")
		case "Japan" => println("�ձ�")
		case _ => println("�Ҳ���ʶ!")
}
