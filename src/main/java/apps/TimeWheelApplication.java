package apps;

import DO.OperatorAction;
import Manager.StreamJoiner;
import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * User: nieshichao
 * Date: 2021/5/6
 */
public class TimeWheelApplication {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        DataStreamSource<String> overtimeSource = env.socketTextStream("42.192.46.170", 9999);
        DataStreamSource<String> streamSource = env.socketTextStream("42.192.46.170", 9092);

        DataStream<OperatorAction> mapOperate = streamSource
                .map(str -> JSONObject.parseObject(str, OperatorAction.class))
                .filter(new FilterFunction<OperatorAction>() {
                    @Override
                    public boolean filter(OperatorAction value) throws Exception {
                        return value.getActionCode() == 500;
                    }
                })
                .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<OperatorAction>(Time.seconds(3)) {
                    @Override
                    public long extractTimestamp(OperatorAction element) {
                        return element.getActionTime().getTime();
                    }
                });

        DataStream<OperatorAction> overtimeOperate = overtimeSource
                .map(str -> JSONObject.parseObject(str, OperatorAction.class))
                .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<OperatorAction>(Time.seconds(3)) {
                    @Override
                    public long extractTimestamp(OperatorAction element) {
                        return element.getActionTime().getTime();
                    }
                });

        StreamJoiner.getJoinedStream(overtimeOperate,mapOperate);
        env.execute("go");

    }
}
