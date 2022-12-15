package com.gsix.persistence;

import org.springframework.stereotype.Repository;

import com.gsix.entity.Station;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface StationDao extends JpaRepository<Station, Integer> {

	public Station searchStationByStationName(String stationName);
	
//	public Collection<Station> getAllStations();
	

}
