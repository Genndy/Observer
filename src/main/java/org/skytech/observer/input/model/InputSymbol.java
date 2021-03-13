package org.skytech.observer.input.model;

import org.joda.time.DateTime;

public class InputSymbol {
    public static final int STATUS_NOT_READY = 0;
    public static final int STATUS_READY = 1;
    public static final int STATUS_DAMAGED = 2;
    public static final int STATUS_PRICES_NOT_READY = 3;
    public static final int STATUS_NEWS_NOT_READY = 4;
    public static final int STATUS_LOADING = 5;

    private String name = "";
    private double version = 0.0;
    private int newsDataCount = 0;
    private String tableNameNews = "";
    private DateTime dateBegin = null;
    private DateTime dateLastSavedNews = null;
    private int pricesDataCount = 0;
    private String tableNamePrices = "";
    private DateTime dateLastSavedPrice = null;

    private DateTime datePlannedToFill = null;
    private int status = STATUS_NOT_READY;

    private InputSymbol(){

    }
    public InputSymbol(String name, DateTime savedNewsBeginsDate, DateTime dateBegin, DateTime datePlannedToFill) {
        this.name = name;
        this.dateBegin = dateBegin;
        this.datePlannedToFill = datePlannedToFill;
    }

    public String getName() {
        return name;
    }
    public double getVersion() { return version; }
    public String getTableNameNews() {
        return tableNameNews;
    }
    public int getNewsDataCount() {
        return newsDataCount;
    }
    public DateTime getDateLastSavedNews() {
        return dateLastSavedNews;
    }
    public String getTableNamePrices() {
        return tableNamePrices;
    }
    public int getPricesDataCount() {
        return pricesDataCount;
    }
    public DateTime getDateLastSavedPrice() {
        return dateLastSavedPrice;
    }
    public DateTime getDatePlannedToFill() {
        return datePlannedToFill;
    }
    public int getStatus(){ return status; }
    public DateTime getDateBegin(){ return dateBegin; }

    public void setName(String name) { this.name = name; }
    public void setVersion(double version) { this.version = version; }
    public void setNewsDataCount(int newsDataCount) { this.newsDataCount = newsDataCount; }
    public void setTableNameNews(String tableNameNews) { this.tableNameNews = tableNameNews; }
    public void setDateLastSavedNews(DateTime dateLastSavedNews) { this.dateLastSavedNews = dateLastSavedNews; }
    public void setPricesDataCount(int pricesDataCount) { this.pricesDataCount = pricesDataCount; }
    public void setTableNamePrices(String tableNamePrices) { this.tableNamePrices = tableNamePrices; }
    public void setDateLastSavedPrice(DateTime dateLastSavedPrice) { this.dateLastSavedPrice = dateLastSavedPrice; }
    public void setDatePlannedToFill(DateTime datePlannedToFill) { this.datePlannedToFill = datePlannedToFill; }
    public void setStatus(int status) { this.status = status; }
    public void setDateBegin(DateTime dateBegin){ this.dateBegin = dateBegin; }

    public static InputSymbol.Builder builder(){
        return new InputSymbol().new Builder();
    }

    public class Builder {
        InputSymbol inputSymbol;
        private Builder(){}
        public InputSymbol build(){
            // Должна быть проверка в базе данных, на наличие
            if((InputSymbol.this.name == null) || InputSymbol.this.name.equals("") || InputSymbol.this.name.contains(" ")){
                System.err.println("Не задано имя для InputSymbolBuilder");
                throw new IllegalArgumentException();
            }
            InputSymbol.this.dateLastSavedNews = dateBegin;
            InputSymbol.this.dateLastSavedPrice = dateBegin;
            InputSymbol.this.newsDataCount = 0;
            InputSymbol.this.pricesDataCount = 0;
            InputSymbol.this.tableNameNews = name + "-news." + version;
            InputSymbol.this.tableNamePrices = name + "-price." + version;
            InputSymbol.this.status = InputSymbol.STATUS_NOT_READY;
            return InputSymbol.this;
        }
        public Builder setName(String name){ InputSymbol.this.name = name; return this; }
        public Builder setVersion(double version){ InputSymbol.this.version = version; return this; }
        public Builder setDateBeginToRead(DateTime beginsDate){ InputSymbol.this.dateBegin = beginsDate; return this; }
        public Builder setDatePlannedToFill(DateTime datePlannedToFill){
            if(datePlannedToFill != null){
                InputSymbol.this.datePlannedToFill = datePlannedToFill;
            }else {
                InputSymbol.this.datePlannedToFill = new DateTime();
            }
            return this;
        }
    }
}