package com.lms.notificationservice.config;

import com.lms.notificationservice.model.Notification;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

@Configuration
@EnableKafka
public class ConsumerConfig {
	
	@Value ("${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;
	
	@Value ("${spring.kafka.consumer.group-id}")
	private String groupId;
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Notification> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Notification> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
	
	@Bean
	public ConsumerFactory<String, Notification> consumerFactory() {
		
		try (JsonDeserializer<Notification> deserializer = new JsonDeserializer<>(Notification.class)) {
			deserializer.setRemoveTypeHeaders(false);
			deserializer.addTrustedPackages("*");
			deserializer.setUseTypeMapperForKey(true);
			
			Map<String, Object> config = Map.of(BOOTSTRAP_SERVERS_CONFIG,
			                                    bootstrapAddress,
			                                    KEY_DESERIALIZER_CLASS_CONFIG,
			                                    StringDeserializer.class,
			                                    VALUE_DESERIALIZER_CLASS_CONFIG,
			                                    deserializer,
			                                    GROUP_ID_CONFIG,
			                                    groupId);
			
			return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
		}
	}
	
	@Bean
	public NewTopic order() {
		return TopicBuilder.name("notifications")
		                   .partitions(1)
		                   .replicas(1)
		                   .build();
	}
}
