//�����ļ�Ϊ/usr/local/scala/mycode/Counter2.scala
class Counter {
    private var value = 0 
    private var name = ""
private var step = 1 //��������Ĭ�ϵݽ�����
println("the main constructor")
    def this(name: String){ //��һ������������
        this() //������������
        this.name = name
		   printf("the first auxiliary constructor,name:%s\n",name)
    }
    def this (name: String,step: Int){ //�ڶ�������������
        this(name) //����ǰһ������������
        this.step = step
printf("the second auxiliary constructor,name:%s,step:%d\n",name,step)
    }
    def increment(step: Int): Unit = { value += step}
    def current(): Int = {value}
}
