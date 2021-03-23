package org.skytech.observer.dao;

import org.joda.time.DateTime;
import org.skytech.observer.dao.services.AbstractConnectionController;
import org.skytech.observer.input.model.InputDataNews;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InputDataNewsDAO extends AbstractConnectionController {
    private String tableName;
    private double version;
    private String sqlArguments = "(text TEXT, " +
            "date varchar(20), " +
            "description text, " +
            "source varchar(100), " +
            "url varchar(200), " +
            "head_title text, " +
            "lang varchar(6), " +
            "symbol varchar(8))";

    public InputDataNewsDAO(String symbolName, double version) throws SQLException {
        this.tableName = symbolName + "-news." + version;
        this.version = version;
    }

    public void createTableIfNotExists() throws SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS " + "\'"+ tableName + "\'" + sqlArguments + ";";
        PreparedStatement statement = getPreparedStatement(sql);
        statement.execute();
    }

    public void dropTableIfExists() throws SQLException {
        String sql = "DROP TABLE IF EXISTS " + "\'" + tableName + "\';";
        PreparedStatement statement = getPreparedStatement(sql);
        statement.execute();
    }

    public List<InputDataNews> getAll() throws SQLException {
        try{
            List<InputDataNews> inputDataNews = new ArrayList<InputDataNews>();
            String sql = "SELECT * FROM symbols;";
            PreparedStatement state = getPreparedStatement(sql);
            ResultSet resultSet = state.executeQuery();
            while(resultSet.next()){
                String text = resultSet.getString("text");
                DateTime date = DateTime.parse(resultSet.getString("date"));
                String description = resultSet.getString("description");
                String source = resultSet.getString("source");
                String url = resultSet.getString("url");
                String headTitle = resultSet.getString("head_title");
                String lang = resultSet.getString("lang");
                String symbol = resultSet.getString("symbol");
                InputDataNews inputDataNew = new InputDataNews(symbol, description, date, source, url, headTitle, text);
                inputDataNews.add(inputDataNew);
            }
            return inputDataNews;
        }catch(SQLException e) {
            System.err.println("Ошибка при попытке достать данные symbols с sqlite");
            e.getStackTrace();
            connection.close();
        }
        return null;
    }

    public void addInputNews(InputDataNews inputData){ //
        try {
            String textEcraned =inputData.getText().replaceAll("\\'", "\'\'");
//            textEcraned = textEcraned.replaceAll("\"", "\\\\\"");

            String sqlAddPriceDAO = "INSERT into \'" + tableName + "\'" +
                    "             (text,\n" +
                    "             date, \n" +
                    "             description,\n" +
                    "             source,\n" +
                    "             url,\n" +
                    "             head_titul,\n" +
                    "             lang,\n" +
                    "             symbol) " +
                    "VALUES(" +
                    "\'" + textEcraned + "\', " +
                    "\'" + inputData.getDate().toString() + "\', " +
                    "\'" + textEcraned + "\', " +
                    "\'" + inputData.getSource() + "\', " +
                    "\'" + inputData.getURL() + "\', " +
                    "\'" + inputData.getHeadTitul() + "\', " +
                    "\'"  + "en" + "\', " +
                    "\'" + inputData.getSymbol() + "\' " +
                     ");";
            PreparedStatement state = getPreparedStatement(sqlAddPriceDAO);
            state.execute();
            state.close();
        } catch (SQLException throwables) {
            System.err.println("Ошибка при попытке ввести данные InputPrice");
            throwables.printStackTrace();
        }
    }
}