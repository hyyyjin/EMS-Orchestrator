package com.client.structure;

public class EmaTriple {
	public String ema;
	public double power, price;
	public EmaTriple() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EmaTriple(String ema, double power) {
		this.ema = ema;
		this.power = power;
		this.price = 0;
	}
	public EmaTriple(String ema, double power, double price) {
		this.ema = ema;
		this.power = power;
		this.price = price;
	}
	public String getEma() {
		return ema;
	}
	public void setEma(String ema) {
		this.ema = ema;
	}
	public double getPower() {
		return power;
	}
	public void setPower(double power) {
		this.power = power;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
}
