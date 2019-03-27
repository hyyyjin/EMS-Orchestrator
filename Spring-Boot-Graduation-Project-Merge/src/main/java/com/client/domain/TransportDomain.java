package com.client.domain;

import javax.persistence.Embeddable;

@Embeddable
public class TransportDomain {
	private String transportName;

	public TransportDomain() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TransportDomain(String transportName) {
		// TODO Auto-generated constructor stub
		this.transportName = transportName;
	}

	public String getTransportName() {
		return transportName;
	}

	public void setTransportName(String transportName) {
		this.transportName = transportName;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{\"transportName" + "\":" + "\"" + transportName + "\"}";
	}
	
}
