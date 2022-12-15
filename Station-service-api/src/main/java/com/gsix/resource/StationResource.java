package com.gsix.resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gsix.entity.Station;
import com.gsix.entity.StationList;
import com.gsix.service.StationService;

@RestController
public class StationResource {

	@Autowired
	private StationService stationService;
	
	// GET method for determining the cost of travel between two stations
	@GetMapping(path="/stations/{source}/{des}",produces = MediaType.TEXT_PLAIN_VALUE)
	public String stationCheckResource(@PathVariable("source") String source, @PathVariable("des") String des) {
		return String.valueOf(stationService.checkRouteCost(source, des));
	}
	
	// GET method for returning a station object on input of a station name - for input of station id into transaction
	@GetMapping(path = "stations/{stationName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Station stationResource(@PathVariable("stationName") String stationName) {
	        return stationService.getStationByStationName(stationName);
	}
	
	// GET method for returning all stations - used so that customer can pick from drop down
	@GetMapping(path = "/stations",produces =MediaType.APPLICATION_JSON_VALUE)
	public StationList getAllStationResource(){
		return stationService.getAllStations();
	}
}