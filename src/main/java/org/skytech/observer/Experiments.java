package org.skytech.observer;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import sun.util.calendar.BaseCalendar;

import java.io.IOException;
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
}
