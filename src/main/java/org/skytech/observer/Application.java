package org.skytech.observer;

import org.joda.time.DateTime;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.skytech.observer.console.MainConsole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

// @SpringBootApplication
public class Application {
    public static void main(String[] args) throws SQLException, IOException {
        System.out.println("Step by step... not a whole project, but - a piece... ");

/*
        String url = "https://polygon.io/aaaaaaa";
        Connection conn = Jsoup.connect(url).ignoreHttpErrors(true);

        int status = conn.execute().statusCode();
        if(status > 199 && status < 300){
            System.out.println(status);
            Jsoup.connect(url).response().statusCode();
            String text = Jsoup.parse(Jsoup.connect(url).get().text()).text();
            System.out.println(text);
        }else {
            System.out.println(status);
            System.out.println("вжух");
        }
*/

        MainConsole mainConsole = new MainConsole();
        mainConsole.consoleListen();
    }
}