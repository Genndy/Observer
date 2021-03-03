package org.skytech.observer;

import org.json.JSONException;
import org.skytech.observer.console.PerceptronsManager;
import org.skytech.observer.input.MarketAuxReader;
import org.skytech.observer.input.MarketStackReader;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

// @SpringBootApplication
public class Application {
    public static void main(String[] args) throws SQLException {
     //   SpringApplication.run(Application.class, args);
        System.out.println("I will try and try...");
//        MarketAuxReader marketAuxReader = new MarketAuxReader();
        MarketStackReader marketStackReader = new MarketStackReader();
        try {
            marketStackReader.init();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        // MarketAuxReader
//        PerceptronsManager perceptronsManager = new PerceptronsManager();
//        perceptronsManager.consoleListen();
    }
}