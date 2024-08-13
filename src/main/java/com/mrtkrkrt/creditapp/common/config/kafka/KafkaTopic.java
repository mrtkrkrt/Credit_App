package com.mrtkrkrt.creditapp.common.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class KafkaTopic {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value(value = "${kafka.user.topic}")
    private String userServiceTopic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        log.info("bootstrapServers: {}", bootstrapServers);
        Map<String, Object> map = new HashMap<>();
        map.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(map);
    }

    @Bean
    public NewTopic topic() {
        return new NewTopic(userServiceTopic, 1, (short) 1);
    }
}
