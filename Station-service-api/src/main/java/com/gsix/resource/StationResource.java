package com.gsix.resource;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsix.entity.Station;
import com.gsix.entity.StationList;
import com.gsix.service.StationService;

@RestController
public class StationResource {

	@Autowired
	private StationService stationService;
	
	@GetMapping(path="/stations/{source}/{des}",produces = MediaType.TEXT_PLAIN_VALUE)
	public double stationResource(@PathVariable("source") String source, @PathVariable("des") String des) {
		return stationService.checkRouteCost(source, des);
	}
	
	@GetMapping(path = "stations/{stationName}", produces = MediaType.APPLICATION_JSON_VALUE)
	    public Station stationResource(@PathVariable("stationName") String stationName) {
	        return stationService.getStationByStationName(stationName);
	}
	
	@GetMapping(path = "/stations",produces =MediaType.APPLICATION_JSON_VALUE)
	public StationList getAllStationResource(){
		return stationService.getAllStations();
	}
}