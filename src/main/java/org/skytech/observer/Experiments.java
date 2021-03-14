package org.skytech.observer;

import org.joda.time.DateTime;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import sun.util.calendar.BaseCalendar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Experiments {
    public static String testParse(String url) throws IOException {
        Connection connect = Jsoup.connect(url);
        Document doc = connect.get();
        String html = doc.text();
        String text = Jsoup.parse(html).text();
        return text;
    }

    public static BaseCalendar.Date date() throws ParseException {
        String str = "2021-03-01T05:22:56.000000Z";
        str = str.replaceFirst("T", " ");
        str = str.replaceFirst("Z", "");
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSSSSS", Locale.ENGLISH);
        Date date = formatter.parse(str);
        System.out.println(date);
        System.out.println(date.toString());
        return null;
    }

    private String parseArrToString(String[] arr){
        String str = "";
        for(int i = 0; i < arr.length; i++){
            str = str + arr[i] + " ";
        }
        return str;
    }

    static private int getTotalDayToLoadCount() throws Exception {
        long dateBegin = consoleListenDateTime("YYYY-MM-dd").getMillis();
        long datePlannedToFill = consoleListenDateTime("YYYY-MM-dd").getMillis();
        long dateCompare = datePlannedToFill - dateBegin;
        long days = dateCompare / 1000 / 60 / 60 / 24;
        return (int)days;
    }

    static private DateTime consoleListenDateTime(String message) throws Exception{
        System.out.println(message);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = "";
        DateTime date;
        try {
            s = br.readLine();
            if (s.equals("")) {
                System.out.println("Отмена");
            } else if (s.toCharArray().length >= 100) {
                System.err.println("Ошибка: текст должен иметь менее 100 символов");
            } else {
//                SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:MM:SS+ZZZZZ", Locale.ENGLISH);
                date = DateTime.parse(s);
                return date;
            }
        } catch (Exception e) {
            System.err.println("Произошла ошибка при попытке ввода");
            e.printStackTrace();
        }
        throw new IOException();
    }
    public void getHttpStatus(){
        String url = "https://finance.yahoo.com/news/fed-set-launch-multitrillion-dollar-155501288.html";
        try {

            Connection conn = Jsoup.connect(url).ignoreHttpErrors(true);
            int status = conn.execute().statusCode();
//            String str = Jsoup.parse(Jsoup.connect(url).get().text()).text();
            System.out.println(status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
