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

import org.json.JSONException;
import org.json.JSONObject;

@Entity(name="Report")
public class ReportDomain implements Comparable<ReportDomain> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String duration, reportRequestID, reportSpecifierID, reportName, srcEMA;
	private Timestamp createdDateTime;
	@OneToMany(fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name="report_id")
	private List<ReportDescriptionDomain> reportDescription;
	
	public ReportDomain() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ReportDomain(ReportDomain report) {
		this.duration = report.duration;
		this.reportRequestID = report.reportRequestID;
		this.reportSpecifierID = report.reportSpecifierID;
		this.reportName = report.reportName;
		this.srcEMA = report.srcEMA;
		this.createdDateTime = report.createdDateTime;
		this.reportDescription = new ArrayList<>();
		for (ReportDescriptionDomain rd: report.reportDescription) {
			this.reportDescription.add(new ReportDescriptionDomain(rd));
		}
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getReportRequestID() {
		return reportRequestID;
	}
	public void setReportRequestID(String reportRequestID) {
		this.reportRequestID = reportRequestID;
	}
	public String getReportSpecifierID() {
		return reportSpecifierID;
	}
	public void setReportSpecifierID(String reportSpecifierID) {
		this.reportSpecifierID = reportSpecifierID;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public Timestamp getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(Timestamp createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	public List<ReportDescriptionDomain> getReportDescription() {
		return reportDescription;
	}
	public void setReportDescription(List<ReportDescriptionDomain> reportDescription) {
		this.reportDescription = reportDescription;
	}
	
	public String getSrcEMA() {
		return srcEMA;
	}

	public void setSrcEMA(String srcEMA) {
		this.srcEMA = srcEMA;
	}

	@Override
	public String toString() {

		JSONObject json = new JSONObject();

		try {

			json.put("duration", getDuration());
			json.put("reportDescription", getReportDescription());
			json.put("reportRequestID", getReportRequestID());
			json.put("reportSpecifierID", getReportSpecifierID());
			json.put("reportName", getReportName());
			json.put("createdDateTime", getCreatedDateTime());
			
			return json.toString();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return "wrong";
		}

	}

	@Override
	public int compareTo(ReportDomain o) {
		// TODO Auto-generated method stub
		return Long.compare(this.id, o.id);
	}
}
