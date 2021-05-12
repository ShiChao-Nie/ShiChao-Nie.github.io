package test;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import utils.KafkaProperty;

import java.util.Properties;

/**
 * User: nieshichao
 * Date: 2021/5/5
 */
public class TestKafka {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties properties = KafkaProperty.getProperties();
        DataStreamSource<String> test = env.addSource(new FlinkKafkaConsumer<String>("test", new SimpleStringSchema(), properties));
        test.print();
        env.execute("kafka test");
    }
}
