package org.skytech.observer.dao;

import org.skytech.observer.dao.services.AbstractConnectionController;
import org.skytech.observer.perceptron.models.Perceptron;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PerceptronDAO extends AbstractConnectionController {
    public PerceptronDAO() throws SQLException {
    }

    public List<Perceptron> getAll() throws SQLException {
        String sql = "SELECT * FROM perceptrons";
        PreparedStatement state = getPreparedStatement(sql);
        state.getResultSet();
        return null;
    }
    public void addPerceptron(Perceptron perc){
        String sqlCreatePerceptronTemplate = "INSERT into perceptrons(id, name, input_count, output_count, " +
                "specialization, created_date, generation, error_degree, perceptron_type, status) VALUES (";
        String sql = sqlCreatePerceptronTemplate + "\'" +
                perc.getId() +"\', \'"+
                perc.getName() +"\', "+
                perc.getInputCount() +", "+
                perc.getOutputCount() +", \'"+
                perc.getSpecialization() +"\',\'"+
                perc.getCreatedDate().toString() +"\',"+
                perc.getGeneration() +", "+
                perc.getError_degree() +",\'"+
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
        String sql = "DELETE FROM perceptrons WHERE id id = " + id;
        PreparedStatement state = getPreparedStatement(sql);
        try {
            state.execute();
            state.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}