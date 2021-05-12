package utils;

import java.util.Properties;

/**
 * User: nieshichao
 * Date: 2021/5/5
 */
public class KafkaProperty {
    private static volatile Properties properties;

    private KafkaProperty() {

    }

    public static Properties getProperties() {
        if (properties == null) {
            synchronized (KafkaProperty.class) {
                if (properties == null) {
                    properties = new Properties();
                    properties.setProperty("bootstrap.servers", "42.192.46.170:9092");
                    properties.setProperty("group.id", "consumer-group");
                    properties.setProperty("key.deserializer",
                            "org.apache.kafka.common.serialization.StringDeserializer");
                    properties.setProperty("value.deserializer",
                            "org.apache.kafka.common.serialization.StringDeserializer");
                    properties.setProperty("auto.offset.reset", "latest");
                }
            }
        }
        return properties;
    }
}
