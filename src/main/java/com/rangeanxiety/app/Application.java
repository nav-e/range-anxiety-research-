package com.rangeanxiety.app;

import java.lang.*;

import com.rangeanxiety.app.service.Network;








public class Application {

	public static void main(String[] args) throws Exception
    {
        Network network= new Network();
        
        
        network.initialize();
        
        long result[]= new long[50];
        result= network.get50RandomVertexId();
        for(int i=0;i<50;i++)
        
        {//System.out.println(result[i]+" ");
        }
        network.converttoJson(result);
        
        long source, target;
        
        source= network.getRandomVertexId();
        System.out.println("Source key:"+ source);
        target=network.getRandomVertexId();
        System.out.println("Target key:"+ target);
        
		
		
        
}}
         
        
        
        
        
	

    


