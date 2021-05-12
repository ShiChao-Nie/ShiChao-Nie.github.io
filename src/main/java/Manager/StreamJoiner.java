package Manager;

import DO.OperatorAction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.functions.co.ProcessJoinFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

import java.util.List;

/**
 * User: nieshichao
 * Date: 2021/5/6
 */
public class StreamJoiner {
    /**
     * OvertimeStream interval Join OperationStream
     */
    public static void getJoinedStream(DataStream<OperatorAction> overtimeStream, DataStream<OperatorAction> operatorStream) {
        DataStream<OperatorAction> joinerStream = overtimeStream.keyBy(OperatorAction::getMailNo)
                .intervalJoin(operatorStream.keyBy(OperatorAction::getMailNo))
                .between(Time.seconds(-30), Time.seconds(0)).process(
                        new ProcessJoinFunction<OperatorAction, OperatorAction, OperatorAction>() {
                            @Override
                            public void processElement(OperatorAction left, OperatorAction right, Context ctx, Collector<OperatorAction> out) throws Exception {
                                System.out.println("left：" + left + "right: " + right);
                            }
                        }
                );
    }
}
