package com.example.apiservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.kishko.dtoservice.dtos.RestaurantDTO;
import ru.kishko.dtoservice.dtos.ReviewDTO;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${kafka.topic.review}")
    private String reviewTopic;

    @Value("${kafka.topic.restaurant}")
    private String restaurantTopic;

    private Map<String, Object> commonProducerConfig() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return configProps;
    }

    @Bean
    public ProducerFactory<Integer, ReviewDTO> reviewProducerFactory() {
        return new DefaultKafkaProducerFactory<>(commonProducerConfig());
    }

    @Bean
    public ProducerFactory<Integer, RestaurantDTO> restaurantProducerFactory() {
        return new DefaultKafkaProducerFactory<>(commonProducerConfig());
    }

    @Bean
    public KafkaTemplate<Integer, RestaurantDTO> restaurantKafkaTemplate() {
        return new KafkaTemplate<>(restaurantProducerFactory());
    }

    @Bean
    public KafkaTemplate<Integer, ReviewDTO> reviewKafkaTemplate() {
        return new KafkaTemplate<>(reviewProducerFactory());
    }

    @Bean
    public NewTopic reviews() {
        return TopicBuilder.name(reviewTopic)
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic restaurants() {
        return TopicBuilder.name(restaurantTopic)
                .partitions(1)
                .build();
    }
}
