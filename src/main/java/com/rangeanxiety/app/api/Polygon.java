package com.rangeanxiety.app.api;

import com.rangeanxiety.app.service.Network;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class Polygon {


    @Autowired
    Network network;


    public String convertToJSONpolygon(long arr[], int count) {


        JSONObject featureCollection = new JSONObject();
        JSONArray features = new JSONArray();
        JSONObject feature = new JSONObject();
        feature.put("type", "Features");
        JSONArray coor = new JSONArray();
        JSONArray brac = new JSONArray();
        JSONObject geometry = new JSONObject();
        geometry.put("type", "Polygon");

        for (int i = 0; i < count; i++) {


            coor.add(network.vertices.get(arr[i]));

        }

        brac.add(coor);
        geometry.put("coordinates", brac);
        feature.put("geometry", geometry);
        features.add(feature);
        featureCollection.put("features", features);

        return featureCollection.toJSONString();

    }


}

