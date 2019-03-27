package com.client.mqtt.publisher;

/**
 * MQTT Publisher Configuration class
 */
public interface MQTTPublisherBase {

	/**
	 * Publish message
	 * 
	 * @param topic
	 * @param jasonMessage
	 */
	public void publishMessage(String topic, String message);

	/**
	 * Disconnect MQTT Client
	 */
	public void disconnect();

}
