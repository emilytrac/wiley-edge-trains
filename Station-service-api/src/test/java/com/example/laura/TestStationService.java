package com.example.laura;

import com.gsix.entity.Station;
import com.gsix.entity.StationList;
import com.gsix.persistence.StationDao;
import com.gsix.service.StationServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
class TestStationService {

    @InjectMocks
    private StationServiceImpl stationServiceImpl;
    @Mock
    private StationDao stationdao;
    private AutoCloseable autoCloseable;

    @BeforeEach
	void setUp() throws Exception {
		/*
		 * tells mockito to scan the test class instance 
		 * for all the fields annotated with @Mock
		 * and initialize those fields as mocks
		 * 
		 */
		autoCloseable=MockitoAnnotations.openMocks(this);
	}

    @AfterEach
	void tearDown() throws Exception {
		autoCloseable.close();
	}

    // postive test
    @Test
    void getAllStationsPos() {
    	
    	List<Station> stations = new LinkedList<Station>();
    	stations.add(new Station(02, "Waterloo"));
    	stations.add(new Station(03, "Paddington"));
       
    	when(stationdao.findAll()).thenReturn(stations);
    	
    	assertEquals(stations, stationServiceImpl.getAllStations().getStations());
    }
    
    // negative test
    @Test
    void getAllStationsNeg() {
    	
    	StationList stationList = new StationList();

    	when(stationdao.findAll()).thenReturn(null);
    	assertEquals(stationList, stationServiceImpl.getAllStations());
    }

    //checking the search by name method 
    @Test
    void searchStationByNamePos() {
    	Station station = new Station(02, "Waterloo");
    	when(stationdao.searchStationByStationName("Waterloo")).thenReturn(station);
    	assertEquals(station, stationServiceImpl.getStationByStationName("Waterloo"));
    	//assertTrue(stationServiceImpl.searchStationById(01)!=null);
    }
    
    // negative test not needed as the user never gets the option to input their own station due to dropdown
    
    // checking the route cost method
    @Test
    void checkRouteCostTest() {
    	Station sourceStation = new Station(2, "Waterloo");
    	Station destinationStation = new Station(3, "Paddington");
    	
    	when(stationdao.searchStationByStationName("Waterloo")).thenReturn(sourceStation);
    	when(stationdao.searchStationByStationName("Paddington")).thenReturn(destinationStation);
    	
    	double result = stationServiceImpl.checkRouteCost("Waterloo", "Paddington");
    	double expected = 5;
    	
    	assertEquals(result, expected);
    
    }
    
    // negative test not needed as the user never gets the option to input their own station due to dropdown

}