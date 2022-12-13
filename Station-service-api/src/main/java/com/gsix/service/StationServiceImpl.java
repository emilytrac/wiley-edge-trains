package com.gsix.service;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsix.entity.Station;
import com.gsix.persistence.StationDao;

@Service
public class StationServiceImpl implements StationService {

	@Autowired
	private StationDao stationDao;

	@Override
	public Collection<Station> getAllStations() {
		return stationDao.findAll();
	}

	static final double ADJACENT_STATIONS = 5.00;
	
	@Override
	public double checkRouteCost(String sourceStation, String destStation) {
		Station source = stationDao.searchStationByStationName(sourceStation);
		Station des = stationDao.searchStationByStationName(destStation);
		int sourceId = source.getStationId();
		int desId = des.getStationId();
		
		int distance = 0;
		
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

}