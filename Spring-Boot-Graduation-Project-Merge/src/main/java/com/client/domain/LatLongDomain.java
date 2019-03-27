package com.client.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="LatLong")
public class LatLongDomain {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private double lat, lng;
	private String srcEMA, description;
	public LatLongDomain() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LatLongDomain(double lat, double lng, String srcEMA) {
		this.lat = lat;
		this.lng = lng;
		this.srcEMA = srcEMA;
		this.description = "Demo EMA";
	}
	public LatLongDomain(double lat, double lng, String srcEMA, String description) {
		this.lat = lat;
		this.lng = lng;
		this.srcEMA = srcEMA;
		this.description = description;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public String getSrcEMA() {
		return srcEMA;
	}
	public void setSrcEMA(String srcEMA) {
		this.srcEMA = srcEMA;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
		
}
