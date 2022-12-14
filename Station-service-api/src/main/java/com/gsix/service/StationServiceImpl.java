package com.gsix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsix.entity.Station;
import com.gsix.entity.StationList;
import com.gsix.persistence.StationDao;

@Service
public class StationServiceImpl implements StationService {

	@Autowired
	private StationDao stationDao;

	@Override
	public StationList getAllStations() {
		
		StationList stationList = new StationList();
		
		stationList.setStations(stationDao.findAll());
		
		return stationList;
	}

	// setting a constant value for the price of travel between two stations
	static final double ADJACENT_STATIONS = 5.00;
	
	// checking the cost of the route based on how many stations are travelled
	// will cost 0 if the swipe in/swipe out station is the same
	// allows for travel both ways
	@Override
	public double checkRouteCost(String sourceStation, String destStation) {
		Station source = stationDao.searchStationByStationName(sourceStation);
		Station des = stationDao.searchStationByStationName(destStation);
		int sourceId = source.getStationId();
		int desId = des.getStationId();
		
		int distance;
		
		if (sourceId == desId) {
			return 0;
		} else if (sourceId > desId) {
			distance = sourceId - desId;
		} else {
			distance = desId - sourceId;
		}
		
		double price = ADJACENT_STATIONS * distance;
		
		return price;
	}

	// returning a station object based on the name input
	@Override
	public Station getStationByStationName(String stationName) {
		
		return stationDao.searchStationByStationName(stationName);
	}

}