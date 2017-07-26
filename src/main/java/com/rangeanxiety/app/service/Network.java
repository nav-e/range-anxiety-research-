                package com.rangeanxiety.app.service;

                import java.io.*;
                import java.lang.*;
                import java.util.ArrayList;
                import java.util.HashMap;
                import java.util.List;
                import java.util.Map;
                import java.util.Random;
                import java.util.Collection;
                import java.util.Iterator;
                import java.util.Stack;

                import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
                import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
                import org.openstreetmap.osmosis.core.domain.v0_6.Node;
                import org.openstreetmap.osmosis.core.task.v0_6.Sink;
                import org.springframework.beans.factory.annotation.Autowired;
                import org.springframework.stereotype.Repository;



                import com.google.common.collect.Multimap;
                import com.google.common.collect.ArrayListMultimap;


                import crosby.binary.osmosis.OsmosisReader;
                import com.rangeanxiety.app.api.Polygon;
                import com.rangeanxiety.app.api.Marker;
                import com.rangeanxiety.app.entities.Vertex;
                import com.rangeanxiety.app.helper.Haversine;




                @Repository
                public class Network {
                    public Multimap<Long, Double> ver = ArrayListMultimap.create();

                    long vertexKey; int count,nodecount=0;

                    @Autowired
                    Haversine hav;
                    @Autowired
                    Polygon polygon;
                    @Autowired
                    Marker marker;

                    private Map<Long, Vertex> vertices = new HashMap<>();


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
                                 if (entity instanceof Node) {
                            Node node = (Node) entity;

                                        ver.put(node.getId(), node.getLatitude());
                                        ver.put(node.getId(), node.getLongitude());
                                        nodecount++;

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


                    public long[] getRandomVertexId() {
                        Random random = new Random();
                        List<Long> keys = new ArrayList<Long>(ver.keySet());

                        long arr[] = new long[nodecount];

                        int i;

                        String result=null;
                        for (i = 0; i < nodecount; i++) {


                            long randomKey = keys.get(random.nextInt(keys.size()));

                            arr[i] = randomKey;
                        }

                        return arr;
                    }

                    public double getRandomLat() {

                        double lat;
                        Random random = new Random();
                        List<Long> keys = new ArrayList<Long>(ver.keySet());
                        long randomKey = keys.get(random.nextInt(keys.size()));
                        vertexKey = randomKey;
                        Collection<Double> coor = ver.get(vertexKey);
                        lat = coor.iterator().next();

                        return lat;
                    }


                    public double getRandomLon() {

                        double lat, lon;
                       Collection<Double> coor = ver.get(vertexKey);
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
                public String  getNodes(double lat, double lng, int choice)

                {
                long arr[]=new long[nodecount];
                long key[]=new long[nodecount];
                String result=null;
                arr=getRandomVertexId();

                double lat1,lon1;
                lat1=lat;lon1=lng;


                 count=0;
                double lat2 , lon2;

                for (int i = 0; i < nodecount; i++) {
                     lat2=getLat(arr[i]);
                     lon2=getLon(arr[i]);
                double checkdis=hav.Havdistance(lat1, lon1, lat2, lon2);
                if ((checkdis>10.9)&&(checkdis<11.1))
                {key[count]= arr[i];

                count++;
                }
                 }



                    result= arrangedCoordinate(key,choice) ;


                 return result;}




                public  String arrangedCoordinate(long arr[],int choice)
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
             result=travellingSalesman(DistanceMatrix, arr,choice) ;
        } catch (Exception e) {
           //do nothing
        }



                return result;
                }

                public String travellingSalesman(double DistanceMatrix[][], long arr[],int choice) throws Exception
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

                     switch (choice)
                     {
                     case 1:
                     result=polygon.convertToJSONpolygon(newarr,count) ;
                     break;
                     case 2:
                     result=marker.convertToJSONmarker(newarr,count) ;
                     break;
                     default:
                     break;
                    }



                return result;
                }


                }
