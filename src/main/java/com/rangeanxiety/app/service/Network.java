package com.rangeanxiety.app.service;

import java.io.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.ArrayListMultimap;



import com.rangeanxiety.app.entities.Edge;
import com.rangeanxiety.app.entities.Vertex;
import com.rangeanxiety.app.entities.Haversine;



@Repository
public class Network {
    private Multimap<Long, Double> ver = ArrayListMultimap.create();

    long Key; int count,size=48206; long c=0;
    
    
    Haversine hav =new Haversine(); 
    
    


    public void initialize() throws Exception {
        
        readCSVFile();
       

    }

    private void readCSVFile() throws FileNotFoundException {
    int i=0;
    String csvFile = "uk-towns.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
            
            if (i==0)
                {i=99;
                continue;}
                else{

                // use comma as separator
                String[] country = line.split(cvsSplitBy);
try{
              ver.put(c, Double.parseDouble(country[7]));
              ver.put(c, Double.parseDouble(country[8]));
              
              c++;
              }
              catch(NumberFormatException e){

              e.printStackTrace();}

            }

        }} catch (IOException e) {
            e.printStackTrace();
        }

    
    
    }
        


  

    public double getRandomLat() {
        
        double lat;
        Random random = new Random();
        List<Long> keys = new ArrayList<Long>(ver.keySet());
        long randomKey = keys.get(random.nextInt(keys.size()));
        Key = randomKey;
        Collection<Double> coor = ver.get(Key);
        lat = coor.iterator().next();
        return lat;
    }


    public double getRandomLon() {
   
        double lat, lon;
       Collection<Double> coor = ver.get(Key);
        lat = coor.iterator().next();
        Iterator<Double> iter = coor.iterator();
        iter.next();
        lon = iter.next();
        return lon;
    }
    
     public double getLat(long key) {
        double lat;
        Collection<Double> coor = ver.get(key);
        lat=coor.iterator().next(); 
        return lat;
    }
    
     public double getLon(long key) {
     double lon,lat;
     Collection<Double> coor = ver.get(key);
     lat=coor.iterator().next(); 
     Iterator<Double> iter = coor.iterator();
     iter.next(); 
     lon=iter.next();
     return lon;
    }
    
//Call getnodes
public String  getnodes(double lat, double lng)

{System.out.println("Source lat "+lat+" Source lon "+lng);
System.out.println("c "+c);

long key[]=new long[size];
String result=null;


double lat1,lon1;
lat1=lat;lon1=lng;


 count=0;
double lat2 , lon2;

for (int i = 0; i < c; i++) {
     lat2=getLat(i); 
     lon2=getLon(i);
double checkdis=hav.Havdistance(lat1, lon1, lat2, lon2);
if ((checkdis>10.5)&&(checkdis<11))
{key[count]= i;

count++;
}
 }
 
 
try {
    result= arrangedcoordinate(key) ;
} catch (Exception e) {
    e.printStackTrace();
}
 
 return result;}

        


public  String arrangedcoordinate(long arr[])
{
double DistanceMatrix[][]= new double[count][count];
int i,j;
String result=null;
double lat1,lon1,lat2,lon2;
for(i=0; i < count; i++)
{lat1=getLat(arr[i]);
 lon1=getLon(arr[i]);
for(j=0; j<count; j++)
{
if (i==j){
DistanceMatrix[i][j]=0d;continue;}
else {lat2=getLat(arr[j]);
      lon2=getLon(arr[j]);
      DistanceMatrix[i][j]=hav.Havdistance(lat1, lon1, lat2, lon2);
      
      }
}
}
//Call and pass DistanceMatrix to TravellingSalesman;

try {
    result=TravellingSalesman(DistanceMatrix, arr) ;
} catch (Exception e) {
    e.printStackTrace();
}
 
return result;
}

public String TravellingSalesman(double DistanceMatrix[][], long arr[])
{


Stack<Integer> stack= new Stack<Integer>();
long newarr[]=new long[count];
newarr[0]=arr[0];
int[] visited = new int[count];
for(int i=0;i<count;i++)
{visited[i]=0;}
        int keycount=1;
        String result=null;
        visited[0] = 1;
        stack.push(0);
        int element, dst = 0, i;
        double min = Integer.MAX_VALUE;
        boolean minFlag = false;
        
 
        while (!stack.isEmpty())
        {
            element = stack.peek();
            i = 0;
            min = Integer.MAX_VALUE;
            while (i < count)
            {
                if (DistanceMatrix[element][i] > 0 && visited[i] == 0)
                {
                    if (min > DistanceMatrix[element][i])
                    {
                        min = DistanceMatrix[element][i];
                        dst = i;
                        minFlag = true;
                    }
                }
                i++;
            }
            if (minFlag)
            {
                visited[dst] = 1;
                
                newarr[keycount]=arr[dst];
                stack.push(dst);
                
                keycount++;
                minFlag = false;
                continue;
            }
            stack.pop();
        }
        
   

try {
     result=converttoJSONpolygon(newarr) ;
} catch (Exception e) {
    e.printStackTrace();
}
return result;
}
   



    public String converttoJSONpolygon(long arr[]) throws Exception {

        JSONObject featureCollection = new JSONObject();
        JSONArray features = new JSONArray();
        JSONObject feature = new JSONObject();
        feature.put("type", "Features");
        JSONArray coor = new JSONArray();
        JSONArray brac = new JSONArray();
        JSONObject geometry = new JSONObject();
        geometry.put("type", "Polygon");

        for (int i = 0; i < count; i++) {


            coor.add(ver.get(arr[i]));

        }

        brac.add(coor);
        geometry.put("coordinates", brac);
        feature.put("geometry", geometry);
        features.add(feature);
        featureCollection.put("features", features);

        return featureCollection.toJSONString();

    }

   public String converttoJSONmarker(long arr[]) throws Exception {

        JSONObject featureCollection = new JSONObject();
        JSONArray features = new JSONArray();
        for (int i = 0; i < count; i++) {
        
        JSONObject feature = new JSONObject();
        
        JSONObject geometry = new JSONObject();
        feature.put("type", "Feature");
        geometry.put("type", "Point");
       
        geometry.put("coordinates", ver.get(arr[i]));
        feature.put("geometry", geometry);
        features.add(feature);
        }

        
        
        
        
        featureCollection.put("features", features);

        return featureCollection.toJSONString();

    }

}
