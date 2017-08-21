package com.rangeanxiety.app.service;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;


public class NetworkTest {
    @Autowired
    Network network;

    @Before
    public void setUp() {
        network = new Network();
        try {
            network.readOSMFile();
        } catch (FileNotFoundException e) {
        }
    }


    @Test
    public void testgetLat() {

        double expectLat = 32.550243;
        double lat;
        lat = network.getLat(1371382719);
        assertEquals(expectLat, lat, 0.1);
    }

    @Test
    public void testgetLon() {

        double expectLon = 35.7073606;
        double lon;
        lon = network.getLon(1371382719);
        assertEquals(expectLon, lon, 0.1);
    }

}