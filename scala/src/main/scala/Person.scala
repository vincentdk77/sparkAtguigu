//�����ļ�Ϊ/usr/local/scala/mycode/Person.scala
object Person {
    private var lastId = 0  //һ���˵���ݱ��
    def newPersonId() = {
        lastId +=1
        lastId
    }
}
