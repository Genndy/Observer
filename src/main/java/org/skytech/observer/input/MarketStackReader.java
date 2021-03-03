package org.skytech.observer.input;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skytech.observer.input.model.InputDataPrice;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MarketStackReader {

    public void init() throws IOException, JSONException, ParseException {
        JSONObject jsonObject = JSONFromUrlScrapper
                .readJsonFromUrl("https://api.marketaux.com/v1/news/all?symbols=AAPL&filter_entities=true&language=en&api_token=8z3QJfJCmJgl2tnq1B9pWWHuSGEsWnFzjfigDyUL");
        parseJsonToNewsObject(jsonObject);
    }

    public void parseJsonToNewsObject(JSONObject jsonObject) throws JSONException, IOException, ParseException {
        ArrayList<InputDataPrice> inputPrices = new ArrayList<InputDataPrice>();
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for(int i = 0; i < (jsonArray.length() - 1);i++){
            String jsonDate = jsonArray.getJSONObject(i).getString("date");
            jsonDate = jsonDate.replace("T", " ");
            SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss+SSSS", Locale.ENGLISH);
            Date date = formatter.parse(jsonDate);
            double price = jsonArray.getJSONObject(i).getDouble("adj_volume");
            inputPrices.add(new InputDataPrice(date, price));
        }
    }
}
