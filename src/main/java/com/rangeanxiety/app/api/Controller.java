package com.rangeanxiety.app;

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
        Network network= new Network();
        
        @RequestMapping("/route")
        public String route(@RequestParam(required = false, value = "source") Long source,
			@RequestParam(required = false, value = "target") Long target) {
			
			if (source == null) {
			source = network.getRandomVertexId();
		}
		if (target == null) {
			target = network.getRandomVertexId();
		}
        
        String result;
        result= network.get50RandomVertexId();
        
   
		
		return result;
        
}}
         
        
        
        
        
	

    


