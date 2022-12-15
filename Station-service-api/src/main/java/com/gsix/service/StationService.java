package com.gsix.service;

import java.util.Collection;

import com.gsix.entity.Station;

public interface StationService {
	Collection<Station> getAllStations();
	
	public Station searchStationById(int id);
}
