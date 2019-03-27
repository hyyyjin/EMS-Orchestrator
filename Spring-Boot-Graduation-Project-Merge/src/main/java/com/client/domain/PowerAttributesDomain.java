package com.client.domain;

import javax.persistence.Embeddable;

import org.json.JSONException;
import org.json.JSONObject;

@Embeddable
public class PowerAttributesDomain {
	private double hertz, voltage;
	private boolean ac;

	public PowerAttributesDomain() {
		super();
		// TODO Auto-generated constructor stub
	}

	public double getHertz() {
		return hertz;
	}

	public void setHertz(double hertz) {
		this.hertz = hertz;
	}

	public double getVoltage() {
		return voltage;
	}

	public void setVoltage(double voltage) {
		this.voltage = voltage;
	}

	public boolean isAc() {
		return ac;
	}

	public void setAc(boolean ac) {
		this.ac = ac;
	}

	@Override
	public String toString() {

		JSONObject json = new JSONObject();

		try {
			json.put("hertz", getHertz());
			json.put("voltage", getVoltage());
			json.put("ac", ac);

			return json.toString();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return "wrong";
		}

	}
}
