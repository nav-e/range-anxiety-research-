package com.rangeanxiety.app.api;

import com.rangeanxiety.app.service.Network;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/greennav")


public class Controller {

    @Autowired
    Network network;

    @RequestMapping("/polygon")
    public String polygon(@RequestParam(required = false, value = "startlat") Double startlat,
                          @RequestParam(required = false, value = "startlng") Double startlng,
                          @RequestParam(required = false, value = "range") Double range) {

        if ((startlat == null)&&(startlng == null)) {
            startlat = network.getRandomLat();
            startlng = network.getRandomLon();
        }
        if (range == null) {

            range = 11d;
        }

        String result;
        result = network.getNodes(startlat, startlng, range, 1);
        return result;

    }

    @RequestMapping("/marker")
    public String marker(@RequestParam(required = false, value = "startlat") Double startlat,
                         @RequestParam(required = false, value = "startlng") Double startlng,
                         @RequestParam(required = false, value = "range") Double range) {

        if ((startlat == null)&&(startlng == null)) {
            startlat = network.getRandomLat();
            startlng = network.getRandomLon();
        }
        if (range == null) {

            range = 11d;
        }

        String result;
        result = network.getNodes(startlat, startlng, range, 2);
        return result;

    }


}








