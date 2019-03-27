package com.client.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="DataPoint")
public class DataPointDomain {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private Timestamp time;
	private double capacity, power;
	private boolean greedy, knapsack;
	public DataPointDomain() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DataPointDomain(Timestamp time, double capacity, double power, boolean greedy, boolean knapsack) {
		this.time = time;
		this.capacity = capacity;
		this.power = power;
		this.greedy = greedy;
		this.knapsack = knapsack;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public double getCapacity() {
		return capacity;
	}
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
	public double getPower() {
		return power;
	}
	public void setPower(double power) {
		this.power = power;
	}
	public boolean isGreedy() {
		return greedy;
	}
	public void setGreedy(boolean greedy) {
		this.greedy = greedy;
	}
	public boolean isKnapsack() {
		return knapsack;
	}
	public void setKnapsack(boolean knapsack) {
		this.knapsack = knapsack;
	}

	
}
