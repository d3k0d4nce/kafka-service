package ru.kishko.restaurantservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.kishko.dtoservice.dtos.RestaurantDTO;
import ru.kishko.dtoservice.dtos.ReviewDTO;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${spring.kafka.review-group-id}")
    private String reviewGroupId;

    @Value("${spring.kafka.restaurant-group-id}")
    private String restaurantGroupId;

    private Map<String, Object> commonConsumerConfig(String groupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return props;
    }

    @Bean
    public ConsumerFactory<Integer, ReviewDTO> reviewConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(commonConsumerConfig(reviewGroupId), new IntegerDeserializer(), new JsonDeserializer<>(ReviewDTO.class));
    }

    @Bean
    public ConsumerFactory<Integer, RestaurantDTO> restaurantConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(commonConsumerConfig(restaurantGroupId), new IntegerDeserializer(), new JsonDeserializer<>(RestaurantDTO.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Integer, ReviewDTO> reviewKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, ReviewDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(reviewConsumerFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Integer, RestaurantDTO> restaurantKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, RestaurantDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(restaurantConsumerFactory());
        return factory;
    }
}