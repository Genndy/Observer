package org.skytech.observer.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.*;
import org.json.JSONArray;

public class SimpleJsonReader {
/*
    public static HashMap<Integer, JSONObject> deals = new HashMap<>();
    public static HashMap<Integer, JSONObject> FilteredDeals = new HashMap<>();
    public static Set<String> HotelKeys;

    static JSONArray HOTELARRAY; // assign in retrive method

    public static void inti() throws IOException, JSONException {
        JSONObject json = readJsonFromUrl("https://offersvc.expedia.com/offers/v2/getOffers?scenario=deal-finder&page=foo&uid=foo&productType=Hotel");
        retriveJsonObject(json);

        for (int i = 0; i < HOTELARRAY.length(); i++) {
            deals.put(i, HOTELARRAY.getJSONObject(i));
        }
        HotelKeys = HOTELARRAY.getJSONObject(0).keySet(); // set contains 7 major hotel info

    }

    public static HashMap<String, JSONObject> getJsonById(String index) throws IOException, JSONException {
        HashMap<String, JSONObject> dealsAttributes = new HashMap<>();
        int id = Integer.parseInt(index);
        for (String item : HotelKeys) {
            dealsAttributes.put(item, HOTELARRAY.getJSONObject(id).getJSONObject(item));

        }
        return dealsAttributes;
    }

    public static HashMap<Integer ,JSONObject> getValue(String country) throws JSONException {

        for (int i = 0; i <HOTELARRAY.length(); i++) {

            if (HOTELARRAY.getJSONObject(i).getJSONObject("hotelInfo").getString("hotelCountryCode").equals(country))
                FilteredDeals.put(i ,HOTELARRAY.getJSONObject(i));


        }

        return FilteredDeals;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);

            return json;
        } finally {
            is.close();
        }
    }

    public static void retriveJsonObject(JSONObject jsonObj) throws JSONException {

        for (Object key : jsonObj.keySet()) {

            String keyStr = (String) key;
            Object keyvalue = jsonObj.get(keyStr);
            // System.out.println("key: " + keyStr + " value: " + keyvalue);
            // key "Hotel" =>50
            if (keyStr.equals("Hotel")) {

                HOTELARRAY = (JSONArray) keyvalue;

                //arraylist size 50  0 - 49 json object
            }
            if (keyvalue instanceof JSONArray) {

                for (Object o : (JSONArray) keyvalue) {

                    if (o instanceof Integer == false) {

                        JSONObject obj = (JSONObject) o;

                        for (Object k : obj.keySet()) {

                            String keyStr2 = (String) k;
                            Object keyvalue2 = obj.get(keyStr2);

                            //   System.out.println("key: " + keyStr2 + " value: " + keyvalue2);
                            if (keyvalue2 instanceof JSONObject) {
                                retriveJsonObject((JSONObject) keyvalue2);

                            }
                        }
                    }

                }
            }
            if (keyvalue instanceof JSONObject) {
                retriveJsonObject((JSONObject) keyvalue);

            }

        }

    }

    public static void main(String[] args) throws IOException, JSONException {
        JsonReader.inti();
        // System.out.println(HOTELARRAY.getJSONObject(0).getJSONObject("hotelInfo").getString("hotelCountryCode").equals("HUN"));
        // System.out.println(HOTELARRAY.getJSONObject(0).getJSONObject("hotelInfo").getString("hotelCountryCode"));
    }
 */
}