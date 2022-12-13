package com.example.laura;

import com.gsix.entity.Station;
import com.gsix.persistence.StationDao;
import com.gsix.service.StationServiceImpl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    @Test
    void getAllStations() {
    	//List<Station> stations = new LinkedList<Station>();
    	List<Station> stations = new LinkedList<Station>();
    	stations.add(new Station(02, "Waterloo"));
        //assertEquals(stations, stationServiceImpl.getAllStations().size()>0);
    	when(stationdao.findAll()).thenReturn(stations);
    	
    	assertTrue(stationServiceImpl.getAllStations().size()>0);
    }

    @Test
    void searchStationByID() {
    	when(stationdao.findById(01)).thenReturn(new Station(01, "Victoria"));
        Station station = new Station(01, "Victoria");
    	assertEquals(station, stationServiceImpl.searchStationById(01));
    	//assertTrue(stationServiceImpl.searchStationById(01)!=null);
        }

}