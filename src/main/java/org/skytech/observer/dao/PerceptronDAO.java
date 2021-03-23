package org.skytech.observer.dao;

import org.joda.time.DateTime;
import org.skytech.observer.dao.services.AbstractConnectionController;
import org.skytech.observer.perceptron.models.Perceptron;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PerceptronDAO extends AbstractConnectionController {
    public PerceptronDAO() throws SQLException {
        createTableIfNotExist();
    }

    public void createTableIfNotExist() throws SQLException {
        String arguments = "id varchar(38), \n" +
                " name varchar(100), " +
                "input_count int, " +
                "output_count int, " +
                "hidden_layer_modifier text, " +
                "specialization varchar(100), " +
                "created_date varchar(50), " +
                "generation int, " +
                "error_degree real, " +
                "perceptron_type varchar(100), " +
                "status varchar(100)";
        String sql = "CREATE TABLE IF NOT EXISTS 'perceptrons'(" + arguments + ");";
        PreparedStatement state = getPreparedStatement(sql);
        state.execute();
        state.close();
    }

    public void deleteTableIfExist() throws SQLException {
        String sql = "DROP TABLE IF EXISTS 'perceptron'";
        PreparedStatement state = getPreparedStatement(sql);
        state.execute();
        state.close();
    }

    public void updatePercetpron(Perceptron perceptron) throws SQLException {
        String sql = "UPDATE 'perceptron' SET " +
                "name = " + perceptron.getName() + ",\n" +
                "input_count = " + perceptron.getInputCount() + ",\n" +
                "input_output = " + perceptron.getOutputCount() + ",\n" +
                "specialization = " + perceptron.getSpecialization() + ",\n" +
                "created_date = " + perceptron.getCreatedDate() + ",\n" +
                "generation = " + perceptron.getGeneration() + ",\n" +
                "error_degree = " + perceptron.getErrorDegree() + ",\n" +
                "perceptron_type = " + perceptron.getPerceptronType() + ",\n" +
                "status = " + perceptron.getPerceptronType() + "\n" +
                "WHERE id = " + perceptron.getId();
        PreparedStatement state = getPreparedStatement(sql);
        state.execute();
        state.close();
    }

    public List<Perceptron> getAll() throws SQLException {
        String sql = "SELECT * FROM 'perceptrons'";
        PreparedStatement state = getPreparedStatement(sql);
        List<Perceptron> perceptrons = new ArrayList<Perceptron>();
        ResultSet res = state.executeQuery();
//        ResultSet res = state.getResultSet();
        while(res.next()){
            String[] strings = res.getString(5).replace(",", "")
                    .replace("[","").replace("]","").split(" ");
            Double[] hiddenLayerModifier = new Double[strings.length];
            for(int i = 0; i < strings.length; i++){
                hiddenLayerModifier[i] = Double.valueOf(strings[i]);
            }

            Perceptron perceptron = Perceptron.builder()
            .setId(res.getString(1))
            .setName(res.getString(2))
            .setInputCount(res.getInt(3))
            .setOutputCount(res.getInt(4))
            .setHiddenLayerModificators(hiddenLayerModifier)
            .setSpecialization(res.getString(6))
            .setCreatedDate(DateTime.parse(res.getString(7)))
            .setGeneration(res.getInt(8))
            .setErrorDegree(res.getDouble(9))
            .setPerceptronType(res.getString(10))
            .setStatus(res.getString(11)).build();
            perceptrons.add(perceptron);
        }
        state.close();
        return perceptrons;
    }

    public void addPerceptron(Perceptron perc){
        String sqlCreatePerceptronTemplate = "INSERT into 'perceptrons'(id, name, input_count, output_count, " +
                "hidden_layer_modifier, specialization, created_date, generation, error_degree, perceptron_type, status) VALUES (";
        String sql = sqlCreatePerceptronTemplate + "\'" +
                perc.getId() +"\', \'"+
                perc.getName() +"\', "+
                perc.getInputCount() +", "+
                perc.getOutputCount() +", \'"+
                Arrays.toString(perc.getHiddenLayerModificators()) +"', \'"+
                perc.getSpecialization() +"\',\'"+
                perc.getCreatedDate() +"\',"+
                perc.getGeneration() +", "+
                perc.getErrorDegree() +",\'"+
                perc.getPerceptronType() +"\', \'"+
                perc.getStatus() + "\')";
        PreparedStatement state = getPreparedStatement(sql);
        try {
            state.execute();
            state.close();
            System.out.println("Успешная запись перцептрона " + perc.getName());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deletePerceptronById(String id){
        String sql = "DELETE FROM 'perceptrons' WHERE id = " + id;
        PreparedStatement state = getPreparedStatement(sql);
        try {
            state.execute();
            state.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Perceptron getPerceptronById(String id) throws SQLException {
        String sql = "SELECT * FROM 'perceptrons' WHERE id = " + id;
        PreparedStatement state = getPreparedStatement(sql);
        ResultSet resultSet = state.getResultSet();
        String[] strings = resultSet.getString(5).split(" ");
        Double[] hiddenLayerModifier = new Double[strings.length];
        for(int i = 0; i < strings.length; i++){
            hiddenLayerModifier[i] = Double.valueOf(strings[i]);
        }

        Perceptron perceptron = Perceptron.builder()
                .setName(id)
                .setName(resultSet.getString(2))
                .setInputCount(resultSet.getInt(3))
                .setOutputCount(resultSet.getInt(4))
                .setHiddenLayerModificators(hiddenLayerModifier)
                .setSpecialization(resultSet.getString(6))
                .setCreatedDate(DateTime.parse(resultSet.getString(7)))
                .setGeneration(resultSet.getInt(8))
                .setErrorDegree(resultSet.getDouble(9))
                .setPerceptronType(resultSet.getString(10))
                .setStatus(resultSet.getString(11)).build();
        return perceptron;
    }
}