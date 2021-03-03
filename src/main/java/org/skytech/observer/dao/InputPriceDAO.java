package org.skytech.observer.dao;

import org.skytech.observer.dao.services.AbstractConnectionController;
import org.skytech.observer.input.model.InputDataPrice;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class InputPriceDAO extends AbstractConnectionController {



    public InputPriceDAO() throws SQLException {
        String sql = ("");
    }
    public List<InputPriceDAO> getAll(String tableName) throws SQLException {
        try{
            String sql = "SELECT * FROM " + tableName + ";";
            PreparedStatement state = getPreparedStatement(sql);
            ResultSet resultSet = state.getResultSet();
            for(int i = 0; i < resultSet.getFetchSize(); i++){
            }
        }catch(SQLException e) {
            System.err.println("Ошибка при попытке достать данные InputPrice с sqlite");
            e.getStackTrace();
            connection.close();
        }
        return null;
    }

    public void addInputPriceDAO(InputDataPrice inputPrice){ //
        try {
            String tableName = "";
            String sqlAddPriceDAO = "INSERT into " +
                    tableName + "(date varchar(40), value double) " +
                    "VALUES(\'" +
                    inputPrice.getDate() + "\', " +
                    inputPrice.getValue() + ");";
            PreparedStatement state = getPreparedStatement(sqlAddPriceDAO);
            state.execute();
        } catch (SQLException throwables) {
            System.err.println("Ошибка при попытке ввести данные InputPrice");
            throwables.printStackTrace();
        }
    }
}