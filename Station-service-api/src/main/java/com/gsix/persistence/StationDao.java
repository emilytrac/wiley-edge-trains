package com.gsix.persistence;

import org.springframework.stereotype.Repository;

import com.gsix.entity.Station;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface StationDao extends JpaRepository<Station, Integer> {

	public List<Station> searchStationById(int stationId);
	
	public Collection<Station> getAllStations();
	
	public Station findById(int stationId);

}
