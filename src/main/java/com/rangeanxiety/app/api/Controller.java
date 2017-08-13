package com.rangeanxiety.app.api;

import com.rangeanxiety.app.service.Network;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import de.topobyte.osm4j.core.model.impl.Node;


@RestController
@RequestMapping("/greennav")


public class Controller {

    @Autowired
    Network network;

    @RequestMapping("/polygon")
    public String polygon(@RequestParam(required = false, value = "startlat") Double startlat,
                          @RequestParam(required = false, value = "startlng") Double startlng,
                          @RequestParam(required = false, value = "range") Double range,
                          @RequestParam(required = false, value = "startNode") Long startNode) {
        String result;
        if (range == null) {

            range = 11d;
        }
        
        if ((startlat == null)&&(startlng == null)&&(startNode == null)) {
            startlat = network.getRandomLat();
            startlng = network.getRandomLon();
            result = network.getNodes(startlat, startlng, range, 1);
        }
        else if ((startlat != null)&&(startlng != null)){
        result = network.getNodes(startlat, startlng, range, 1);
        if (result==null){
           throw new coordinateException();}}
        
        else {result = network.getNodes(startNode, range, 1);
        if (result==null)
           throw new nodeIdException();}
        
        

        
        
        return result;

    }

    @RequestMapping("/marker")
    public String marker(@RequestParam(required = false, value = "startlat") Double startlat,
                         @RequestParam(required = false, value = "startlng") Double startlng,
                         @RequestParam(required = false, value = "range") Double range,
                         @RequestParam(required = false, value = "startNode") Long startNode) {
        String result;
        if (range == null) {

            range = 11d;
        }
        
        if ((startlat == null)&&(startlng == null)&&(startNode == null)) {
            startlng = network.getRandomLon();
            startlat = network.getRandomLat();
            result = network.getNodes(startlat, startlng, range, 2);
        }
        else if ((startlat != null)&&(startlng != null)){
        
        result = network.getNodes(startlat, startlng, range, 2);
        if (result==null){
           throw new coordinateException();}}
        
        else{result = network.getNodes(startNode, range, 2);
        if (result==null)
           throw new nodeIdException();}
        
        return result;


}

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Incorrect NodeId. Please enter a correct NodeId. ")
 public class nodeIdException extends RuntimeException   {
     
 }
 
 @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Incorrect Coordinates. Please enter correct coordinates.")
 public class coordinateException extends RuntimeException  {
     // ...
 }
}












