package com.client.service;

import java.util.List;

import com.client.domain.DataPointDomain;
import com.client.domain.EmaDomain;
import com.client.domain.EventDomain;
import com.client.domain.LatLongDomain;
import com.client.domain.ProfileDomain;
import com.client.domain.ReportDomain;
import com.client.structure.EmaTriple;

/**
 * database service interface 
 */
public interface RepositoryService {
	public void saveEma(EmaDomain ema);
	public void saveProfile(ProfileDomain profile);
	public void saveProfile(List<ProfileDomain> profiles);
	public void saveReport(List<ReportDomain> reports);
	public void saveEvent(List<EventDomain> events);
	public void saveLatLong(LatLongDomain latLong);
	public void saveDataPoints(List<DataPointDomain> dataPoints);
	
	public EmaDomain findEma(String srcEMA);
	public List<ProfileDomain> findProfile(String srcEMA);
	public List<String> findEmaList();
	public ReportDomain findReport(String srcEMA); 
	public EventDomain findEvent(String specificDestEMA);
	public List<ReportDomain> findReportsLimitN(String srcEMA, int n, long millis);
	public List<LatLongDomain> findAllLocation();
	
	public boolean hasLocation(String srcEMA);
	
	List<EmaTriple> findDefaultSet();
	List<EmaTriple> findGreedySet();
	List<EmaTriple> findKnapsackSet();
	List<DataPointDomain> findDataPoints(int type, int n, long millis);
}
