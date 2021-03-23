package org.skytech.observer.dao;

import org.joda.time.DateTime;
import org.skytech.observer.dao.services.AbstractConnectionController;
import org.skytech.observer.input.model.InputDataValue;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InputDataValueDAO extends AbstractConnectionController {
    String tableName = "";
    String sqlArguments = "(date varchar(40), value double)";

    public InputDataValueDAO() throws SQLException {
    }

    public List<InputDataValue> getAll(String tableName) throws SQLException {
        try{
            List<InputDataValue> inputValues = new ArrayList<InputDataValue>();
            String sql = "SELECT * FROM " + tableName + ";";
            PreparedStatement state = getPreparedStatement(sql);
            ResultSet resultSet = state.getResultSet();
            while(resultSet.next()){
                DateTime date = DateTime.parse(resultSet.getString("date"));
                double value = resultSet.getDouble("value");
                inputValues.add(new InputDataValue(date, value));
            }
            return inputValues;
        }catch(SQLException e) {
            System.err.println("Ошибка при попытке достать данные InputPrice с sqlite");
            e.getStackTrace();
            connection.close();
        }
        return null;
    }

    public void addInputPrice(InputDataValue inputPrice){ //
        try {
            String sqlAddPriceDAO = "INSERT into \'" +
                    tableName + "\' (date, value) " +
                    "VALUES(\'" +
                    inputPrice.getDate() + "\', " +
                    inputPrice.getValue() + ");";
            PreparedStatement state = getPreparedStatement(sqlAddPriceDAO);
            state.execute();
            state.close();
        } catch (SQLException throwables) {
            System.err.println("Ошибка при попытке ввести данные InputPrice");
            throwables.printStackTrace();
        }
    }

    public void createTable(String symbolName, double version){
        try{
            this.tableName = symbolName + "-price." + version;
            String sql = "CREATE TABLE IF NOT EXISTS" + " \'"+ tableName + "\'"+ sqlArguments +";";
            PreparedStatement state = getPreparedStatement(sql);
            state.execute();
        }catch (SQLException e){

        }
    }

    public void deleteTable(String symbolName, double version){
        try{
            this.tableName = symbolName + "-price." + version;
            String sql = "DROP TABLE IF EXISTS \'" + tableName + "\';";
            PreparedStatement state = getPreparedStatement(sql);
            state.execute();
        }catch (SQLException e){

        }
    }
}