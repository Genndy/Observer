package org.skytech.observer.input;

import org.joda.time.DateTime;
import org.skytech.observer.dao.InputDataNewsDAO;
import org.skytech.observer.dao.InputDataValueDAO;
import org.skytech.observer.input.api.MarketAuxReader;
import org.skytech.observer.input.api.MarketStackReader;
import org.skytech.observer.input.api.PolygonReader;
import org.skytech.observer.input.model.InputDataNews;
import org.skytech.observer.input.model.InputDataValue;
import org.skytech.observer.input.model.InputSymbol;
import sun.awt.Symbol;

import java.sql.SQLException;
import java.util.List;

public class InputDataManager {
    InputSymbol symbol;
    public void loadDataNewsForSymbol(InputSymbol symbol) {
        this.symbol = symbol;
        loadPrices();
        loadNews();
    }

    private void loadPrices(){
//        MarketStackReader marketStackReader;
        PolygonReader polygonReader;
        InputDataValueDAO inputDataValueDAO;
        int totalDayToLoadCount = getTotalDayToLoadCount();
        try {
            System.out.println("Инициализация загрузки котировки");
            polygonReader = new PolygonReader();
            inputDataValueDAO = new InputDataValueDAO();
            inputDataValueDAO.deleteTable(symbol.getName(), symbol.getVersion());
            inputDataValueDAO.createTable(symbol.getName(), symbol.getVersion());
  //          DateTime date = symbol.getDateLastSavedPrice(); // init first, or...
            List<InputDataValue> readedInputDataValues = polygonReader.getInputDataPricesListByDate(symbol.getDateBegin(), symbol.getDatePlannedToFill(), symbol.getName());
            for (InputDataValue readedInputDataValue : readedInputDataValues) {
                inputDataValueDAO.addInputPrice(readedInputDataValue);
//                date = date.plusDays(1); // Дата получения
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadNews(){
        InputDataNewsDAO inputDataNewsDAO;
        MarketAuxReader marketAuxReader;
        int totalDayToLoadCount = getTotalDayToLoadCount();
        try {
            marketAuxReader = new MarketAuxReader();
            inputDataNewsDAO = new InputDataNewsDAO(symbol.getName(), 1.0);
            DateTime date = symbol.getDateLastSavedNews(); // init first, or...
            for(int i = 0; i < totalDayToLoadCount; i++){ //
                List<InputDataNews> readedInputDataNewsList = marketAuxReader.getInputDataNewsListByDate(date, symbol.getName());
                for(int j = 0; j < readedInputDataNewsList.size(); j++){
                    inputDataNewsDAO.addInputNews(readedInputDataNewsList.get(j));
                }
                date = date.plusDays(1); // Дата получения
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Рассчёт дней
    private int getTotalDayToLoadCount(){
        long dateBegin = symbol.getDateBegin().getMillis();
        long datePlannedToFill = symbol.getDatePlannedToFill().getMillis();
        long dateCompare = datePlannedToFill - dateBegin;
        long days = dateCompare / 1000 / 60 / 60 / 24;
        return (int)days;
    }
}
