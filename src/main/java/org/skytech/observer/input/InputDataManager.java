package org.skytech.observer.input;

import org.joda.time.DateTime;
import org.skytech.observer.dao.InputDataNewsDAO;
import org.skytech.observer.dao.InputDataValueDAO;
import org.skytech.observer.input.api.MarketAuxNewsReader;
import org.skytech.observer.input.api.PolygonNewsReader;
import org.skytech.observer.input.api.PolygonPriceReader;
import org.skytech.observer.input.model.InputDataNews;
import org.skytech.observer.input.model.InputDataValue;
import org.skytech.observer.input.model.InputSymbol;

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
        PolygonPriceReader polygonPriceReader;
        InputDataValueDAO inputDataValueDAO;
        int totalDayToLoadCount = getTotalDayToLoadCount();
        try {
            System.out.println("Инициализация загрузки котировки");
            polygonPriceReader = new PolygonPriceReader();
            inputDataValueDAO = new InputDataValueDAO();
            inputDataValueDAO.deleteTable(symbol.getName(), symbol.getVersion());
            inputDataValueDAO.createTable(symbol.getName(), symbol.getVersion());
  //          DateTime date = symbol.getDateLastSavedPrice(); // init first, or...
            List<InputDataValue> readedInputDataValues = polygonPriceReader.getInputDataPricesListByDate(symbol.getDateBegin(), symbol.getDatePlannedToFill(), symbol.getName());
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
        PolygonNewsReader polygonNewsReader;
        int totalDayToLoadCount = getTotalDayToLoadCount();
        try {
            polygonNewsReader = new PolygonNewsReader();
            inputDataNewsDAO = new InputDataNewsDAO(symbol.getName(), symbol.getVersion());
            inputDataNewsDAO.dropTableIfExists();
            inputDataNewsDAO.createTableIfNotExists();
            DateTime date = symbol.getDateLastSavedNews(); // init first, or...
            for(int i = 0; i < 1; i++){ //
                List<InputDataNews> readedInputDataNewsList = polygonNewsReader.getInputDataNewsListByDate(i, symbol.getName());
                for(int j = 0; j < readedInputDataNewsList.size(); j++){
                    inputDataNewsDAO.addInputNews(readedInputDataNewsList.get(j));
                }
                date = date.plusDays(1); // Дата получения
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private int getTotalDayToLoadCount(){
        long dateBegin = symbol.getDateBegin().getMillis();
        long datePlannedToFill = symbol.getDatePlannedToFill().getMillis();
        long dateCompare = datePlannedToFill - dateBegin;
        long days = dateCompare / 1000 / 60 / 60 / 24;
        return (int)days;
    }
}
