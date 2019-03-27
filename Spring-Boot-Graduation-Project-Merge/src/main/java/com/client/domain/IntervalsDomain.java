package com.client.domain;

import javax.persistence.Embeddable;

import org.json.JSONException;
import org.json.JSONObject;

@Embeddable
public class IntervalsDomain {
	
	private String duration, uid;
	private double value;
	public IntervalsDomain() {
		super();
		// TODO Auto-generated constructor stub
	}
	public IntervalsDomain(String duration, String uid, double value) {
		// TODO Auto-generated constructor stub
		this.duration = duration;
		this.uid = uid;
		this.value = value;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		JSONObject json = new JSONObject();

		try {
			
			json.put("duration", getDuration());
			json.put("uid", getUid());
			json.put("value", getValue());

			return json.toString();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return "wrong";
		}
	}
}
