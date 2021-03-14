package org.skytech.observer.input.model;


import org.joda.time.DateTime;

import java.util.Date;

public class InputDataNews extends InputData {
    private String text;
    private String description;
    private String source;
    private String url;
    private String headTitul;
    private String symbol;

    public InputDataNews(String symbol, String description, DateTime date, String source, String type, String text){
        this.symbol = symbol;
        super.date = date;
        this.description = description;
        this.source = source;
        this.text = text;
    }
    public String getSource(){
        return source;
    }
    public String getURL(){
        return source;
    }
    public String getHeadTitul(){
        return headTitul;
    }
    public String text() { return text; }
    public String getText() {
        return text;
    }
    public String getDescription() { return description; }
    public String getSymbol() { return symbol; }
}