package org.skytech.observer.input.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.*;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.sun.jndi.ldap.Connection;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.skytech.observer.input.service.JSONFromUrlScrapper;
import org.skytech.observer.input.model.InputDataNews;
import sun.net.www.protocol.http.Handler;

import static java.net.Proxy.Type.HTTP;

public class MarketAuxNewsReader {

    public List<InputDataNews> parseJsonToNewsObject(JSONObject jsonObject, String symbolName) throws JSONException, IOException, ParseException {
        List<InputDataNews> inputDataNewsList = new ArrayList<InputDataNews>();
        JSONArray jsonArrayData = jsonObject.getJSONArray("data");
        for(int i = 0; i < jsonArrayData.length(); i++){
            JSONObject jsonData = jsonArrayData.getJSONObject(i);
            System.out.println(jsonData.toString());

            String dateStr = jsonData.getString("published_at");
            dateStr = dateStr.replaceFirst("Z", "");
            DateTime date = DateTime.parse(dateStr);

            String symbol = jsonData.getString(symbolName); // will get from...
            String description = jsonData.getString("description");
            String source = jsonData.getString("source");
            String headTitle = jsonData.getString("title");
            String url = jsonData.getString("url");
            String lang = jsonData.getString("language");
            String text = Jsoup.parse(Jsoup.connect(url).get().text()).text();

//            InputDataNews inputNews = new InputDataNews(symbol, description, date, source, "акция", text);
//
  //          inputDataNewsList.add(inputNews);
//            System.out.println(inputNews.toString());
        }
        return inputDataNewsList;
    }

    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        URL urlConn = new URL(url);
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

    public List<InputDataNews> getInputDataNewsListByDate(DateTime date, String name) {
        List<InputDataNews> inputDataNewsList = null;
        try {
        String token = "8z3QJfJCmJgl2tnq1B9pWWHuSGEsWnFzjfigDyUL";
        String dateStr = date.toString().substring(0, 10);
//        String testUrl = "https://api.marketaux.com/v1/news/all?symbols=AAPL,TSLA&filter_entities=true&language=en&api_token=8z3QJfJCmJgl2tnq1B9pWWHuSGEsWnFzjfigDyUL";
        String url = "https://api.marketaux.com/v1/news/all?symbols="+name + "&published_on=" +
                dateStr +
                "&filter_entities=true&language=en&api_token=" + token;
        JSONObject JSONResponse = readJsonFromUrl(url);
        inputDataNewsList = parseJsonToNewsObject(JSONResponse, name);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return inputDataNewsList;
    }
}
