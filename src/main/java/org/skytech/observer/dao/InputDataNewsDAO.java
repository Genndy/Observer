package org.skytech.observer.dao;

import org.skytech.observer.dao.services.AbstractConnectionController;
import org.skytech.observer.input.model.InputDataNews;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class InputDataNewsDAO extends AbstractConnectionController {
    private String tableName;
    private double version;
    private String sqlArguments = "(text text, " +
            "description text, " +
            "source varchar(100), " +
            "url varchar(200), " +
            "head_titul text, " +
            "lang varchar(6), " +
            "symbol varchar(8))";

    public InputDataNewsDAO(String tableName, double version) throws SQLException {
        this.tableName = tableName;
        this.version = version;
        String sql = "CREATE TABLE IF NOT EXIST" + "\'"+ tableName + "-news." + version + "\'" + sqlArguments + ";";
    }

    public List<InputDataValueDAO> getAll() throws SQLException {
        try{
            String sql = "SELECT * FROM symbols;";
            PreparedStatement state = getPreparedStatement(sql);
            ResultSet resultSet = state.getResultSet();
            for(int i = 0; i < resultSet.getFetchSize(); i++){
            }
        }catch(SQLException e) {
            System.err.println("Ошибка при попытке достать данные symbols с sqlite");
            e.getStackTrace();
            connection.close();
        }
        return null;
    }

    public void addInputNews(InputDataNews inputData){ //
        try {
            String sqlAddPriceDAO = "INSERT into " +
                    tableName + "_" + version +
                    "             (text,\n" +
                    "             description,\n" +
                    "             source,\n" +
                    "             url,\n" +
                    "             head_titul,\n" +
                    "             lang,\n" +
                    "             symbol) " +
                    "VALUES(" +
                    "\'" + parseArrToString(inputData.getText()) + "\', " +
                    "\'" + inputData.getDescription() + "\', " +
                    "\'" + inputData.getSource() + "\', " +
                    "\'" + inputData.getURL() + "\', " +
                    "\'" + inputData.getHeadTitul() + "\', " +
                    "\'" + inputData.getLang() + "\', " +
                    "\'" + tableName + "\' " +
                     ");";
            PreparedStatement state = getPreparedStatement(sqlAddPriceDAO);
            state.execute();
        } catch (SQLException throwables) {
            System.err.println("Ошибка при попытке ввести данные InputPrice");
            throwables.printStackTrace();
        }
    }

    private String parseArrToString(String[] arr){
        String str = "";
        for(int i = 0; i < arr.length; i++){
            str = str + arr[i] + " ";
        }
        return str;
    }
}