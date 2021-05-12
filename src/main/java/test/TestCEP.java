//package test;
//
//import org.apache.flink.api.common.functions.MapFunction;
//import org.apache.flink.cep.CEP;
//import org.apache.flink.cep.PatternSelectFunction;
//import org.apache.flink.cep.PatternStream;
//import org.apache.flink.cep.pattern.Pattern;
//import org.apache.flink.cep.pattern.conditions.IterativeCondition;
//import org.apache.flink.streaming.api.TimeCharacteristic;
//import org.apache.flink.streaming.api.datastream.DataStreamSource;
//import org.apache.flink.streaming.api.datastream.KeyedStream;
//import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
//import org.apache.flink.streaming.api.windowing.time.Time;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * User: nieshichao
// * Date: 2021/5/4
// */
//public class TestCEP {
//    public static void main(String[] args) throws Exception {
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
//        env.setParallelism(1);
//        DataStreamSource<String> streamSource = env.readTextFile("D:\\IdeaWorkSpace\\flink\\FinalDesign\\src\\main\\resources\\cep.txt");
//        KeyedStream<LoginEvent, Long> loginEventLongKeyedStream = streamSource
//                .map(new MapFunction<String, LoginEvent>() {
//                    @Override
//                    public LoginEvent map(String value) throws Exception {
//                        String[] split = value.split(",");
//                        LoginEvent loginEvent = new LoginEvent();
//                        loginEvent.setUserId(Long.parseLong(split[0]));
//                        loginEvent.setIp(split[1]);
//                        loginEvent.setEventType(split[2]);
//                        loginEvent.setEventTime(Long.parseLong(split[3]));
//                        return loginEvent;
//                    }
//                }).assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<LoginEvent>(Time.seconds(5)) {
//                    @Override
//                    public long extractTimestamp(LoginEvent element) {
//                        return element.eventTime * 1000;
//                    }
//                }).keyBy(LoginEvent::getUserId);
//        loginEventLongKeyedStream.print();
//        Pattern<LoginEvent, LoginEvent> within = Pattern.<LoginEvent>begin("begin").where(new IterativeCondition<LoginEvent>() {
//            @Override
//            public boolean filter(LoginEvent loginEvent, Context<LoginEvent> context) throws Exception {
//                return loginEvent.eventType.equals("fail");
//            }
//        })
//                .next("next").where(new IterativeCondition<LoginEvent>() {
//                    @Override
//                    public boolean filter(LoginEvent loginEvent, Context<LoginEvent> context) throws Exception {
//                        return loginEvent.eventType.equals("fail");
//                    }
//                })
//                .within(Time.seconds(3));
//        PatternStream<LoginEvent> pattern = CEP.pattern(loginEventLongKeyedStream, within);
//        SingleOutputStreamOperator<Warning> loginFailDataStream  = pattern.select(new PatternSelectFunction<LoginEvent, Warning>() {
//            @Override
//            public Warning select(Map<String, List<LoginEvent>> map) throws Exception {
//                LoginEvent begin = map.get("begin").iterator().next();
//                LoginEvent next = map.get("next").iterator().next();
//                Warning warning = new Warning();
//                warning.setUserId(begin.userId);
//                warning.setFirstFailTime(begin.eventTime);
//                warning.setLastFailTime(next.eventTime);
//                warning.setWarningMsg("123warn");
//                return warning;
//            }
//        });
//        loginFailDataStream.print();
//        env.execute("login fail with cep job");
//    }
//}
//
//class LoginEventt {
//    Long userId;
//    String ip;
//    String eventType;
//    Long eventTime;
//
//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//
//    public String getIp() {
//        return ip;
//    }
//
//    public void setIp(String ip) {
//        this.ip = ip;
//    }
//
//    public String getEventType() {
//        return eventType;
//    }
//
//    public void setEventType(String eventType) {
//        this.eventType = eventType;
//    }
//
//    public Long getEventTime() {
//        return eventTime;
//    }
//
//    public void setEventTime(Long eventTime) {
//        this.eventTime = eventTime;
//    }
//
//    @Override
//    public String toString() {
//        return "LoginEvent{" +
//                "userId=" + userId +
//                ", ip='" + ip + '\'' +
//                ", eventType='" + eventType + '\'' +
//                ", eventTime=" + eventTime +
//                '}';
//    }
//}
//
//class Warning {
//    Long userId;
//    Long firstFailTime;
//    Long lastFailTime;
//    String warningMsg;
//
//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//
//    public Long getFirstFailTime() {
//        return firstFailTime;
//    }
//
//    public void setFirstFailTime(Long firstFailTime) {
//        this.firstFailTime = firstFailTime;
//    }
//
//    public Long getLastFailTime() {
//        return lastFailTime;
//    }
//
//    public void setLastFailTime(Long lastFailTime) {
//        this.lastFailTime = lastFailTime;
//    }
//
//    public String getWarningMsg() {
//        return warningMsg;
//    }
//
//    public void setWarningMsg(String warningMsg) {
//        this.warningMsg = warningMsg;
//    }
//
//    @Override
//    public String toString() {
//        return "Warning{" +
//                "userId=" + userId +
//                ", firstFailTime=" + firstFailTime +
//                ", lastFailTime=" + lastFailTime +
//                ", warningMsg='" + warningMsg + '\'' +
//                '}';
//    }
//}