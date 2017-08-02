            package com.rangeanxiety.app.api;

            import com.rangeanxiety.app.service.Network;
            import org.springframework.stereotype.Repository;
            import org.springframework.beans.factory.annotation.Autowired;
            import org.json.simple.JSONObject;
            import org.json.simple.JSONArray;


            @Repository
            public class Marker {


                        @Autowired
                        Network network;


                        public String convertToJSONmarker(long arr[],int count)  {

                        JSONObject featureCollection = new JSONObject();
                        JSONArray features = new JSONArray();
                        for (int i = 0; i < count; i++) {

                        JSONObject feature = new JSONObject();

                        JSONObject geometry = new JSONObject();
                        feature.put("type", "Feature");
                        geometry.put("type", "Point");

                        geometry.put("coordinates", network.vertices.get(arr[i]));
                        feature.put("geometry", geometry);
                        features.add(feature);
                        }





                        featureCollection.put("features", features);

                        return featureCollection.toJSONString();

                    }

            }

