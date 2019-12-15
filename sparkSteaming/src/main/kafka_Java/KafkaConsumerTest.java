import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerTest {
    public static void main(String[] args){
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop102:9092");
        //消费者组
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        //开启自动提交
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        //自动提交的延时
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        //订阅主题
//        consumer.subscribe(Arrays.asList("first","second"));//多个值可以这样写
        consumer.subscribe(Collections.singletonList("first"));//只有一个值的情况下这样写 不会报警告
        while (true) {
            //消费者会长轮询拉取数据，如果超过该时间没有拉取到数据，轮询时间就应该更长一点
            ConsumerRecords<String, String> records =consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
        }
        //不需要关闭consumer
    }
}
