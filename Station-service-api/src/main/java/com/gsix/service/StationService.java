package com.gsix.service;

import java.util.Collection;

import com.gsix.entity.Station;

public interface StationService {
	
	Collection<Station> getAllStations();
	
	double checkRouteCost(String sourceStation, String destStation);
}
