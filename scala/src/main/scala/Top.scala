//�����ļ�Ϊ/usr/local/scala/mycode/Top.scala
class  Top(name:String,subname:String){ //������
case class Nested(name:String) //Ƕ����
def show{
val c=new Nested(subname)
printf("Top %s includes a Nested %s\n",name,c.name)
}
}
val t = new Top("A","B")
t.show
