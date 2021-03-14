package org.skytech.observer.dao;

import org.skytech.observer.dao.services.AbstractConnectionController;
import org.skytech.observer.input.model.InputSymbol;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InputSymbolsDAO extends AbstractConnectionController {
    public InputSymbolsDAO() throws SQLException {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS inputSymbol(\n" +
                "        name varchar(100),\n" +
                "        status int,\n" +
                "        newsDataCount int,\n" +
                "        pricesDataCount int,\n" +
                "        dateBegin varchar(50),\n" +
                "        datePlannedToFillDate varchar(50),\n" +
                "        dateEndsSavedNews varchar(50),\n" +
                "        dateEndsPlannedToFillDate varchar(50),\n " +
                " UNIQUE(name));";
        PreparedStatement state = getPreparedStatement(sqlCreate);
        state.execute();
    }

    public List<InputSymbol> getAll() throws SQLException {
        try{
            ArrayList<InputSymbol> symbols = new ArrayList<InputSymbol>();
            String sql = "SELECT * FROM symbols;";
            PreparedStatement state = getPreparedStatement(sql);
            ResultSet resultSet = state.getResultSet();
            for(int i = 0; i < resultSet.getFetchSize(); i++){
//                InputSymbol symbol = new InputSymbol.builder().setName("").build();
            }
        }catch(SQLException e) {
            System.err.println("Ошибка при попытке достать данные symbols с sqlite");
            e.getStackTrace();
            connection.close();
        }
        return null;
    }

    public void addInputSymbolDAO(InputSymbol inputSymbol){ //
        try {
            String tableName = "";
            String sqlAddPriceDAO = ("INSERT into inputSymbol (" +
                    "        name,\n" +
                    "        status,\n" +
                    "        newsDataCount,\n" +
                    "        pricesDataCount,\n" +
                    "        dateBegin,\n" +
                    "        datePlannedToFillDate,\n" +
                    "        dateEndsSavedNews,\n" +
                    "        dateEndsPlannedToFillDate" +
                    ") VALUES(\'" +
                    inputSymbol.getName() + "." + inputSymbol.getVersion() + "\', " +
                    inputSymbol.getStatus() + ", " +
                    inputSymbol.getNewsDataCount() + ", " +
                    inputSymbol.getPricesDataCount() + ", \'" +
                    inputSymbol.getDateBegin() + "\', \'" +
                    inputSymbol.getDatePlannedToFill() + "\', \'" +
                    inputSymbol.getDateLastSavedNews() + "\', \'" +
                    inputSymbol.getDateLastSavedPrice() + "\'" +
                    "); "
            );
            PreparedStatement state = getPreparedStatement(sqlAddPriceDAO);
            state.execute();
        } catch (SQLException throwables) {
            System.err.println("Ошибка при попытке ввести данные inputSymbol");
            throwables.printStackTrace();
        }
    }
}
