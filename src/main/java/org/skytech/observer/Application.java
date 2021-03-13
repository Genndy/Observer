package org.skytech.observer;

import org.joda.time.DateTime;
import org.skytech.observer.console.MainConsole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

// @SpringBootApplication
public class Application {
    public static void main(String[] args) throws SQLException {
        System.out.println("Step by step... not a whole project, but - a piece... ");
        MainConsole mainConsole = new MainConsole();
        mainConsole.consoleListen();
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
}