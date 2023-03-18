package org.egov.digit.kafka;

import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

	/*
	 * Uncomment the below line to start consuming record from kafka.topics.consumer
	 * Value of the variable kafka.topics.consumer should be overwritten in
	 * application.properties
	 */
	 @KafkaListener(topics = {"payment-status"})
	public void listen(final HashMap<String, Object> record) {

		log.info("+++++++++++++++++++++++++++++++++++++++++++++++++");
		log.info("RECORD IS ==> "+record);
		log.info("+++++++++++++++++++++++++++++++++++++++++++++++++");

	}
}
