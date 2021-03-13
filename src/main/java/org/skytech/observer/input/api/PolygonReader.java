package org.skytech.observer.input.api;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skytech.observer.input.model.InputDataValue;
import org.skytech.observer.input.service.JSONFromUrlScrapper;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PolygonReader {
    DateTime readedDate;

    public List<InputDataValue> getInputDataPricesListByDate(DateTime beginDate, DateTime endDate, String name) {
        List<InputDataValue> inputDataPricesList = null;
        readedDate = beginDate;
        String beginDateStr = beginDate.toString().substring(0, 10);
        String endDateStr = endDate.toString().substring(0, 10);
        String token = "EWlMht7GAk8vgnV0EWSp92C26aFYKCo2";
        try {
            String url = "https://api.polygon.io/v2/aggs/ticker/" + name +
                    "/range/1/day/" + beginDateStr + "/" + endDateStr +
                    "?apiKey=" + token;
            JSONObject JSONResponse = readJsonFromUrl(url);
            inputDataPricesList = parseJsonToPricesObject(JSONResponse, name);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return inputDataPricesList;
    }

    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
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

    private static String readAll(Reader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int cursorPoint;
        while ((cursorPoint = reader.read()) != -1) {
            stringBuilder.append((char) cursorPoint);
        }
        return stringBuilder.toString();
    }

    public List<InputDataValue> parseJsonToPricesObject(JSONObject jsonObject, String name) throws JSONException, IOException, ParseException {
        ArrayList<InputDataValue> inputDataPrices = new ArrayList<InputDataValue>();
        JSONArray jsonArray = jsonObject.getJSONArray("results");
        for(int i = 0; i < (jsonArray.length() - 1);i++){
            double price = jsonArray.getJSONObject(i).getDouble("c");
            long dateLong = jsonArray.getJSONObject(i).getLong("t");
            DateTime readedDate = new DateTime(dateLong);
            inputDataPrices.add(new InputDataValue(readedDate, price));
        }
        return inputDataPrices;
    }
}
