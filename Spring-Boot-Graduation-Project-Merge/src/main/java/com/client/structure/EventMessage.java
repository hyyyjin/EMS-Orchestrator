package com.client.structure;

/**
 * simple model attribute for {@link com.client.controller.MainRestController#createEvent createEvent}
 */
public class EventMessage {
	public String destEMA;
	public String startYMD;
	public String starttime;
	public double threshold;
	
	public EventMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EventMessage(String destEMA, String startYMD, String starttime, double threshold) {
		this.destEMA = destEMA;
		this.startYMD = startYMD;
		this.starttime = starttime;
		this.threshold = threshold;
	}
	public String getDestEMA() {
		return destEMA;
	}
	public void setDestEMA(String destEMA) {
		this.destEMA = destEMA;
	}
	public String getStartYMD() {
		return startYMD;
	}
	public void setStartYMD(String startYMD) {
		this.startYMD = startYMD;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public double getThreshold() {
		return threshold;
	}
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	@Override
	public String toString() {
		return "Event [destEMA=" + destEMA + ", startYMD=" + startYMD + ", starttime=" + starttime + ", threshold="
				+ threshold + "]";
	}
	
	
}
