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
	public Station searchStationById(int id) {
		return stationDao.findById(id).orElse(null);
	}

	@Override
	public Collection<Station> getAllStations() {
		return stationDao.findAll();
	}

}