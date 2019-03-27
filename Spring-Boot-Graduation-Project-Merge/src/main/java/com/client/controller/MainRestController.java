package com.client.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.client.domain.DataPointDomain;
import com.client.domain.LatLongDomain;
import com.client.domain.ReportDescriptionDomain;
import com.client.domain.ReportDomain;
import com.client.service.MessageService;
import com.client.service.RepositoryService;
import com.client.structure.EventMessage;

/**
 * api collection for graph.
 * we assume that those are only called from jsp 
 * and all parameters is correct, so skip validation check
 */
@RestController
public class MainRestController {
	@Autowired
	RepositoryService repoService;
	@Autowired
	MessageService msgService;

	/**
	 * get input from ema_template.jsp modal. distribute event
	 * and return <code>true</code>.
	 * 
	 * @param e [ema, time, threshold] model attribute 
	 * @return <code>true</code> (no meaning)
	 */
	@RequestMapping(value="/event", method=RequestMethod.POST)
	public Boolean createEvent(@ModelAttribute EventMessage e) {
		StringBuilder sb = new StringBuilder();
		sb.append(e.getStartYMD()).append(" ").append(e.getStarttime());
		msgService.distributeEvent(e.getDestEMA(), sb.toString(), e.getThreshold());
		
		return true;
	}
	
	/**
	 * find (time, power) pair data points of ema.
	 * called by ema_graph.jsp.
	 * 
	 * @param eid target ema name
	 * @param length number of data points
	 * @param millis <code>long</code> type milliseconds
	 * @return JSON list; <code>NULL</code> if there is no such data.
	 */
	@RequestMapping(value="/emapoints", method=RequestMethod.GET)
	public List<Map<String, Object>> emaDataPoints(@RequestParam("eid")String eid, 
			@RequestParam("length")int length, @RequestParam("time")long millis) {
		// if millis is 0, search records within last 1 minute.
		// if not, search record after that time.
		long time = 0;
		if (millis != 0)
			time = millis;
		else {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, -1);
			time = cal.getTimeInMillis();
			// if gateway or client timezone dose not match with server,
			// set time to 0.
//			time = 0;
		}
		// read N data whose createdDateTime is larger than time
		List<ReportDomain> list = repoService.findReportsLimitN(eid, length, time);
		if (list == null)
			return null;
		
		List<Map<String,Object>> points = new ArrayList<>(length);
		Map<String, Object> point = null;
		for (ReportDomain r: list) {
			for (ReportDescriptionDomain rd: r.getReportDescription()) {
				if (rd.getrID().equals(eid)) {
					point = new HashMap<>();
					// x: time, y: power
					point.put("x", r.getCreatedDateTime().getTime());
					point.put("y", rd.getPower());
					points.add(point);
					break;
				}
			}
		}
		
		return points;
	}
	
	/**
	 * similar to {@link #emaDataPoints}.
	 * called by sub_graph.jsp.
	 * 
	 * @param eid ema name which is parent of subnode
	 * @param deviceId target subnode name
	 * @param length number of data points
	 * @param millis <code>long</code> type milliseconds
	 * @return JSON list; <code>NULL</code> if there is no such data.
	 */
	@RequestMapping(value="/subpoints", method=RequestMethod.GET)
	public List<Map<String, Object>> subDataPoints(@RequestParam("eid")String eid, 
			@RequestParam("deviceId")String deviceId, @RequestParam("length")int length,
			@RequestParam("time")Long millis) {
		// if millis is 0, search records within last 1 minute.
		// if not, search record after that time.
		long time = 0;
		if (millis != 0)
			time = millis;
		else {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, -1);
			time = cal.getTimeInMillis();
			// if gateway or client timezone dose not match with server,
			// set time to 0.
//			time = 0;
		}
		
		List<ReportDomain> list = repoService.findReportsLimitN(eid, length, time);
		if (list == null)
			return null;
		List<Map<String,Object>> points = new ArrayList<>(length);
		Map<String, Object> point = null;
		for (ReportDomain r: list) {
			for (ReportDescriptionDomain rd: r.getReportDescription()) {
				if (rd.getrID().equals(deviceId)) {
					point = new HashMap<>();
					point.put("x", r.getCreatedDateTime().getTime());
					point.put("y", rd.getPower());
					points.add(point);
					break;
				}
			}
		}
		
		return points;
	}
	
	/**
	 * find the newest information about report in database.
	 * called by report_graph.jsp.
	 * @param eid target ema name
	 * @return JSON object
	 */
	@RequestMapping("/reportpoints")
	public Map<String,Object> reportDataPoints(@RequestParam("eid")String eid) {
		ReportDomain report = repoService.findReport(eid);

		Map<String,Object> dataSet = new HashMap<>();
		List<Map<String,Object>> dataPoints = new ArrayList<>();
		Map<String,Object> dataPoint = null;
		for (ReportDescriptionDomain rd: report.getReportDescription()) {
			if (rd.getDeviceType().equals("EMA")) {
				dataSet.put("power", rd.getPower());
				dataSet.put("avg", rd.getAvgValue());
				dataSet.put("max", rd.getMaxValue());
				dataSet.put("min", rd.getMinValue());
			}
			else { /* subnodes */
				dataPoint = new HashMap<>();
				dataPoint.put("label", rd.getDeviceType()+" "+rd.getrID());
				dataPoint.put("legendText", rd.getrID());
				dataPoint.put("y", rd.getPower());
				dataPoints.add(dataPoint);
			}
		}
		dataSet.put("dataPoints", dataPoints);
		dataSet.put("count", dataPoints.size());
		
		return dataSet;
	}
	
	/**
	 * find all (lat, long) records.
	 * called by initMap function in googleMap.js
	 * @return location JSON list
	 */
	@RequestMapping("/mapdata")
	public List<Map<String, Object>> map() {
		List<LatLongDomain> list = repoService.findAllLocation();
		
		List<Map<String,Object>> latLong = new ArrayList<>();
		Map<String,Object> point = null;
		for (LatLongDomain l: list) {
			point = new HashMap<>();
			point.put("name", l.getSrcEMA());
			point.put("lat", Double.parseDouble(String.format("%.6f", l.getLat())));
			point.put("lng", Double.parseDouble(String.format("%.6f", l.getLng())));
			point.put("desc", l.getDescription());
			latLong.add(point);
		}

		return latLong;
	}
	
	/**
	 * find (time, power) pair data points of ema.
	 * called by ema_graph.jsp.
	 * 
	 * @param eid target ema name
	 * @param length number of data points
	 * @param millis <code>long</code> type milliseconds
	 * @return JSON list; <code>NULL</code> if there is no such data.
	 */
	@RequestMapping(value="/drpoints", method=RequestMethod.GET)
	public List<Map<String, Object>> drDataPoints(@RequestParam("type")int type, 
			@RequestParam("length")int length, @RequestParam("time")long millis) {
		// if millis is 0, search records within 1 minute.
		// if not, search record after that time.
		long time = 0;
		if (millis != 0)
			time = millis;
		else {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, -1);
			time = cal.getTimeInMillis();
			// if gateway or client timezone dose not match with server,
			// set time to 0.
//			time = 0;
		}
		List<DataPointDomain> list = repoService.findDataPoints(type, length, time);
		if (list == null)
			return null;
		
		List<Map<String,Object>> points = new ArrayList<>(length);
		Map<String, Object> point = null;
		for (DataPointDomain d: list) {
			point = new HashMap<>();
			point.put("x", d.getTime().getTime());
			point.put("c", d.getCapacity());
			point.put("p", d.getPower());
			points.add(point);
		}
		
		return points;
	}
	
}
