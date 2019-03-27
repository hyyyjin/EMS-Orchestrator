package com.client.service;

public interface MessageService {
	
	/**
	 * parse JSON format message from broker and save/reply message.
	 * 
	 * @param topic name of the topic on the message was published to
	 * @param message actual JSON message
	 */
	public void handleMessage(String topic, String message);

	/**
	 * distribute event to destination EMA.
	 * called by {@link com.client.controller.MainRestController#createEvent createEvent}
	 * in <code>MainRestController</code> or {@link com.client.service.DRAlgorithm#demandResponse demandResponse}
	 * in <code>ScheduledTast</code>.
	 * @param destEMA
	 * @param time
	 * @param threshold
	 */
	public void distributeEvent(String destEMA, String time, double threshold);
}
