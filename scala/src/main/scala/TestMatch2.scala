//�����ļ�Ϊ/usr/local/scala/mycode/TestMatch2.scala
for (elem <- List(6,9,0.618,"Spark","Hadoop",'Hello)){
	val str  = elem match  {
	    case i: Int => i + " is an int value."//ƥ�����͵�ֵ������ֵ��i
	    case d: Double => d + " is a double value." //ƥ�両���͵�ֵ
		case "Spark"=>"Spark is found." //ƥ���ض����ַ���
		case s: String => s + " is a string value." //ƥ�������ַ���
		case _ =>"unexpected value��"+ elem  //�����϶���ƥ��
}
	  println(str)
}
