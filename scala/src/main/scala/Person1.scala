//�����ļ�Ϊ/usr/local/scala/mycode/Person1.scala
class Person(val name:String){
    private val id = Person.newPersonId() //�����˰��������еķ���
    def info() {
        printf("The id of %s is %d.\n",name,id)
    }
}
object Person {
    private var lastId = 0  //һ���˵���ݱ��
    def newPersonId() = {
        lastId +=1
        lastId
    }
    def main(args: Array[String]) {
        val person1 = new Person("Lilei")
        val person2 = new Person("Hanmei")
        person1.info()
        person2.info()
    }
}
