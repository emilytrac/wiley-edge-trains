package com.gsix.service;
import java.util.Collection;

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

	static final double ADJACENT_STATIONS = 5.00;
	
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

	@Override
	public Station getStationByStationName(String stationName) {
		
		return stationDao.searchStationByStationName(stationName);
	}

}