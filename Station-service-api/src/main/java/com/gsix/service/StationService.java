package com.gsix.service;

import java.util.Collection;

import com.gsix.entity.Station;
import com.gsix.entity.StationList;

public interface StationService {
	
	StationList getAllStations();
	
	Station getStationByStationName(String stationName);
	
	double checkRouteCost(String sourceStation, String destStation);
}
