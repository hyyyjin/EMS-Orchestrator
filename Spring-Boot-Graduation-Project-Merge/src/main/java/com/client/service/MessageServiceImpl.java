package com.client.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.client.domain.EmaDomain;
import com.client.domain.EventDomain;
import com.client.domain.EventSignalsDomain;
import com.client.domain.IntervalsDomain;
import com.client.domain.PowerAttributesDomain;
import com.client.domain.ProfileDomain;
import com.client.domain.ReportDescriptionDomain;
import com.client.domain.ReportDomain;
import com.client.domain.TransportDomain;
import com.client.message.*;
import com.client.mqtt.publisher.MQTTPublisherBase;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/*
 * implementation class of MessageService interface
 */
@Service
public class MessageServiceImpl implements MessageService {
	
	/**
	 * (topic, message) tuple inner class.
	 */
	public class Reply {
		public String topic;
		public String message;
		
		public Reply() { }
		
		public Reply(String topic, String message) {
			this.topic = topic;
			this.message = message;
		}
	}
	
	@Autowired
	MQTTPublisherBase publisher;
	@Autowired
	RepositoryService repoService;
	@Autowired
	LocationService locService;
	
	private static final String protocol = "EMAP";
	private static final String serverName = "SERVER_EMS1";
	private static final String version = "1.0b";
	private static final String SessionSetup = "SessionSetup";
	private static final String Report = "Report";
	private static final String Event = "Event";
	
	// flag for emap pull model
	private static Map<String, Reply> flags = new ConcurrentHashMap<>();

