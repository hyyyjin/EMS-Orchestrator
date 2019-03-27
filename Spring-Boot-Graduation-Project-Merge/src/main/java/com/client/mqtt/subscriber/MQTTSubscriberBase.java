package com.client.mqtt.subscriber;;

/**
 * MQTT Subscriber Base Interface
 */
public interface MQTTSubscriberBase {

	/**
	 * Subscribe message
	 * 
	 * @param topic
	 * @param jasonMessage
	 */
	public void subscribeMessage(String topic);

	/**
	 * Disconnect MQTT Client
	 */
	public void disconnect();
}
