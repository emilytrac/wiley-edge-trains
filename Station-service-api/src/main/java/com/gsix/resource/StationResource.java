package com.gsix.resource;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsix.entity.Station;
import com.gsix.service.StationService;

@RestController
public class StationResource {

	@Autowired
	private StationService stationService;
	
	@GetMapping(path="/stations/{sid}",produces = MediaType.APPLICATION_JSON_VALUE)
	public Station searchStationById(@PathVariable("sid") int id) {
		return stationService.searchStationById(id);
	}
	
	@GetMapping(path = "/stations",produces =MediaType.APPLICATION_JSON_VALUE)
	public Collection<Station> getAllStationResource(){
		return stationService.getAllStations();
	}
}