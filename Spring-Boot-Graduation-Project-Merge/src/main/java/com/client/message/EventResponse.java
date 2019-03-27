package com.client.message;

public class EventResponse {

	private String requestID, responseDescription;
	private int responseCode;
	
	@Override
	public String toString() {
		
		
		
		return "{\"responseCode" + "\":" + getResponseCode() + ", "
				+ "\"responseDescription" + "\":" + "\"" + getResponseDescription() + "\"" + ", "
				+"\"requestID" + "\":" + "\"" + getRequestID() + "\""+ "}";
		
		
		
	}
	
	
	public EventResponse() {
		super();
		// TODO Auto-generated constructor stub
	}


	public EventResponse(String requestID, String responseDescription, int responseCode) {
		this.requestID = requestID;
		this.responseDescription = responseDescription;
		this.responseCode = responseCode;
	}


	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	public String getResponseDescription() {
		return responseDescription;
	}
	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	
	
	
}
