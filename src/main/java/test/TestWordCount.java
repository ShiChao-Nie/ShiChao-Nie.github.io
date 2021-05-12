package test;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.Date;

/**
 * User: nieshichao
 * Date: 2021/5/3
 */
public class TestWordCount {
//    public static void main(String[] args) throws Exception {
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        DataStreamSource<String> streamSource = env.socketTextStream("42.192.46.170", 9999);
//        DataStream<Tuple2<String, Integer>> resultStream = streamSource.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
//            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
//                String[] strs = s.split(" ");
//                for (String str : strs) {
//                    collector.collect(new Tuple2<>(str, 1));
//                }
//            }
//        }).keyBy(tuple -> tuple.f0).sum(1);
//        resultStream.print();
//        env.execute("stream word count");
//    }
//    public static void main(String[] args) {
//        String time = "{\"date\":\"2021-05-04 10:11:12\"}";
//        Time time1 = JSONObject.parseObject(time, Time.class);
//        System.out.println(time1.getDate().getTime());
//    }
}

