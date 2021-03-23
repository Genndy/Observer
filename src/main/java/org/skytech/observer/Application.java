package org.skytech.observer;

import org.skytech.observer.console.MainConsole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

// @SpringBootApplication
public class Application {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws SQLException, IOException {
        System.out.println("Let's go!");
//        MainConsole mainConsole = new MainConsole();
//        mainConsole.consoleListen();
        for(double i = -10; i <= 10; i++){
            double a = (i / 10);
            double result = (1 / (1 + Math.exp(-a)));
            System.out.println(a +": " + result);
        }
        consoleListenDouble("Введите число");
    }


    private static void consoleListenDouble(String message) throws IOException{
        System.out.println(message);
        try{
            String s = "";
            s = br.readLine();
            double a = Double.parseDouble(s);
//            double exp = 2.718;
            double result = 1 / (1 + Math.exp(-a));
            System.out.println(result);
            consoleListenDouble("Again...");
//            return Double.parseDouble(s);
        }catch (IOException e){
            e.getStackTrace();
            consoleListenDouble("Введите число");
        }catch (NumberFormatException e){
            System.err.println("Неверный формат ввода значения типа double");
            e.fillInStackTrace();
        }
        throw new IOException();
    }
}