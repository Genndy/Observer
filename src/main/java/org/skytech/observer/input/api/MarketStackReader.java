package org.skytech.observer.input.api;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skytech.observer.input.model.InputDataNews;
import org.skytech.observer.input.service.JSONFromUrlScrapper;
import org.skytech.observer.input.model.InputDataValue;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MarketStackReader {

    public void init() throws IOException, JSONException, ParseException {
        JSONObject jsonObject = JSONFromUrlScrapper
                .readJsonFromUrl("https://api.marketaux.com/v1/news/all?symbols=AAPL&filter_entities=true&language=en&api_token=8z3QJfJCmJgl2tnq1B9pWWHuSGEsWnFzjfigDyUL");
    }

    public List<InputDataValue> getInputDataPricesListByDate(DateTime beginDate, DateTime endDate, String name) {
        List<InputDataValue> inputDataPricesList = null;
        String beginDateStr = beginDate.toString().substring(0, 10);
        String endDateStr = endDate.toString().substring(0, 10);

        // date_from date_to
        try {
            String token = "1c2a3cc95dcebf702b3c52b2ac71278b";
            String dateStr = null;
//                    = date.toString().substring(0, 10);
            String url = "https://api.marketstack.com/v1/eod?symbols="+name+
                    "&date_from=" + dateStr + "&date_to"
                    + "&filter_entities=true&language=en&api_token=" + token;
            JSONObject JSONResponse = readJsonFromUrl(url);

// https://api.marketstack.com/v1/eod?access_key=1c2a3cc95dcebf702b3c52b2ac71278b&symbols=AAPL&date_from=2020-11-01&date_to=2020-12_31
            // https://api.marketstack.com/v1/eod?access_key=1c2a3cc95dcebf702b3c52b2ac71278b&symbols=AAPL

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
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for(int i = 0; i < (jsonArray.length() - 1);i++){
            String jsonDate = jsonArray.getJSONObject(i).getString("date");
//            jsonDate = jsonDate.replace("T", " ");
            DateTime date = DateTime.parse(jsonDate);
            double price = jsonArray.getJSONObject(i).getDouble("adj_volume");
            inputDataPrices.add(new InputDataValue(date, price));
        }
        return inputDataPrices;
    }
}
