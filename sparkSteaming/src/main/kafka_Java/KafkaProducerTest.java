import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class KafkaProducerTest {
    public static void main(String[] args){
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop102:9092");//kafka 集群，broker-list
        props.put(ProducerConfig.ACKS_CONFIG, "all");  //-1,0,1
        props.put(ProducerConfig.RETRIES_CONFIG, 1);//重试次数
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);//批次大小 字节
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);//等待时间  ms
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);//RecordAccumulator 缓冲区大小
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++) {
            producer.send(new ProducerRecord<String, String>("first",Integer.toString(i), Integer.toString(i)),
                    new Callback() {
                //回调函数，该方法会在 Producer 收到 ack 时调用，为异步调用
                @Override
                public void onCompletion(RecordMetadata metadata,Exception exception) {
                    if (exception == null) {
                        System.out.println("success->" + metadata.offset());
                    } else {
                        exception.printStackTrace();
                    }
                }
            });
        }
        //必须关闭，！！，因为jvm退出了，没到等待时间1ms或者没到指定的批次大小，就不会发送数据。有了close方法内部会帮我们清空缓存
        producer.close();
    }
}
