package com.client.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.client.message.Profile;

@Entity(name="Profile")
public class ProfileDomain {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long pid;
	private String srcEMA;
	private String profileName;
	@ElementCollection(fetch=FetchType.EAGER)
	private List<TransportDomain> transports;
	
	public ProfileDomain() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ProfileDomain(String srcEMA) {
		this();
		this.srcEMA = srcEMA;
	}


	public ProfileDomain(String srcEMA, String profileName, List<TransportDomain> transports) {
		this.srcEMA = srcEMA;
		this.profileName = profileName;
		this.transports = transports;
	}

	public long getPid() {
		return pid;
	}


	public void setPid(long pid) {
		this.pid = pid;
	}


	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public List<TransportDomain> getTransports() {
		return transports;
	}

	public void setTransports(List<TransportDomain> transports) {
		this.transports = transports;
	}

	public String getSrcEMA() {
		return srcEMA;
	}

	public void setSrcEMA(String srcEMA) {
		this.srcEMA = srcEMA;
	}
	
	public void updateProfile(Profile p) {
		this.profileName = p.getProfileName();
		this.transports = new ArrayList<>();
		for (String s :p.getTransports()) {
			transports.add(new TransportDomain(s));
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{\"profileName" + "\":" + "\"" + profileName + "\"" + ", " + "\"transports" + "\":"
				+ transports + "}";
	}
	
}
