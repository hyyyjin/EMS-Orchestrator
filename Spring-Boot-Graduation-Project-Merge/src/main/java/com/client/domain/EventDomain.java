package com.client.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity(name="Event")
public class EventDomain {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String eventID, modificationReason, marketContext, eventStatus, vtnComment,
	properties, components, specificDestEMA, duration, tolerance, notification, rampUp, recovery;
	private int modificationNumber, priority;
	private boolean testEvent;
	@OneToMany(fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name="event_id")
	private List<EventSignalsDomain> eventSignals;
	private Timestamp createdDateTime, dtStart;
	public EventDomain() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EventDomain(EventDomain event) {
		this.eventID = event.eventID;
		this.modificationReason = event.modificationReason;
		this.marketContext = event.marketContext;
		this.eventStatus = event.eventStatus;
		this.vtnComment = event.vtnComment;
		this.properties = event.properties;
		this.components = event.components;
		this.specificDestEMA = event.specificDestEMA;
		this.duration = event.duration;
		this.tolerance = event.tolerance;
		this.notification = event.notification;
		this.rampUp = event.rampUp;
		this.recovery = event.recovery;
		this.modificationNumber = event.modificationNumber;
		this.priority = event.priority;
		this.testEvent = event.testEvent;
		this.eventSignals = new ArrayList<>();
		for (EventSignalsDomain es: event.eventSignals) {
			this.eventSignals.add(new EventSignalsDomain(es));
		}
		this.createdDateTime = event.createdDateTime;
		this.dtStart = event.dtStart;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEventID() {
		return eventID;
	}
	public void setEventID(String eventID) {
		this.eventID = eventID;
	}
	public String getModificationReason() {
		return modificationReason;
	}
	public void setModificationReason(String modificationReason) {
		this.modificationReason = modificationReason;
	}
	public String getMarketContext() {
		return marketContext;
	}
	public void setMarketContext(String marketContext) {
		this.marketContext = marketContext;
	}
	public String getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}
	public String getVtnComment() {
		return vtnComment;
	}
	public void setVtnComment(String vtnComment) {
		this.vtnComment = vtnComment;
	}
	public String getProperties() {
		return properties;
	}
	public void setProperties(String properties) {
		this.properties = properties;
	}
	public String getComponents() {
		return components;
	}
	public void setComponents(String components) {
		this.components = components;
	}
	public String getSpecificDestEMA() {
		return specificDestEMA;
	}
	public void setSpecificDestEMA(String specificDestEMA) {
		this.specificDestEMA = specificDestEMA;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getTolerance() {
		return tolerance;
	}
	public void setTolerance(String tolerance) {
		this.tolerance = tolerance;
	}
	public String getNotification() {
		return notification;
	}
	public void setNotification(String notification) {
		this.notification = notification;
	}
	public String getRampUp() {
		return rampUp;
	}
	public void setRampUp(String rampUp) {
		this.rampUp = rampUp;
	}
	public String getRecovery() {
		return recovery;
	}
	public void setRecovery(String recovery) {
		this.recovery = recovery;
	}
	public int getModificationNumber() {
		return modificationNumber;
	}
	public void setModificationNumber(int modificationNumber) {
		this.modificationNumber = modificationNumber;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public boolean isTestEvent() {
		return testEvent;
	}
	public void setTestEvent(boolean testEvent) {
		this.testEvent = testEvent;
	}
	public List<EventSignalsDomain> getEventSignals() {
		return eventSignals;
	}
	public void setEventSignals(List<EventSignalsDomain> eventSignals) {
		this.eventSignals = eventSignals;
	}
	public Timestamp getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(Timestamp createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	public Timestamp getDtStart() {
		return dtStart;
	}
	public void setDtStart(Timestamp dtStart) {
		this.dtStart = dtStart;
	}
	@Override
	public String toString() {
		return "{\"eventSignals" + "\":" +  getEventSignals() + ", "
				+ "\"eventID" + "\":" + "\"" + getEventID() + "\"" + ", "
				+"\"modificationNumber" + "\":" + "\"" + getModificationNumber() + "\"" + ", "
				+"\"modificationReason" + "\":" + "\"" + getModificationReason() + "\"" + ", "
				+"\"priority" + "\":" + "\"" + getPriority() + "\"" + ", "
				+"\"marketContext" + "\":" + "\"" + getMarketContext() + "\"" + ", "
				+"\"createdDateTime" + "\":" + "\"" + getCreatedDateTime() + "\"" + ", "
				+"\"eventStatus" + "\":" + "\"" + getEventStatus() + "\"" + ", "
				+"\"testEvent" + "\":" + "\"" + isTestEvent() + "\"" + ", "
				+"\"vtnComment" + "\":" + "\"" + getVtnComment() + "\"" + ", "
				+"\"properties" + "\":" + "\"" + getProperties() + "\"" + ", "
				+"\"components" + "\":" + "\"" + getComponents() + "\"" + ", "
				+"\"specificDestEMA" + "\":" + "\"" + getSpecificDestEMA() + "\"" + ", "
				+"\"dtStart" + "\":" + "\"" + getDtStart() + "\"" + ", "
				+"\"duration" + "\":" + "\"" + getDuration() + "\"" + ", "
				+"\"tolerance" + "\":" + "\"" + getTolerance() + "\"" + ", "
				+"\"notification" + "\":" + "\"" + getNotification() + "\"" + ", "
				+"\"rampUp" + "\":" + "\"" + getRampUp() + "\"" + ", "
				+"\"recovery" + "\":" + "\"" + getRecovery() + "\"" + "}";
	}
}
