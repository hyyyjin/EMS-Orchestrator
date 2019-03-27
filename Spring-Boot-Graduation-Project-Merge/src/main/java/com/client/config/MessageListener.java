package com.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.client.mqtt.subscriber.MQTTSubscriberBase;

/**
 * run subscriber with default topic
 */
@Component
public class MessageListener implements Runnable{

	@Autowired
	MQTTSubscriberBase subscriber;
	public static final String defaultTopic = "/EMAP/SERVER_EMS1/#";
	
	@Override
	public void run() {
		subscriber.subscribeMessage(defaultTopic);		
	}

}
