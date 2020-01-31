package com.example.android.findme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser
{

    private HashMap<String,String> getSingleNearbyplace(JSONObject googlePlaceJSON)
    {
        HashMap<String,String> googlePlaceMap = new HashMap<>();

        String nameOfPlace="-NA-";
        String vicinity="-NA-";
        String latitude="";
        String longitude="";
        String reference="";


        try {
            if(!googlePlaceJSON.isNull("name"))
            {
                nameOfPlace=googlePlaceJSON.getString("name");
            }
            if(!googlePlaceJSON.isNull("vicinity"))
            {
                vicinity=googlePlaceJSON.getString("vicinity");
            }

            latitude =googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude =googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference=googlePlaceJSON.getString("reference");

            googlePlaceMap.put("place_name",nameOfPlace);
            googlePlaceMap.put("vicinity",vicinity);
            googlePlaceMap.put("lat",latitude);
            googlePlaceMap.put("lng",longitude);
            googlePlaceMap.put("reference",reference);

        } catch (JSONException e) {
            e.printStackTrace();
        }
            return googlePlaceMap;
    }


    private List<HashMap<String,String>> getAllNearbyPlace(JSONArray jsonArray)
    {


        List<HashMap<String,String>> NearbyPlacesList =  new ArrayList<>();

        HashMap<String ,String> NearbyPlacesMap = null ;

        for(int i = 0 ;i<jsonArray.length();i++)
        {
            try {
                NearbyPlacesMap = getSingleNearbyplace((JSONObject) jsonArray.get(i));
                NearbyPlacesList.add(NearbyPlacesMap);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return  NearbyPlacesList;
    }

    public List<HashMap<String,String>> parse(String JSONdata)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject =new JSONObject(JSONdata);
            jsonArray = jsonObject.getJSONArray("results");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return getAllNearbyPlace(jsonArray);
    }
}

