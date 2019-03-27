package com.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MQTT Core Configuration
 */
@ConfigurationProperties(prefix="config.mqtt")
public abstract class MQTTConfig {

	// field for MQTT broker address
	protected final String broker = "localhost";
//	protected final String broker = "192.168.11.1";
//	protected final String broker = "166.104.28.51";
	protected final int qos = 2;
	protected Boolean hasSSL = false; /* By default SSL is disabled */
	protected Integer port = 1883; /* Default port */
	protected final String userName = "testUserName";
	protected final String password = "demoPassword";
	protected final String TCP = "tcp://";
	protected final String SSL = "ssl://";
	
	protected final boolean quiet = true; /* console print option */

	/**
	 * Custom Configuration
	 * 
	 * @param broker
	 * @param port
	 * @param ssl
	 * @param withUserNamePass
	 */
	protected abstract void config(String broker, Integer port, Boolean ssl, Boolean withUserNamePass);

	/**
	 * Default Configuration <br>
	 * Usually called by bean
	 */
	protected abstract void config();
}
