package com.client.mqtt.subscriber;

import java.sql.Timestamp;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.client.config.MQTTConfig;
import com.client.service.MessageService;

/**
 * MQTT Subscriber Class
 */

@Component
public class MQTTSubscriber extends MQTTConfig implements MqttCallback, MQTTSubscriberBase {

	private String brokerUrl = null;
	final private String colon = ":";
	final private String clientId = "SEMA sub";

	private MqttClient mqttClient = null;
	private MqttConnectOptions connectionOptions = null;
	private MemoryPersistence persistence = null;
	
	private static final Logger logger = LoggerFactory.getLogger(MQTTSubscriber.class);
		
	@Autowired
	MessageService msgHandler;

	public MQTTSubscriber() {
		this.config();
	}
	
	public MQTTSubscriber(String broker, Integer port, Boolean ssl, Boolean withUserNamePass) {
		this.config(broker, port, ssl, withUserNamePass);;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.
	 * Throwable)
	 */
	@Override
	public void connectionLost(Throwable cause) {
		logger.info("Connection Lost");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.paho.client.mqttv3.MqttCallback#messageArrived(java.lang.String,
	 * org.eclipse.paho.client.mqttv3.MqttMessage)
	 */
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// Called when a message arrives from the server that matches any
		// subscription made by the client
		String text = new String(message.getPayload());
		if (!quiet) {
			// convert simple string to pretty printed json string
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String jsonStr = gson.toJson(gson.fromJson(text, Object.class));
			String time = new Timestamp(System.currentTimeMillis()).toString();
			System.out.println();
			System.out.println("***********************************************************************");
			System.out.println("Message Arrived at Time: " + time + "  Topic: " + topic
								+ "\nMessage: "	+ jsonStr);
			System.out.println("***********************************************************************");
			System.out.println();
		}
		if (!"".equals(text))
			msgHandler.handleMessage(topic, text);		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.paho.client.mqttv3.MqttCallback#deliveryComplete(org.eclipse.paho
	 * .client.mqttv3.IMqttDeliveryToken)
	 */
	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// Leave it blank for subscriber

	}

	@Override
	public void subscribeMessage(String topic) {
		try {
			this.mqttClient.subscribe(topic, this.qos);
		} catch (MqttException me) {
			me.printStackTrace();
		} catch (Exception me) {
			me.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			this.mqttClient.disconnect();
		} catch (MqttException me) {
			logger.error("ERROR", me);
		}
	}

	@Override
	protected void config(String broker, Integer port, Boolean ssl, Boolean withUserNamePass) {

		String protocal = this.TCP;
		if (true == ssl) {
			protocal = this.SSL;
		}

		this.brokerUrl = protocal + broker + colon + port;
		this.persistence = new MemoryPersistence();
		this.connectionOptions = new MqttConnectOptions();

		try {
			this.mqttClient = new MqttClient(brokerUrl, clientId, persistence);
			this.connectionOptions.setCleanSession(true);
			if (true == withUserNamePass) {
				if (password != null) {
					this.connectionOptions.setPassword(this.password.toCharArray());
				}
				if (userName != null) {
					this.connectionOptions.setUserName(this.userName);
				}
			}
			this.mqttClient.connect(this.connectionOptions);
			this.mqttClient.setCallback(this);
		} catch (MqttException me) {
			me.printStackTrace();
		}

	}

	@Override
	protected void config() {

		this.brokerUrl = this.TCP + this.broker + colon + this.port;
		this.persistence = new MemoryPersistence();
		this.connectionOptions = new MqttConnectOptions();
		try {
			this.mqttClient = new MqttClient(brokerUrl, clientId, persistence);
			this.connectionOptions.setCleanSession(true);
			this.mqttClient.connect(this.connectionOptions);
			this.mqttClient.setCallback(this);
		} catch (MqttException me) {
			me.printStackTrace();
		}

	}
}
