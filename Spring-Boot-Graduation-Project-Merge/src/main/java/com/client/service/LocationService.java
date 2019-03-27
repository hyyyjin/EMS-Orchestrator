package com.client.service;

import com.client.domain.LatLongDomain;

public interface LocationService {

	/**
	 * get default or randomly generated (lat, long) location.
	 * 
	 * @param srcEMA ema name
	 * @return location information
	 */
	public LatLongDomain getLocation(String srcEMA);
}
