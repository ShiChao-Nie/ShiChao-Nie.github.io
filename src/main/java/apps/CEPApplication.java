package apps;

import DO.OperatorAction;
import Manager.PatternManager;
import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import utils.KafkaProperty;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * User: nieshichao
 * Date: 2021/5/3
 */
public class CEPApplication {
    /**
     * 监测到站 12 小时未领件
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
//        DataStreamSource<String> streamSource = env.socketTextStream("42.192.46.170", 9092);
//                env.readTextFile("D:\\IdeaWorkSpace\\flink\\FinalDesign\\src\\main\\resources\\cep.txt");
        Properties properties = KafkaProperty.getProperties();
        DataStreamSource<String> streamSource = env.addSource(new FlinkKafkaConsumer<String>("test", new SimpleStringSchema(), properties));

        DataStream<OperatorAction> mapOperate = streamSource
                .map(str -> JSONObject.parseObject(str, OperatorAction.class))
                .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<OperatorAction>(Time.seconds(3)) {
                    @Override
                    public long extractTimestamp(OperatorAction element) {
                        return element.getActionTime().getTime();
                    }
                });
//        KeyedStream<OperatorAction, String> keyedStream = mapOperate.keyBy(OperatorAction::getMailNo);
//        keyedStream.print();
//        System.out.println("123123");
        // 400 后 5s 没有 500 操作
        Pattern<OperatorAction, OperatorAction> pattern = PatternManager.getPattern();
        PatternStream<OperatorAction> patternStream = CEP.pattern(mapOperate.keyBy(OperatorAction::getMailNo), pattern);
        patternStream.select(
                new PatternSelectFunction<OperatorAction, String>() {
                    @Override
                    public String select(Map<String, List<OperatorAction>> map) throws Exception {
                        return map.toString();
                    }
                }
        ).print();
        env.execute("go");
    }
}
