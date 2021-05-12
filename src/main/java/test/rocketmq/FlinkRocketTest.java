//package test.rocketmq;
//
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//
//import java.util.Properties;
//
///**
// * User: nieshichao
// * Date: 2021/5/6
// */
//public class FlinkRocketTest {
//    public static void main(String[] args) {
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//
//        // enable checkpoint
//        env.enableCheckpointing(3000);
//
//        Properties consumerProps = new Properties();
//        consumerProps.setProperty(RocketMqConfig.NAME_SERVER_ADDR, "localhost:9876");
//        consumerProps.setProperty(RocketMqConfig.CONSUMER_GROUP, "c002");
//        consumerProps.setProperty(RocketMqConfig.CONSUMER_TOPIC, "flink-source2");
//
//        Properties producerProps = new Properties();
//        producerProps.setProperty(RocketMqConfig.NAME_SERVER_ADDR, "localhost:9876");
//
//        env.addSource(new RocketMQSource(new SimpleKeyValueDeserializationSchema("id", "address"), consumerProps))
//                .name("rocketmq-source")
//                .setParallelism(2)
//                .process(new ProcessFunction<Map, Map>() {
//                    @Override
//                    public void processElement(Map in, Context ctx, Collector<Map> out) throws Exception {
//                        HashMap result = new HashMap();
//                        result.put("id", in.get("id"));
//                        String[] arr = in.get("address").toString().split("\\s+");
//                        result.put("province", arr[arr.length-1]);
//                        out.collect(result);
//                    }
//                })
//                .name("upper-processor")
//                .setParallelism(2)
//                .addSink(new RocketMQSink(new SimpleKeyValueSerializationSchema("id", "province"),
//                        new DefaultTopicSelector("flink-sink2"), producerProps).withBatchFlushOnCheckpoint(true))
//                .name("rocketmq-sink")
//                .setParallelism(2);
//
//        try {
//            env.execute("rocketmq-flink-example");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
