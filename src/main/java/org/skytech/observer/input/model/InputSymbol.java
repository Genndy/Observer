package org.skytech.observer.input.model;

import java.util.Date;

public class InputSymbol {
    public static final int STATUS_NOT_READY = 0;
    public static final int STATUS_READY = 1;
    public static final int STATUS_DAMAGED = 2;
    public static final int STATUS_PRICES_NOT_READY = 3;
    public static final int STATUS_NEWS_NOT_READY = 4;
    public static final int STATUS_LOADING = 5;

    String name = "";
    int newsDataCount = 0;
    String tableNameNews = "";
    Date savedNewsBeginsDate;
    Date savedNewsEndsDate;
    Date newsPlannedToFillDate;
    int pricesDataCount = 0;
    String tableNamePrices = "";
    Date savedPricesBeginsDate;
    Date savedPricesEndsDate;
    Date newsPricesToFillDate;
    int status = STATUS_NOT_READY;

    private InputSymbol(){

    }
    public InputSymbol(String name, Date savedNewsBeginsDate, Date newsPlannedToFillDate, Date savedPricesBeginsDate, Date newsPricesToFillDate) {
        this.name = name;
        this.savedNewsBeginsDate = savedNewsBeginsDate;
        this.newsPlannedToFillDate = newsPlannedToFillDate;
        this.savedPricesBeginsDate = savedPricesBeginsDate;
        this.newsPricesToFillDate = newsPricesToFillDate;
    }

    public String getName() {
        return name;
    }
    public String getTableNameNews() {
        return tableNameNews;
    }
    public int getNewsDataCount() {
        return newsDataCount;
    }
    public Date getSavedNewsBeginsDate() {
        return savedNewsBeginsDate;
    }
    public Date getSavedNewsEndsDate() {
        return savedNewsEndsDate;
    }
    public Date getNewsPlannedToFillDate() {
        return newsPlannedToFillDate;
    }
    public String getTableNamePrices() {
        return tableNamePrices;
    }
    public int getPricesDataCount() {
        return pricesDataCount;
    }
    public Date getSavedPricesBeginsDate() {
        return savedPricesBeginsDate;
    }
    public Date getSavedPricesEndsDate() {
        return savedPricesEndsDate;
    }
    public Date getNewsPricesToFillDate() {
        return newsPricesToFillDate;
    }

    public void setName(String name) { this.name = name; } //
    public void setNewsDataCount(int newsDataCount) { this.newsDataCount = newsDataCount; } //
    public void setTableNameNews(String tableNameNews) { this.tableNameNews = tableNameNews; } //
    public void setSavedNewsBeginsDate(Date savedNewsBeginsDate) { this.savedNewsBeginsDate = savedNewsBeginsDate; } //
    public void setSavedNewsEndsDate(Date savedNewsEndsDate) { this.savedNewsEndsDate = savedNewsEndsDate; }
    public void setNewsPlannedToFillDate(Date newsPlannedToFillDate) { this.newsPlannedToFillDate = newsPlannedToFillDate; }
    public void setPricesDataCount(int pricesDataCount) { this.pricesDataCount = pricesDataCount; }
    public void setTableNamePrices(String tableNamePrices) { this.tableNamePrices = tableNamePrices; }
    public void setSavedPricesBeginsDate(Date savedPricesBeginsDate) { this.savedPricesBeginsDate = savedPricesBeginsDate; }
    public void setSavedPricesEndsDate(Date savedPricesEndsDate) { this.savedPricesEndsDate = savedPricesEndsDate; }
    public void setNewsPricesToFillDate(Date newsPricesToFillDate) { this.newsPricesToFillDate = newsPricesToFillDate; }
    public void setStatus(int status) { this.status = status; }

    public static class InputSymbolBuilder{
        InputSymbol inputSymbol;
        public InputSymbol build(){
            if((inputSymbol.getName() == null) || inputSymbol.getName().equals("") || inputSymbol.getName().contains(" ")){
                System.err.println("Не задано имя для InputSymbolBuilder");
                throw new IllegalArgumentException();
            }
            inputSymbol.setSavedNewsEndsDate(null);
            inputSymbol.setSavedPricesEndsDate(null);
            inputSymbol.setNewsDataCount(0);
            inputSymbol.setPricesDataCount(0);
            inputSymbol.setTableNamePrices("");
            inputSymbol.setTableNameNews("");
            inputSymbol.setStatus(InputSymbol.STATUS_NOT_READY);
            return inputSymbol;
        }
        public InputSymbolBuilder(){ inputSymbol = new InputSymbol(); }
        public InputSymbolBuilder setName(String name){ inputSymbol.setName(name); return this; }
        public InputSymbolBuilder setTableNameNews(String tableNameNews){ inputSymbol.setTableNameNews(tableNameNews); return this; }
        public InputSymbolBuilder setSavedNewsBeginsDate(Date savedNewsBeginsDate){ inputSymbol.setSavedNewsBeginsDate(savedNewsBeginsDate); return this; }
        public InputSymbolBuilder setNewsPlannedToFillDate(Date newsPlannedToFillDate){ inputSymbol.setNewsPlannedToFillDate(newsPlannedToFillDate); return this; }
        public InputSymbolBuilder setSavedPricesBeginsDate(Date savedPricesBeginsDate){ inputSymbol.setSavedPricesBeginsDate(savedPricesBeginsDate); return this; }
        public InputSymbolBuilder setNewsPricesToFillDate(Date newsPricesToFillDate){ inputSymbol.setNewsPricesToFillDate(newsPricesToFillDate); return this; }
    }
}