package Manager;

import DO.OperatorAction;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * User: nieshichao
 * Date: 2021/5/6
 */
public class PatternManager {
    public static Pattern<OperatorAction, OperatorAction> getPattern() {
        Pattern<OperatorAction, OperatorAction> startPattern = Pattern
                .<OperatorAction>begin("start").where(
                        new SimpleCondition<OperatorAction>() {
                            @Override
                            public boolean filter(OperatorAction operatorAction) throws Exception {
                                return operatorAction.getActionCode() == 400;
                            }
                        }).notNext("notNext").where(new SimpleCondition<OperatorAction>() {
                    @Override
                    public boolean filter(OperatorAction operatorAction) throws Exception {
                        return operatorAction.getActionCode() == 500;
                    }
                }).within(Time.seconds(5));
        return startPattern;
    }
}
