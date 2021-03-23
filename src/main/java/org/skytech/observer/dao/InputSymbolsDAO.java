package org.skytech.observer.dao;

import jdk.internal.util.xml.impl.Input;
import org.joda.time.DateTime;
import org.skytech.observer.dao.services.AbstractConnectionController;
import org.skytech.observer.input.model.InputSymbol;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InputSymbolsDAO extends AbstractConnectionController {
    public InputSymbolsDAO() throws SQLException {
        createTableIfNotExist();
    }

    public void deleteByNameAndVersion(String name, double version){
        String sql = "DELETE FROM inputSymbol WHERE name = '" + name + "', version = " + version + ";";
        PreparedStatement state = getPreparedStatement(sql);
        try {
            state.execute();
            state.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTableIfNotExist() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS inputSymbol(" +
                "name varchar(100), \n" +
                "version real, \n" +
                "status int, \n" +
                "newsDataCount int, \n" +
                "pricesDataCount int, \n" +
                "dateBegin varchar(16), \n" +
                "datePlannedToFill varchar(16), \n" +
                "dateLastSavedNews varchar(16), \n" +
                "dateLastSavedPrice varchar(16));";
        PreparedStatement preparedStatement = getPreparedStatement(sql);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public InputSymbol getInputSymbolById(String name, double version) throws SQLException {
        String sql = "SELECT * FROM inputSymbol WHERE name = '" + name + "', version = " + version + ";";
        PreparedStatement state = getPreparedStatement(sql);
        ResultSet resultSet = state.getResultSet();
        state.close();
        InputSymbol symbol = InputSymbol.builder()
                .setName(resultSet.getString("name"))
                .setVersion(resultSet.getDouble("version"))
                .setStatus(resultSet.getInt("status"))
                .setNewsDataCount(resultSet.getInt("newsDataCount"))
                .setPricesDataCount(resultSet.getInt("pricesDataCount"))
                .setDateBeginToRead(DateTime.parse(resultSet.getString("dateBegin")))
                .setDatePlannedToFill(DateTime.parse(resultSet.getString("datePlannedToFillDate")))
                .setDateLastSavedNews(DateTime.parse(resultSet.getString("dateEndsSavedNews")))
                .setDateLastSavedPrices(DateTime.parse(resultSet.getString("dateEndsSavedPrices"))).build();
        return symbol;
    }

    public void updateInputSymbolDAO(InputSymbol inputSymbol) throws SQLException {
        String sql = "UPDATE inputSymbol \n" +
                "status = '" + inputSymbol.getStatus() + "', \n" +
                "newsDataCount = " + inputSymbol.getStatus() + ", \n" +
                "pricesDataCount = " + inputSymbol.getStatus() + ", \n" +
                "dateBegin = '" + inputSymbol.getStatus() + "', \n" +
                "datePlannedToFillDate = '" + inputSymbol.getStatus() + "', \n" +
                "dateLastSavedNews = '" + inputSymbol.getStatus() + "', \n" +
                "dateLastSavedPrices = '" + inputSymbol.getStatus() + "' \n" +
                "WHERE name = '" + inputSymbol.getName() + "', " +
                "version = " + inputSymbol.getVersion() + ";";
        PreparedStatement preparedStatement = getPreparedStatement(sql);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public List<InputSymbol> getAll() throws SQLException {
        try{
            ArrayList<InputSymbol> symbols = new ArrayList<InputSymbol>();
            String sql = "SELECT * FROM symbols;";
            PreparedStatement state = getPreparedStatement(sql);
            ResultSet resultSet = state.getResultSet();
            while(resultSet.next()){
                InputSymbol symbol = InputSymbol.builder()
                        .setName(resultSet.getString("name"))
                        .setVersion(resultSet.getDouble("version"))
                        .setStatus(resultSet.getInt("status"))
                        .setNewsDataCount(resultSet.getInt("newsDataCount"))
                        .setPricesDataCount(resultSet.getInt("pricesDataCount"))
                        .setDateBeginToRead(DateTime.parse(resultSet.getString("dateBegin")))
                        .setDatePlannedToFill(DateTime.parse(resultSet.getString("datePlannedToFillDate")))
                        .setDateLastSavedNews(DateTime.parse(resultSet.getString("dateLastSavedNews")))
                        .setDateLastSavedPrices(DateTime.parse(resultSet.getString("dateLastSavedPrices"))).build();
                symbols.add(symbol);
            }
            return symbols;
        }catch(SQLException e) {
            System.err.println("Ошибка при попытке достать данные symbols с sqlite");
            e.getStackTrace();
            connection.close();
        }
        throw new SQLException();
    }

    public void addInputSymbolDAO(InputSymbol inputSymbol){ //
        try {
            String tableName = "";
            String sqlAddPriceDAO = ("INSERT into inputSymbol (" +
                    "        name,\n" +
                    "        version,\n" +
                    "        status,\n" +
                    "        newsDataCount,\n" +
                    "        pricesDataCount,\n" +
                    "        dateBegin,\n" +
                    "        datePlannedToFill,\n" +
                    "        dateLastSavedNews,\n" +
                    "        dateLastSavedPrice" +
                    ") VALUES('" +
                    inputSymbol.getName() + "', " +
                    inputSymbol.getVersion() + ", '" +
                    inputSymbol.getStatus() + "', '" +
                    inputSymbol.getNewsDataCount() + "', '" +
                    inputSymbol.getPricesDataCount() + "', '" +
                    inputSymbol.getDateBegin() + "', '" +
                    inputSymbol.getDatePlannedToFill() + "', '" +
                    inputSymbol.getDateLastSavedNews() + "', '" +
                    inputSymbol.getDateLastSavedPrice() + "'" +
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
