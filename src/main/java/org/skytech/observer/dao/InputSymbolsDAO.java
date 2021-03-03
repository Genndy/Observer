package org.skytech.observer.dao;

import org.skytech.observer.dao.services.AbstractConnectionController;
import org.skytech.observer.input.model.InputDataPrice;
import org.skytech.observer.input.model.InputSymbol;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InputSymbolsDAO extends AbstractConnectionController {
    public InputSymbolsDAO() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS inputSymbol(\n" +
                "        name varchar(100),\n" +
                "        newsDataCount int,\n" +
                "        tableNameNews varchar(100),\n" +
                "        savedNewsBeginsDate varchar(16),\n" +
                "        savedNewsEndsDate varchar(16),\n" +
                "        newsPlannedToFillDate varchar(16),\n" +
                "        pricesDataCount int,\n" +
                "        tableNamePrices varchar(100),\n" +
                "        savedPricesBeginsDate varchar(16),\n" +
                "        savedPricesEndsDate varchar(16),\n" +
                "        newsPricesToFillDate varchar(16),\n" +
                "        status int\n" +
                ");";
        PreparedStatement state = getPreparedStatement(sql);
        state.execute();
    }

    public List<InputSymbol> getAll() throws SQLException {
        try{
            ArrayList<InputSymbol> symbols = new ArrayList<InputSymbol>();
            String sql = "SELECT * FROM symbols;";
            PreparedStatement state = getPreparedStatement(sql);
            ResultSet resultSet = state.getResultSet();
            for(int i = 0; i < resultSet.getFetchSize(); i++){
                InputSymbol symbol = new InputSymbol.InputSymbolBuilder().setName("").build();
            }
        }catch(SQLException e) {
            System.err.println("Ошибка при попытке достать данные symbols с sqlite");
            e.getStackTrace();
            connection.close();
        }
        return null;
    }

    public void addInputPriceDAO(InputSymbol inputSymbol){ //
        try {
            String tableName = "";
            String sqlAddPriceDAO = ("INSERT into inputSymbol (,) VALUES(\'"
                    + inputSymbol.getName() + "\', "
            );

            PreparedStatement state = getPreparedStatement(sqlAddPriceDAO);
            state.execute();
        } catch (SQLException throwables) {
            System.err.println("Ошибка при попытке ввести данные inputSymbol");
            throwables.printStackTrace();
        }
    }
}
