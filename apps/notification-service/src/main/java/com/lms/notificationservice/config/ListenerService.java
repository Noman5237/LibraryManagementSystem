package com.lms.notificationservice.config;

import com.lms.notificationservice.model.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ListenerService {
	
	Logger LOG = LoggerFactory.getLogger(ListenerService.class);
	
	@KafkaListener (topics = "order", groupId = "notification-consumer-group", containerFactory = "kafkaListenerContainerFactory")
	void listener(Notification notification) {
		LOG.info("Notification Received: {}", notification);
	}
	
}

