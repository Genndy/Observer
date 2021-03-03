package org.skytech.observer.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.skytech.observer.input.model.InputDataNews;

public class MarketAuxReader {

//    String strJson =
    public void init() throws IOException, JSONException, ParseException {
        JSONObject jsonObject = JSONFromUrlScrapper.readJsonFromUrl("https://api.marketaux.com/v1/news/all?symbols=AAPL&filter_entities=true&language=en&api_token=8z3QJfJCmJgl2tnq1B9pWWHuSGEsWnFzjfigDyUL");
        parseJsonToNewsObject(jsonObject);
    }

    public void parseJsonToNewsObject(JSONObject jsonObject) throws JSONException, IOException, ParseException {
        ArrayList<InputDataNews> news = new ArrayList<InputDataNews>();
        JSONArray jsonArrayData = jsonObject.getJSONArray("data");
        for(int i = 0; i < jsonArrayData.length(); i++){
            JSONObject jsonData = jsonArrayData.getJSONObject(i);
            System.out.println(jsonData.toString());

            String dateStr = jsonData.getString("published_at");
            dateStr = dateStr.replaceFirst("T", " ");
            dateStr = dateStr.replaceFirst("Z", "");
            SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSSSSS", Locale.ENGLISH);
            Date date = formatter.parse(dateStr);

            String symbol = "AAPL"; // will get from...
            String description = jsonData.getString("description");
            String source = jsonData.getString("source");
            String headTitle = jsonData.getString("title");
            String url = jsonData.getString("url");
            String lang = jsonData.getString("language");
            String[] text = Jsoup.parse(Jsoup.connect(url).get().text()).text().split(" ");

            InputDataNews inputNews = new InputDataNews(symbol, description, date, source, "акция", text);
            System.out.println("Успех!");
            System.out.println(inputNews.toString());
        }
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
}
