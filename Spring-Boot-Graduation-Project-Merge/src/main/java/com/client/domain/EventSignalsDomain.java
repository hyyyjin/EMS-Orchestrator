package com.client.domain;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="EventSignals")
public class EventSignalsDomain {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String signalName, signalType, signalID, unit;
	private double currentValue, threshold, capacity, price;
	@ElementCollection(fetch=FetchType.EAGER)
	private List<IntervalsDomain> intervals;
	public EventSignalsDomain() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public EventSignalsDomain(String signalName, String signalType, String signalID, String unit, double currentValue,
			double threshold, double capacity, double price, List<IntervalsDomain> intervals) {
		this.signalName = signalName;
		this.signalType = signalType;
		this.signalID = signalID;
		this.unit = unit;
		this.currentValue = currentValue;
		this.threshold = threshold;
		this.capacity = capacity;
		this.price = price;
		this.intervals = intervals;
	}
	public EventSignalsDomain(EventSignalsDomain es) {
		this.signalName = es.signalName;
		this.signalType = es.signalType;
		this.signalID = es.signalID;
		this.unit = es.unit;
		this.currentValue = es.currentValue;
		this.threshold = es.threshold;
		this.capacity = es.capacity;
		this.price = es.price;
		this.intervals = es.intervals;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSignalName() {
		return signalName;
	}
	public void setSignalName(String signalName) {
		this.signalName = signalName;
	}
	public String getSignalType() {
		return signalType;
	}
	public void setSignalType(String signalType) {
		this.signalType = signalType;
	}
	public String getSignalID() {
		return signalID;
	}
	public void setSignalID(String signalID) {
		this.signalID = signalID;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public double getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(double currentValue) {
		this.currentValue = currentValue;
	}
	public double getThreshold() {
		return threshold;
	}
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	public double getCapacity() {
		return capacity;
	}
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public List<IntervalsDomain> getIntervals() {
		return intervals;
	}
	public void setIntervals(List<IntervalsDomain> intervals) {
		this.intervals = intervals;
	}
	@Override
	public String toString() {
		return "{\"intervals" + "\":" +  getIntervals() + ", "
				+ "\"signalName" + "\":" + "\"" + getSignalName() + "\"" + ", "
				+"\"signalType" + "\":" + "\"" + getSignalType() + "\"" + ", "
				+"\"signalID" + "\":" + "\"" + getSignalID() + "\"" + ", "
				+"\"currentValue" + "\":" + "\"" + getCurrentValue() + "\"" + ", "
				+"\"threshold" + "\":" + "\"" + getThreshold() + "\"" + ", "
				+"\"capacity" + "\":" + "\"" + getCapacity() + "\"" + ", "
				+"\"price" + "\":" + "\"" + getPrice() + "\"" + ", "
				+"\"unit" + "\":" + "\"" + getUnit() + "\"" + "}";
	}
}
