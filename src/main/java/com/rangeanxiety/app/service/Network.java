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


import org.json.simple.JSONObject;
import org.json.simple.JSONArray;


import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.domain.v0_6.WayNode;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.ArrayListMultimap;


import crosby.binary.osmosis.OsmosisReader;
import com.rangeanxiety.app.entities.Edge;
import com.rangeanxiety.app.entities.Vertex;
import com.rangeanxiety.app.entities.Haversine;


@Repository
public class Network {
    private Multimap<Long, Double> ver = ArrayListMultimap.create();

    long Key;
    private Map<Long, Vertex> vertices = new HashMap<>();

    private Multimap<Long, Edge> edges = MultimapBuilder.hashKeys().hashSetValues().build();


    public void initialize() throws Exception {
        readOSMFile("test.osm.pbf");
       

    }

    private void readOSMFile(String filename) throws FileNotFoundException {
        // On booting up, load the data from file
        File testFile = new File(filename);
        FileInputStream fis = new FileInputStream(testFile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        OsmosisReader reader = new OsmosisReader(bis);
        // The sink serves as a callback, reacting on any nodes and ways found
        reader.setSink(new Sink() {


            @Override
            public void initialize(Map<String, Object> arg0) {
                // do nothing
            }

            @Override
            public void process(EntityContainer entityContainer) {
                Entity entity = entityContainer.getEntity();
                switch (entity.getType()) {
                    case Node:
                        Node node = (Node) entity;
                        ver.put(node.getId(), node.getLatitude());
                        ver.put(node.getId(), node.getLongitude());


                        vertices.put(node.getId(), new Vertex(node.getLatitude(), node.getLongitude()));

                        break;
                    case Way:
                        Way way = (Way) entity;
                        List<WayNode> nodes = way.getWayNodes();

                        for (int i = 1; i < nodes.size(); i++) {

                            long from = nodes.get(i - 1).getNodeId();

                            long to = nodes.get(i).getNodeId();

                            edges.put(from, new Edge(Double.POSITIVE_INFINITY, to));

                            edges.put(to, new Edge(Double.POSITIVE_INFINITY, from));


                        }

                        break;
                    default:
                        break;
                }

            }

            @Override
            public void complete() {
                // do nothing
            }

            @Override
            public void release() {
                // do nothing
            }

        });
        reader.run();
    }


    public String get1000RandomVertexId(double lat, double lng) {
        Random random = new Random();
        List<Long> keys = new ArrayList<Long>(ver.keySet());

        long arr[] = new long[1000];
        
        int i;
        
        String result=null;
        for (i = 0; i < 1000; i++) {


            long randomKey = keys.get(random.nextInt(keys.size()));
            //System.out.println("i " + i + "  " + "key" + " " + randomKey + "  " + "value" + ver.get(randomKey));
            arr[i] = randomKey;
        }
        try {

            result = getnodes(arr,lat,lng);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public double getRandomLat() {
        double lat;
        Random random = new Random();
        List<Long> keys = new ArrayList<Long>(ver.keySet());
        long randomKey = keys.get(random.nextInt(keys.size()));
        Key = randomKey;
        Collection<Double> coor = ver.get(randomKey);
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

public String getnodes(long arr[], double lat, double lng)

{ Haversine hav =new Haversine();  
long key[]=new long[1000];
double lat1,lon1;
lat1=lat;lon1=lng;
System.out.println("source lat "+lat1+" Source lon "+lon1);
int count=0;
 String result=null;
double lat2 , lon2;
//Not a very clean code.
for (int i = 0; i < 1000; i++) {
   Collection<Double> coor = ver.get(arr[i]);
     lat2=coor.iterator().next(); 
     Iterator<Double> iter = coor.iterator();
iter.next(); 
lon2=iter.next();
double checkdis=hav.Havdistance(lat1, lon1, lat2, lon2);
if (checkdis>20)
{key[count]= arr[i];
//System.out.println("Latitude "+lat2+" Longitude "+lon2+" Distance "+checkdis);
count++;
}


     }  
     // System.out.println("count "+count); 
      try {
     result=converttoJSON(key, count) ;
} catch (Exception e) {
    e.printStackTrace();
}
return result;
        }




    public long getRandomVertexId() {
        Random random = new Random();
        List<Long> keys = new ArrayList<Long>(ver.keySet());
        long randomKey = keys.get(random.nextInt(keys.size()));
        return randomKey;
    }



    public String converttoJSON(long arr[],  int count) throws Exception {

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

}
