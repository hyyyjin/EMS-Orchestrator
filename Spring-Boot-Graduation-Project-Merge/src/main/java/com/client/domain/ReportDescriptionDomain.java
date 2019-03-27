package com.client.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(name="ReportDescription")
public class ReportDescriptionDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String rID, resouceID, deviceType, reportType, itemUnits, siScaleCode, marketContext, minPeriod, maxPeriod,
	itemDescription, state, qos;
	private Timestamp maxTime, minTime;
	private int dimming, priority;
	private double power, margin, generate, storage, maxValue, minValue, avgValue;
	private boolean onChange;
	@ElementCollection(fetch=FetchType.EAGER)
	private List<PowerAttributesDomain> powerAttributes;
	
	public ReportDescriptionDomain(ReportDescriptionDomain rd) {
		this.rID = rd.rID;
		this.resouceID = rd.resouceID;
		this.deviceType = rd.deviceType;
		this.reportType = rd.reportType;
		this.itemUnits = rd.itemUnits;
		this.siScaleCode = rd.siScaleCode;
		this.marketContext = rd.marketContext;
		this.minPeriod = rd.minPeriod;
		this.maxPeriod = rd.maxPeriod;
		this.itemDescription = rd.itemDescription;
		this.state = rd.state;
		this.qos = rd.qos;
		this.maxTime = rd.maxTime;
		this.minTime = rd.minTime;
		this.dimming = rd.dimming;
		this.priority = rd.priority;
		this.power = rd.power;
		this.margin = rd.margin;
		this.generate = rd.generate;
		this.storage = rd.storage;
		this.maxValue = rd.maxValue;
		this.minValue = rd.minValue;
		this.avgValue = rd.avgValue;
		this.onChange = rd.onChange;
		this.powerAttributes = rd.powerAttributes;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getrID() {
		return rID;
	}
	public void setrID(String rID) {
		this.rID = rID;
	}
	public String getResouceID() {
		return resouceID;
	}
	public void setResouceID(String resouceID) {
		this.resouceID = resouceID;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getItemUnits() {
		return itemUnits;
	}
	public void setItemUnits(String itemUnits) {
		this.itemUnits = itemUnits;
	}
	public String getSiScaleCode() {
		return siScaleCode;
	}
	public void setSiScaleCode(String siScaleCode) {
		this.siScaleCode = siScaleCode;
	}
	public String getMarketContext() {
		return marketContext;
	}
	public void setMarketContext(String marketContext) {
		this.marketContext = marketContext;
	}
	public String getMinPeriod() {
		return minPeriod;
	}
	public void setMinPeriod(String minPeriod) {
		this.minPeriod = minPeriod;
	}
	public String getMaxPeriod() {
		return maxPeriod;
	}
	public void setMaxPeriod(String maxPeriod) {
		this.maxPeriod = maxPeriod;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Timestamp getMaxTime() {
		return maxTime;
	}
	public void setMaxTime(Timestamp maxTime) {
		this.maxTime = maxTime;
	}
	public Timestamp getMinTime() {
		return minTime;
	}
	public void setMinTime(Timestamp minTime) {
		this.minTime = minTime;
	}
	public String getQos() {
		return qos;
	}
	public void setQos(String qos) {
		this.qos = qos;
	}
	public int getDimming() {
		return dimming;
	}
	public void setDimming(int dimming) {
		this.dimming = dimming;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public double getPower() {
		return power;
	}
	public void setPower(double power) {
		this.power = power;
	}
	public double getMargin() {
		return margin;
	}
	public void setMargin(double margin) {
		this.margin = margin;
	}
	public double getGenerate() {
		return generate;
	}
	public void setGenerate(double generate) {
		this.generate = generate;
	}
	public double getStorage() {
		return storage;
	}
	public void setStorage(double storage) {
		this.storage = storage;
	}
	public double getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}
	public double getMinValue() {
		return minValue;
	}
	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}
	public double getAvgValue() {
		return avgValue;
	}
	public void setAvgValue(double avgValue) {
		this.avgValue = avgValue;
	}
	public boolean isOnChange() {
		return onChange;
	}
	public void setOnChange(boolean onChange) {
		this.onChange = onChange;
	}
	public List<PowerAttributesDomain> getPowerAttributes() {
		return powerAttributes;
	}
	public void setPowerAttributes(List<PowerAttributesDomain> powerAttributes) {
		this.powerAttributes = powerAttributes;
	}
	public ReportDescriptionDomain() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {

		JSONObject json = new JSONObject();

		try {
			if (qos != null) {
				json.put("rID", getrID());
				json.put("resourceID", getResouceID());
				json.put("deviceType", getDeviceType());
				json.put("reportType", getReportType());
				json.put("itemUnits", getItemUnits());
				json.put("siScaleCode", getSiScaleCode());
				json.put("marketContext", getMarketContext());
				json.put("minPeriod", getMinPeriod());
				json.put("maxPeriod", getMaxPeriod());
				json.put("onChange", isOnChange());
				json.put("itemDescription", getItemDescription());
				json.put("powerAttributes", getPowerAttributes());
				json.put("state", getState());
				json.put("qos", getQos());
				json.put("power", getPower());
				json.put("dimming", getDimming());
				json.put("margin", getMargin());
				json.put("generate", getGenerate());
				json.put("storage", getStorage());
				json.put("maxValue", getMaxValue());
				json.put("minValue", getMinValue());
				json.put("avgValue", getAvgValue());
				json.put("maxTime", getMaxTime());
				json.put("minTime", getMinTime());
				json.put("priority", getPriority());
			} else {
				
				System.out.println("여기맞지?");
				
				json.put("rID", getrID());
				json.put("resourceID", getResouceID());
				json.put("deviceType", getDeviceType());
				json.put("reportType", getReportType());
				json.put("itemUnits", getItemUnits());
				json.put("siScaleCode", getSiScaleCode());
				json.put("marketContext", getMarketContext());
				json.put("minPeriod", getMinPeriod());
				json.put("maxPeriod", getMaxPeriod());
				json.put("onChange", isOnChange());
				json.put("itemDescription", getItemDescription());
				json.put("powerAttributes", powerAttributes.toString());
				json.put("state", getState());
				json.put("power", getPower());
				json.put("dimming", getDimming());
				json.put("margin", getMargin());
				json.put("generate", getGenerate());
				json.put("storage", getStorage());
				json.put("maxValue", getMaxValue());
				json.put("minValue", getMinValue());
				json.put("avgValue", getAvgValue());
				json.put("maxTime", getMaxTime());
				json.put("minTime", getMinTime());
				json.put("priority", getPriority());
			}
			return json.toString();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return "wrong";
		}

	}
}
