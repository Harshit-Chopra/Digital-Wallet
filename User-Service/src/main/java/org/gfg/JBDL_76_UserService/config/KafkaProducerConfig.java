package org.gfg.JBDL_76_UserService.config;

import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.gfg.JBDL_76_UserService.dto.SenderReceiverInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

        @Bean(name = "factory1")
        public ProducerFactory<String, SenderReceiverInfo> producerFactory() {
            Map<String, Object> configProps = new HashMap<>();
            configProps.put(
                    ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                    "localhost:9092");
            configProps.put(
                    ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                    StringSerializer.class);
            configProps.put(
                    ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                    JsonSerializer.class);
            return new DefaultKafkaProducerFactory<>(configProps);
        }


        @Bean(name = "kafkaTemplateJson")
        public KafkaTemplate<String, SenderReceiverInfo> kafkaTemplate() {
            return new KafkaTemplate<>(producerFactory());
        }

        @Bean(name = "kafkaTemplate")
        public KafkaTemplate<String, String> kafkaTemplateForString() {
            Map<String, Object> configProps = new HashMap<>();
            configProps.put(
                    ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                    "localhost:9092");
            configProps.put(
                    ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                    StringSerializer.class);
            configProps.put(
                    ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                    StringSerializer.class);
            return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(configProps));
        }

}
