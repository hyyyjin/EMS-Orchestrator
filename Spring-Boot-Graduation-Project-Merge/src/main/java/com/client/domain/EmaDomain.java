package com.client.domain;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.client.message.*;

@Entity(name="Ema")
public class EmaDomain {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String srcEMA, destEMA, requestID, service, version;
	private Timestamp time;
	private String responseDescription, duration, registrationID;
	private int responseCode;
	private String profileName, transportName;
	private boolean reportOnly, xmlSignature, httpPullModel;
	private String type;
	private String responseRequired;
	
	
	public EmaDomain() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmaDomain(EmaDomain ema) {
		this.srcEMA = ema.srcEMA;
		this.destEMA = ema.destEMA;
		this.requestID = ema.requestID;
		this.service = ema.service;
		this.version = ema.version;
		this.time = ema.time;
		this.responseDescription = ema.responseDescription;
		this.duration = ema.duration;
		this.registrationID = ema.registrationID;
		this.responseCode = ema.responseCode;
		this.profileName = ema.profileName;
		this.transportName = ema.transportName;
		this.reportOnly = ema.reportOnly;
		this.xmlSignature = ema.xmlSignature;
		this.httpPullModel = ema.httpPullModel;
		this.type = ema.type;
		this.responseRequired = ema.responseRequired;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSrcEMA() {
		return srcEMA;
	}
	public void setSrcEMA(String srcEMA) {
		this.srcEMA = srcEMA;
	}
	public String getDestEMA() {
		return destEMA;
	}
	public void setDestEMA(String destEMA) {
		this.destEMA = destEMA;
	}
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getResponseDescription() {
		return responseDescription;
	}
	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getRegistrationID() {
		return registrationID;
	}
	public void setRegistrationID(String registrationID) {
		this.registrationID = registrationID;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getTransportName() {
		return transportName;
	}
	public void setTransportName(String transportName) {
		this.transportName = transportName;
	}
	public boolean isReportOnly() {
		return reportOnly;
	}
	public void setReportOnly(boolean reportOnly) {
		this.reportOnly = reportOnly;
	}
	public boolean isXmlSignature() {
		return xmlSignature;
	}
	public void setXmlSignature(boolean xmlSignature) {
		this.xmlSignature = xmlSignature;
	}
	public boolean isHttpPullModel() {
		return httpPullModel;
	}
	public void setHttpPullModel(boolean httpPullModel) {
		this.httpPullModel = httpPullModel;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getResponseRequired() {
		return responseRequired;
	}
	public void setResponseRequired(String responseRequired) {
		this.responseRequired = responseRequired;
	}
	
	public void updateEma(ConnectRegistration conn, ConnectedPartyRegistration cr) {
		this.srcEMA = conn.getSrcEMA();
		this.destEMA = cr.getSrcEMA();
		this.requestID = conn.getRequestID();
		this.version = conn.getVersion();
		
		this.responseCode = cr.getResponseCode();
		this.responseDescription = cr.getResponseDescription();
		this.duration = cr.getDuration();
		this.service = cr.getService();
		this.time = Timestamp.valueOf(cr.getTime());
	}
	
	public void updateEma(CreatePartyRegistration create, CreatedPartyRegistration created) {
		this.transportName = create.getTransportName();
		this.reportOnly = create.isReportOnly();
		this.httpPullModel = create.isHttpPullModel();
		this.xmlSignature = create.isXmlSignature();
		this.requestID = create.getRequestID();
		this.profileName = create.getProfileName();
		
		this.responseCode = created.getResponseCode();
		this.responseDescription = created.getResponseDescription();
		this.service = created.getService();
		this.time = Timestamp.valueOf(created.getTime());
	}
	
	public void updateEma(RegisterReport rt, RegisteredReport rdt) {
		this.requestID = rt.getRequestID();
		this.type = rt.getType();
		
		this.responseCode = rdt.getResponseCode();
		this.responseDescription = rdt.getResponseDescription();
		this.service = rdt.getService();
		this.time = Timestamp.valueOf(rdt.getTime());
	}
	
	public void updateEma(Poll poll, RegisterReport rt) {
		this.requestID = rt.getRequestID();
		this.service = rt.getService();
		this.time = Timestamp.valueOf(rt.getTime());
	}
	
	public void updateEma(RegisteredReport rdt, Response rs) {
		this.requestID = rs.getRequestID();
		this.responseCode = rs.getResponseCode();
		this.responseDescription = rs.getResponseDescription();
		this.service = rs.getService();
		this.time = Timestamp.valueOf(rs.getTime());
	}
	
	public void updateEma(RequestEvent re, DistributeEvent de) {
		this.requestID = re.getRequestID();
		this.service = de.getService();
		this.responseRequired = de.getResponseRequired();
		this.time = Timestamp.valueOf(de.getTime());
	}
	
	public void updateEma(UpdateReport ur, UpdatedReport udr) {
		this.requestID = ur.getRequestID();
		this.responseCode = udr.getResponseCode();
		this.responseDescription = udr.getResponseDescription();
		this.type = udr.getType();
		this.service = udr.getService();
		this.time = Timestamp.valueOf(udr.getTime());
	}
	
	public void updateEma(CreatedEvent ce, Response rs) {
		this.requestID = rs.getRequestID();
		this.responseCode = rs.getResponseCode();
		this.responseDescription = rs.getResponseDescription();
		this.service = rs.getService();
		this.time = Timestamp.valueOf(rs.getTime());
	}
	
	public void updateEma(Response rs) {
		this.requestID = rs.getRequestID();
		this.responseCode = rs.getResponseCode();
		this.responseDescription = rs.getResponseDescription();
		this.service = rs.getService();
		this.time = Timestamp.valueOf(rs.getTime());
	}

	public void updateEma(DistributeEvent de) {
		this.service = de.getService();
		this.responseRequired = de.getResponseRequired();
		this.time = Timestamp.valueOf(de.getTime());
	}
}
