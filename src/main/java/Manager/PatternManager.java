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
    public static Pattern<Shipment, Shipment> getPattern() {
        Pattern<Shipment, Shipment> startPattern = Pattern
                .<Shipment>begin("start").where(
                        new SimpleCondition<Shipment>() {
                            @Override
                            public boolean filter(Shipment operatorAction) throws Exception {
                                return shipment.getGmtCreate() != null && shipment.getShipmentCreateTime() == null;
                            }
                        }).notNext("notNext").where(new SimpleCondition<Shipment>() {
                    @Override
                    public boolean filter(Shipment shipment) throws Exception {
                        return return shipment.getGmtCreate() != null && shipment.getShipmentCreateTime() != null;
                    }
                }).within(Time.hours(3));
        return startPattern;
    }
}
