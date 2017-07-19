package com.rangeanxiety.app.api;

import java.lang.*;


import com.rangeanxiety.app.service.Network;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/greennav")


public class Controller {

    @Autowired
    Network network = new Network();

    @RequestMapping("/range")
    public String route(@RequestParam(required = false, value = "startlat") Double startlat,
                        @RequestParam(required = false, value = "startlng") Double startlng,
                        @RequestParam(required = false, value = "range") Double range) {

        if (startlat == null) {
            startlat = network.getRandomLat();
        }

        if (startlng == null) {
            startlng = network.getRandomLon();
        }

        if (range == null) {

            range = 20d;
        }

        String result;
        result = network.get1000RandomVertexId(startlat,startlng);

    }
}

        
        
	

    