	@Async("threadPoolTaskExecutor")
	@Override
	public void distributeEvent(String destEMA, String time, double threshold) {
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		EmaDomain ema = repoService.findEma(destEMA);
		
		// set message
		List<IntervalsDomain> iList = new ArrayList<>(); 
		iList.add(new IntervalsDomain("duration", "uid", 100.1));
							
		List<EventSignalsDomain> esList = new ArrayList<>();
		esList.add(new EventSignalsDomain("signalName", "Price Event", "signalID", "KW/WON",
				400.1, threshold, 500.2, 10000, iList));

		List<EventDomain> evList = new ArrayList<>();
		EventDomain ev = new EventDomain();
		ev.setEventID("eventID");
		ev.setEventSignals(esList);
		ev.setModificationNumber(1);
		ev.setModificationReason("modificationReason");
		ev.setPriority(2);
		ev.setMarketContext("https://hourlypricing.comed.com/live-prices/?date=20180826");
		ev.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));
		ev.setEventStatus("eventStatus");
		ev.setTestEvent(false);
		ev.setVtnComment("Event");
		ev.setProperties("properties");
		ev.setComponents("components");
		ev.setSpecificDestEMA(destEMA);
		ev.setDtStart(Timestamp.valueOf(time));
		ev.setDuration("PT1H");
		ev.setTolerance("tolerance");
		ev.setNotification("notification");
		ev.setRampUp("rampUp");
		ev.setRecovery("recovery");
		evList.add(ev);
		
		List<EventResponse> erList = new ArrayList<>(); 
		erList.add(new EventResponse(ema.getRequestID(), "OK", 200));
		
		DistributeEvent de = new DistributeEvent();
		de.setSrcEMA(serverName);
		de.setDestEMA(destEMA);
		de.setRequestID(ema.getRequestID());
		de.setService("DistributeEvent");
		de.setResponse(erList.toString());
		de.setEvent(evList.toString());
		de.setResponseRequired("responseRequired");
		de.setTime(sdf.format(System.currentTimeMillis()));
		
		ema.updateEma(de);	// update ema info
		repoService.saveEma(ema);	// save ema
		repoService.saveEvent(evList);	// save dr event
		
		// pull: wait until next poll message
		// push: just publish
		if (ema.isHttpPullModel())
			flags.put(destEMA, new Reply("/"+protocol+"/"+de.getDestEMA()+"/"+version+"/"+Event, de.toString()));
		else
			publisher.publishMessage("/"+protocol+"/"+de.getDestEMA()+"/"+version+"/"+Event, de.toString());	
	}
		
	@Async("threadPoolTaskExecutor")
	@Override
	public void handleMessage(String topic, String message) {
		String type = parseTopic(topic);
		if (type == null)
			return;
		Reply reply = parseMessage(type, message);
		if (reply == null)
			return;
		publisher.publishMessage(reply.topic, reply.message);
	}
	
	/**
	 * tokenize topic.
	 * @param topic a topic to be parsed.
	 * @return the last token of the topic; <code>null</code> if version doesn't match or
	 * 		   there is no more token.
	 */
	private String parseTopic(String topic) {
		try {
			StringTokenizer token = new StringTokenizer(topic, "/");
			// topic prefix is /EMAP/SERVER_EMS1
			token.nextToken(); /* == EMAP */
			token.nextToken(); /* == SERVER_EMS1 */
			if (!token.hasMoreTokens() || !version.equals(token.nextToken()))	/* == 1.0b */
				return null;
			return token.hasMoreTokens()? token.nextToken(): null; /* Session, Report, Poll, Event */
		} catch (NoSuchElementException e) {
			System.err.println("no more token, topic: "+topic);
			return null;
		}
	}
	
	
	/**
	 * deserialize message with {@link Gson}, save the information
	 * and publish reply to source EMA.
	 * 
	 * @param type a type of topic
	 * @param message received JSON message
	 * @return {@link Reply} to be published
	 */
	private Reply parseMessage(String type, String message) {
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		Reply reply = null; 
		EmaDomain ema;
		try {
			// parse original message to json
			JsonElement json = parser.parse(message);
			switch(type) {
			/* session setup */
			case "SessionSetup":
				switch(json.getAsJsonObject().get("service").getAsString()) {
				case "ConnectRegistration":
					ConnectRegistration conn = gson.fromJson(json, ConnectRegistration.class);
					// json libraries with default deserializer read key as CamelCase (SrcEMA => srcEMA). 
					// so directly get SrcEMA from original message
					conn.setSrcEMA(json.getAsJsonObject().get("SrcEMA").getAsString());
					ema = new EmaDomain();

					// if profile doesn't exist in DB, create new profile
					List<ProfileDomain> pList = repoService.findProfile(conn.getSrcEMA());
					if (pList == null) {
						List<TransportDomain> trList = new ArrayList<>();
						trList.add(new TransportDomain("MQTT"));
						trList.add(new TransportDomain("HTTP"));

						pList = new ArrayList<>();
						pList.add(new ProfileDomain(conn.getSrcEMA(), "EMAP2.0B", trList));
						pList.add(new ProfileDomain(conn.getSrcEMA(), "OpenADR2.0b", trList));
						
						repoService.saveProfile(pList);	// save profile
					}
					
					// set message
					ConnectedPartyRegistration cr = new ConnectedPartyRegistration();
					cr.setDestEMA(conn.getSrcEMA());
					cr.setDuration("PT2S");
					cr.setProfile(pList.toString());
					cr.setRequestID(conn.getRequestID());
					cr.setResponseCode(200);
					cr.setResponseDescription("OK");
					cr.setService("ConnectedRegistration");
					cr.setSrcEMA(serverName);
					cr.setTime(sdf.format(System.currentTimeMillis()));
					cr.setVersion(conn.getVersion());

					ema.updateEma(conn, cr);	// update EMA info
					repoService.saveEma(ema);	// save EMA
					
					// if EMA doesn't have latlong, save new location. 
					if (!repoService.hasLocation(conn.getSrcEMA())) {
						repoService.saveLatLong(locService.getLocation(conn.getSrcEMA()));
					}
					reply = new Reply("/"+protocol+"/"+cr.getDestEMA()+"/"+version+"/"+SessionSetup, cr.toString());
					break;
				case "CreatePartyRegistration":
					// activate those if-else when receive boolean field as integer
					if(json.getAsJsonObject().get("reportOnly").getAsInt() == 0)
						json.getAsJsonObject().addProperty("reportOnly", false);
					else
						json.getAsJsonObject().addProperty("reportOnly", true);
					if(json.getAsJsonObject().get("xmlSignature").getAsInt() == 0)
						json.getAsJsonObject().addProperty("xmlSignature", false);
					else
						json.getAsJsonObject().addProperty("xmlSignature", true);
					
					// set message
					CreatePartyRegistration cp = gson.fromJson(json, CreatePartyRegistration.class);
					cp.setSrcEMA(json.getAsJsonObject().get("SrcEMA").getAsString());
					
					ema = repoService.findEma(cp.getSrcEMA());
					List<ProfileDomain> pList2 = repoService.findProfile(cp.getSrcEMA());
					
					CreatedPartyRegistration cdp = new CreatedPartyRegistration();
					cdp.setSrcEMA(serverName);
					cdp.setDestEMA(cp.getSrcEMA());
					cdp.setDuration(ema.getDuration());
					cdp.setRequestID(cp.getRequestID());
					cdp.setResponseCode(200);
					cdp.setResponseDescription("OK");
					cdp.setProfile(pList2.toString());
					cdp.setDuration(ema.getDuration());
					cdp.setRegistrationID("RegistrationID");
					cdp.setService("CreatedPartyRegistration");
					cdp.setVersion(ema.getVersion());
					cdp.setTime(sdf.format(System.currentTimeMillis()));

					ema.updateEma(cp, cdp);
					repoService.saveEma(ema);
					
					reply = new Reply("/"+protocol+"/"+cdp.getDestEMA()+"/"+version+"/"+SessionSetup, cdp.toString());
					break;
				case "RegisterReport":
					// deserialize original message and report separately
					JsonArray rArr = json.getAsJsonObject().get("report").getAsJsonArray();
					// remove report key. gson cannot deserialize into nested class
					json.getAsJsonObject().remove("report");
					RegisterReport rt = gson.fromJson(json, RegisterReport.class);	// json -> RegisterReport
					rt.setSrcEMA(json.getAsJsonObject().get("SrcEMA").getAsString());
					List<ReportDomain> rList = parseReport(rArr, gson);	// rArr -> ReportDomain list
					for (ReportDomain r: rList) {
						r.setSrcEMA(rt.getSrcEMA());
					}
					
					ema = repoService.findEma(rt.getSrcEMA());
					
					// set message
					RegisteredReport rtd = new RegisteredReport();
					rtd.setSrcEMA(serverName);
					rtd.setDestEMA(rt.getSrcEMA());
					rtd.setRequestID(rt.getRequestID());
					rtd.setResponseCode(200);
					rtd.setResponseDescription("OK");
					rtd.setService("RegisteredReport");
					rtd.setTime(sdf.format(System.currentTimeMillis()));
					
					ema.updateEma(rt, rtd);
					repoService.saveEma(ema);
					repoService.saveReport(rList);
					
					reply = new Reply("/"+protocol+"/"+rtd.getDestEMA()+"/"+version+"/"+SessionSetup, rtd.toString());
					break;
				case "Poll":
					Poll p = gson.fromJson(json, Poll.class);
					p.setSrcEMA(json.getAsJsonObject().get("SrcEMA").getAsString());
					
					ema = repoService.findEma(p.getSrcEMA());
					
					// set message
					RegisterReport rt2 = new RegisterReport();
					rt2.setSrcEMA(serverName);
					rt2.setDestEMA(p.getSrcEMA());
					rt2.setRequestID(ema.getRequestID());
					rt2.setService("RegisterReport");
					rt2.setReport("");
					rt2.setType("");
					rt2.setTime(sdf.format(System.currentTimeMillis()));
					
					ema.updateEma(p, rt2);
					repoService.saveEma(ema);
					
					reply = new Reply("/"+protocol+"/"+rt2.getDestEMA()+"/"+version+"/"+SessionSetup, rt2.toString());
					break;
				case "RegisteredReport":
					RegisteredReport rtd2 = gson.fromJson(json, RegisteredReport.class);
					rtd2.setSrcEMA(json.getAsJsonObject().get("SrcEMA").getAsString());
					
					ema = repoService.findEma(rtd2.getSrcEMA());
					
					// set message
					Response rs = new Response();
					rs.setSrcEMA(serverName);
					rs.setDestEMA(rtd2.getSrcEMA());
					rs.setRequestID(ema.getRequestID());
					rs.setResponseCode(200);
					rs.setResponseDescription("OK");
					rs.setService("Response");
					rs.setTime(sdf.format(System.currentTimeMillis()));
					
					ema.updateEma(rtd2, rs);
					repoService.saveEma(ema);
					
					reply = new Reply("/"+protocol+"/"+rs.getDestEMA()+"/"+version+"/"+SessionSetup, rs.toString());
					break;
				case "RequestEvent":
					RequestEvent re = gson.fromJson(json, RequestEvent.class);
					re.setSrcEMA(json.getAsJsonObject().get("SrcEMA").getAsString());
					
					ema = repoService.findEma(re.getSrcEMA());
					
					// set event
					List<IntervalsDomain> iList = new ArrayList<>(); 
					iList.add(new IntervalsDomain("duration", "uid", 100.1));
										
					List<EventSignalsDomain> esList = new ArrayList<>();
					esList.add(new EventSignalsDomain("signalName", "Price Event", "signalID", "KW/WON",
							400.1, 400.1, 500.2, 10000, iList));
					
					List<EventDomain> evList = new ArrayList<>();
					EventDomain ev = new EventDomain();
					ev.setEventID("eventID");
					ev.setEventSignals(esList);
					ev.setModificationNumber(1);
					ev.setModificationReason("modificationReason");
					ev.setPriority(2);
					ev.setMarketContext("https://hourlypricing.comed.com/live-prices/?date=20180826");
					ev.setCreatedDateTime(Timestamp.valueOf(sdf.format(System.currentTimeMillis())));
					ev.setEventStatus("eventStatus");
					ev.setTestEvent(false);
					ev.setVtnComment("SessionSetup");
					ev.setProperties("properties");
					ev.setComponents("components");
					ev.setSpecificDestEMA(re.getSrcEMA());
					ev.setDtStart(ev.getCreatedDateTime());
					ev.setDuration("PT1H");
					ev.setTolerance("tolerance");
					ev.setNotification("notification");
					ev.setRampUp("rampUp");
					ev.setRecovery("recovery");
					evList.add(ev);
					
					List<EventResponse> erList = new ArrayList<>(); 
					erList.add(new EventResponse(re.getRequestID(), "OK", 200));
					
					// set message
					DistributeEvent de = new DistributeEvent();
					de.setSrcEMA(serverName);
					de.setDestEMA(re.getSrcEMA());
					de.setRequestID(re.getRequestID());
					de.setService("DistributeEvent");
					de.setResponse(erList.toString());
					de.setEvent(evList.toString());
					de.setResponseRequired("responseRequired");
					de.setTime(sdf.format(System.currentTimeMillis()));
					
					ema.updateEma(re, de);
					repoService.saveEma(ema);
					repoService.saveEvent(evList);
					
					reply = new Reply("/"+protocol+"/"+re.getSrcEMA()+"/"+version+"/"+SessionSetup, de.toString());
					break;
				default:
					// nothing
					break;
				}
				/* end of SesstionSetup */
				break;
				
			/* report */
			case "Report":
				String srcEMA = json.getAsJsonObject().get("SrcEMA").getAsString();
				ema = repoService.findEma(srcEMA);
				
				// type == Explicit: parse report field
				if (json.getAsJsonObject().get("type").getAsString().equals("Explicit")) {
					JsonArray rArr = json.getAsJsonObject().get("report").getAsJsonArray();
					json.getAsJsonObject().remove("report");
					List<ReportDomain> rList = parseReport(rArr, gson);
					for (ReportDomain r: rList) {
						r.setSrcEMA(srcEMA);
					}
					repoService.saveReport(rList);
				}
				
				// set message
				UpdateReport ur = gson.fromJson(json, UpdateReport.class);
				ur.setSrcEMA(srcEMA);
								
				UpdatedReport udr = new UpdatedReport();
				udr.setSrcEMA(serverName);
				udr.setDestEMA(ur.getSrcEMA());
				udr.setRequestID(ur.getRequestID());
				udr.setResponseCode(200);
				udr.setResponseDescription("OK");
				udr.setService("UpdatedReport");
				udr.setType(ur.getType());
				udr.setTime(sdf.format(System.currentTimeMillis()));
				
				ema.updateEma(ur, udr);
				repoService.saveEma(ema);
				
				reply = new Reply("/"+protocol+"/"+ur.getSrcEMA()+"/"+version+"/"+Report, udr.toString());
				/* end of Report */
				break;
				
			/* poll */
			case "Poll":
				// receive poll message only if pull model 
				Poll p = gson.fromJson(json, Poll.class);
				p.setSrcEMA(json.getAsJsonObject().get("SrcEMA").getAsString());
				
				ema = repoService.findEma(p.getSrcEMA());
				
				// true: DistributeEvent (have any message to be sent)
				// false: Response
				if (flags.containsKey(p.getSrcEMA())) {
					reply = flags.get(p.getSrcEMA());
					flags.remove(p.getSrcEMA());	// remove value for next time
				}
				else {
					Response rs = new Response();
					rs.setSrcEMA(serverName);
					rs.setDestEMA(p.getSrcEMA());
					rs.setRequestID(ema.getRequestID());
					rs.setResponseCode(200);
					rs.setResponseDescription("OK");
					rs.setService("Response");
					rs.setTime(sdf.format(System.currentTimeMillis()));

					ema.updateEma(rs);
					reply = new Reply("/"+protocol+"/"+rs.getDestEMA()+"/"+version+"/"+Event, rs.toString());
				}
				/* end of Poll */
				break;
				
			case "Event":
				switch(json.getAsJsonObject().get("service").getAsString()) {
				case "CreatedEvent":
					CreatedEvent ce = gson.fromJson(json, CreatedEvent.class);
					ce.setSrcEMA(json.getAsJsonObject().get("SrcEMA").getAsString());
					
					ema = repoService.findEma(ce.getSrcEMA());
					
					Response rs2 = new Response();
					rs2.setSrcEMA(serverName);
					rs2.setDestEMA(ce.getSrcEMA());
					rs2.setRequestID(ce.getRequestID());
					rs2.setResponseCode(200);
					rs2.setResponseDescription(ce.getResponseDescription());
					rs2.setService("Response");
					rs2.setTime(sdf.format(System.currentTimeMillis()));
					
					ema.updateEma(ce, rs2);
					repoService.saveEma(ema);
					reply = new Reply("/"+protocol+"/"+rs2.getDestEMA()+"/"+version+"/"+Event, rs2.toString());
					break;
				default:
					// ignore Response from EMA in this time
					break;
				}
				/* end of Event */
				break;
			default:
				/* end of outer switch */
				break;
			}
		} catch (JsonSyntaxException e) {
			System.err.println("fail to deserialize into specific class");
			System.err.println(e.toString());
			System.err.println(message);
			e.printStackTrace();
			return null;
		} catch (JsonParseException e) {
			System.err.println("fail to parse message");
			System.err.println(e.toString());
			System.err.println(message);
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return reply;
	}
	
	/**
	 * parse nested report json array. make sure that time fields have
	 * yyyy-MM-dd HH:mm:ss date format (didn't use try-catch for exceptions).
	 * @param jArr report json array
	 * @param gson Gson object (reuse)
	 * @return deserialized report array
	 */
	private List<ReportDomain> parseReport(JsonArray jArr, Gson gson) {
		List<ReportDomain> rList = new ArrayList<>();
		for (JsonElement r : jArr) {
			JsonArray rdArr = r.getAsJsonObject().get("reportDescription").getAsJsonArray();
			r.getAsJsonObject().remove("reportDescription");
			// to convert String to Timestamp
			String cdt = r.getAsJsonObject().get("createdDateTime").getAsString();
			r.getAsJsonObject().remove("createdDateTime");
			ReportDomain report = gson.fromJson(r, ReportDomain.class);
			report.setCreatedDateTime(Timestamp.valueOf(cdt));
			List<ReportDescriptionDomain> rdList = new ArrayList<>();
			for (JsonElement rd : rdArr) {
				JsonArray powArr = rd.getAsJsonObject().get("powerAttributes").getAsJsonArray();
				rd.getAsJsonObject().remove("powerAttributes");
				// to convert String to Timestamp
				String maxTime = rd.getAsJsonObject().get("maxTime").getAsString();
				rd.getAsJsonObject().remove("maxTime");
				String minTime = rd.getAsJsonObject().get("minTime").getAsString();
				rd.getAsJsonObject().remove("minTime");
				ReportDescriptionDomain reportDesc = gson.fromJson(rd, ReportDescriptionDomain.class);
				// can remove if statement when maxTime and minTime have value
				if (!"".equals(maxTime))
					reportDesc.setMaxTime(Timestamp.valueOf(maxTime));
				if (!"".equals(minTime))
					reportDesc.setMinTime(Timestamp.valueOf(minTime));
				List<PowerAttributesDomain> powList = new ArrayList<>();
				for (JsonElement pow : powArr) {
					PowerAttributesDomain powerAttr = gson.fromJson(pow, PowerAttributesDomain.class);
					powList.add(powerAttr);
				}
				reportDesc.setPowerAttributes(powList);
				rdList.add(reportDesc);
			}
			report.setReportDescription(rdList);
			rList.add(report);
		}
		
		return rList;
	}
}
