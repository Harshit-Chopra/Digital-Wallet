package org.gfg.TxnService.KafkaConfig;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.gfg.TxnService.dto.SenderReceiverInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig2 {
    @Value("${bootstrap.server}")
    private String bootstrapAddress;
    @Value("${txn.kafka.groupId}")
    private String groupId;

    @Bean(name = "consumerFactory2")
    public ConsumerFactory<String, SenderReceiverInfo> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                groupId);
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);

//        props.put(JsonDeserializer.TRUSTED_PACKAGES, "org.gfg.TxnService");

        JsonDeserializer<SenderReceiverInfo> jsonDeserializer = new JsonDeserializer<>(SenderReceiverInfo.class);
        jsonDeserializer.addTrustedPackages("org.gfg.TxnService"); // Allow deserialization of any class (use carefully)
        jsonDeserializer.setUseTypeHeaders(false); // Don't use headers for the type

        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false); // can mot be done via properties
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
    }


    @Bean(name= "factory2")
    public ConcurrentKafkaListenerContainerFactory<String, SenderReceiverInfo>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, SenderReceiverInfo> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }
}

