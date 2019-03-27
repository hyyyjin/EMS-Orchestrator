package com.client.service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.client.domain.LatLongDomain;

@Service
public class LocationServiceImpl implements LocationService {

	// latitude and longitude boundary (hanyang univ.)
	private static final double minLat = 37.554094, maxLat = 37.559180;
	private static final double minLng = 127.043795, maxLng = 127.050265;
	private static final int FIELD_LAT = 0, FIELD_LNG = 1;
	private Map<String, LatLongDomain> locations;
	
	public LocationServiceImpl() {
		// (latitude, longitude) array
		double[][] loc = {{37.555454, 127.049212}, {37.556481, 127.049971}, {37.554629, 127.046633}, {37.556648, 127.044498}
		,{37.554807, 127.046093}, {37.556365, 127.048407}, {37.558150, 127.048178}, {37.557172, 127.047631}
		,{37.558014, 127.046773}, {37.556684, 127.046758}, {37.555603, 127.046250}, {37.556556, 127.045778}
		,{37.555995, 127.044748}, {37.556582, 127.043915}, {37.555023, 127.044219}, {37.558170, 127.045056}
		,{37.558799, 127.044326}, {37.558331, 127.043511}, {37.556251, 127.047053}, {37.558377, 127.049059}};
		locations = new ConcurrentHashMap<>();
		// initialize hashmap with default locations
		for (int i=0; i<loc.length; i++) {
			String srcEMA = String.format("CLIENT_EMA%02d", i+1);
			locations.put(srcEMA, new LatLongDomain(loc[i][FIELD_LAT], loc[i][FIELD_LNG], srcEMA, "Demo EMA"));
		}
	}
	
	@Override
	public LatLongDomain getLocation(String srcEMA) {
		// TODO Auto-generated method stub
		if (locations.containsKey(srcEMA)) {	// srcEMA is in the map
			return locations.get(srcEMA);
		}
		else {	// not in the map
			// make new point
			Random rand = new Random();
			LatLongDomain latLong = new LatLongDomain();
			latLong.setSrcEMA(srcEMA);
			// select random location
			// linear interpolation (1-t)A + tB
			latLong.setLat(rand.nextDouble()*(maxLat - minLat) + minLat);
			latLong.setLng(rand.nextDouble()*(maxLng - minLng) + minLng);
			latLong.setDescription("Extra EMA");
			locations.put(srcEMA, latLong);
			return latLong;
		}
	}

}
