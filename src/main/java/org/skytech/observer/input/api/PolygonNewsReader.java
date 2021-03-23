package org.skytech.observer.input.api;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.skytech.observer.input.model.InputDataNews;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class PolygonNewsReader {

    public List<InputDataNews> parseJsonToNewsObject(JSONArray jsonArrayData, String symbolName) throws JSONException, IOException, ParseException {
        List<InputDataNews> inputDataNewsList = new ArrayList<InputDataNews>();
        for(int i = 0; i < jsonArrayData.length(); i++){
            JSONObject jsonData = jsonArrayData.getJSONObject(i);
            System.out.println(jsonData.toString());
            String dateStr = jsonData.getString("timestamp");
            dateStr = dateStr.replaceFirst("Z", "");
            DateTime date = DateTime.parse(dateStr);
            String description = jsonData.getString("summary");
            String source = jsonData.getString("source");
            String headTitle = jsonData.getString("title");
            String url = jsonData.getString("url");
            String text = jsonData.getString("summary");
            InputDataNews inputNews = new InputDataNews(symbolName, description, date, source, url, headTitle, text);
            inputDataNewsList.add(inputNews);
            System.out.println(inputNews.toString());
        }
        return inputDataNewsList;
    }

    public JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
        URL urlConn = new URL(url);
        InputStream is = new URL(url).openStream();

        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            JSONArray json = new JSONArray(jsonText);
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

    public List<InputDataNews> getInputDataNewsListByDate(int page, String name) {

        // https://api.polygon.io/v1/meta/symbols/AAPL/news?perpage=50&page=1&apiKey=*
        List<InputDataNews> inputDataNewsList = null;
        try {
        String[] tokens = {"P2z6sI_gKGe8J0XphpPrcsK8jZoXdsL5", "EWlMht7GAk8vgnV0EWSp92C26aFYKCo2",
                "y1yulUeMzGWF3tzR4etfUVL5Hc9VE_pk", "PV2ySRnDwTEXIfNmA5miSLTVpY56TRl8", "KXvwR5LJpspA2jv44b32QFYttI2HY0Ek",
                "yeqUAMSQemGnAz5jmdtYfps3kKFEN2lD", "mP00DUhcWqbet7cjgUNpPEp9QaWBdpnM"};
        String url = "https://api.polygon.io/v1/meta/symbols/" +name + "/news?perpage=50&page=" + page + "&apiKey=" + tokens[page % tokens.length];
        JSONArray JSONArray = readJsonFromUrl(url);
        inputDataNewsList = parseJsonToNewsObject(JSONArray, name);
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
