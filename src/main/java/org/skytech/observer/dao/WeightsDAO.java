package org.skytech.observer.dao;

import org.skytech.observer.dao.services.AbstractConnectionController;
import org.skytech.observer.perceptron.models.Perceptron;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WeightsDAO extends AbstractConnectionController {
    public WeightsDAO() throws SQLException {
    }

    public ArrayList<ArrayList<Double>> getEntityById(Perceptron perceptron) {
        ArrayList<ArrayList<Double>> weights;
        return null;
    }

    public ArrayList<ArrayList<Double>> getAll(Perceptron perceptron) throws SQLException {
        ArrayList<ArrayList<Double>> weightsNormalized = new ArrayList<>();
        ArrayList<Double> weightsInOneLine = new ArrayList<>();
        String sql = "SELECT * FROM '" + perceptron.getId() + "';";
        PreparedStatement state = getPreparedStatement(sql);
        ResultSet resultSet = state.executeQuery();

        while (resultSet.next()){
            weightsInOneLine.add(resultSet.getDouble("weights"));
        }
        int currentLayerNeuronCount = perceptron.getInputCount();
        int previusLayerNeuronCount = perceptron.getInputCount();
        int weightsTotalIncrement = 0;
        for(int i = 1; i <= perceptron.getHiddenLayerModificators().length + 1; i++){
            weightsNormalized.add(new ArrayList<Double>());
            if(i == 0){
                currentLayerNeuronCount = perceptron.getInputCount();
            }else if(i == perceptron.getHiddenLayerModificators().length + 1){
                currentLayerNeuronCount = perceptron.getOutputCount();
            }else{
                currentLayerNeuronCount = (int) (currentLayerNeuronCount * perceptron.getHiddenLayerModificators()[i - 1]);
            }
            for (int j = 0; j < currentLayerNeuronCount * previusLayerNeuronCount; j++){
                weightsNormalized.get(i - 1).add(weightsInOneLine.get(weightsTotalIncrement));
                weightsTotalIncrement++;
            }
            previusLayerNeuronCount = currentLayerNeuronCount;
        }
        return weightsNormalized;
    }

    public void dropTableIfExists(String tableName) throws SQLException {
        String sql = "DROP TABLE IF EXISTS '" + tableName + "';";
        PreparedStatement state = getPreparedStatement(sql);
        state.execute();
        state.close();
    }

    public void createTableIfNotExists(String tableName) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS '" + tableName + "'" + "(weights real);";
        PreparedStatement state = getPreparedStatement(sql);
        state.execute();
        state.close();
    }
    
    public boolean updateWeights(ArrayList<ArrayList<Double>> weights, String tableName) {
        boolean isCreated = false;
        // pragma и всякое такое
        String prepareSql = "PRAGMA synchronous = 0\n" +
                "PRAGMA journal_mode = OFF;";
        PreparedStatement state = getPreparedStatement(prepareSql);
        try {
            state.execute();
            for (ArrayList<Double> layer : weights) {
                for (Double weight : layer) {
                    System.out.println("Вжух");
                    state = getPreparedStatement("INSERT INTO \'" + tableName + "\' (weights) values( " + weight + ");");
                    state.execute();
                }
            }
            state = getPreparedStatement("PRAGMA synchronous = FULL;");
            state.execute();
            state = getPreparedStatement("PRAGMA journal_mode = DELETE");
            state.execute();
            state.close();
        } catch (SQLException throwables) {
            System.err.println("Ошибка во время сохранения весов");
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addWeights(Perceptron perceptron) throws SQLException {
        boolean isCreated = false;
        String name = perceptron.getId();
        Double[] hiddenLayersMod = perceptron.getHiddenLayerModificators();
        int[] neuronsCountInLayers = new int[hiddenLayersMod.length + 2];
        neuronsCountInLayers[0] = perceptron.getInputCount();
        neuronsCountInLayers[neuronsCountInLayers.length - 1] = perceptron.getOutputCount();
        for(int i = 1; i < neuronsCountInLayers.length - 1; i++) {
            neuronsCountInLayers[i] = (int) (neuronsCountInLayers[i - 1] * hiddenLayersMod[i - 1]);
        }
        long sinapsCount = 0;
        for(int i = 1; i < neuronsCountInLayers.length; i++){
            sinapsCount += neuronsCountInLayers[i] * neuronsCountInLayers[i - 1];
            System.out.println("Между слоями " + (i-1) + " и " + i + " количество синапсов: " + neuronsCountInLayers[i] * neuronsCountInLayers[i - 1]);
        }
        System.out.println("Количество синапсов к записи " + sinapsCount);
        PreparedStatement state = getPreparedStatement("PRAGMA synchronous = 0");
        state.execute();
        state = getPreparedStatement("PRAGMA journal_mode = OFF;");
        state.execute();
        state.close();
        state = getPreparedStatement("INSERT INTO \'" + name + "\' (weights) values(0.5);");
        for(int i = 0; i < sinapsCount; i++ ){ // надо просто вбить INSERT столько раз, сколько потребуется и всё.
            try {
                state.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        state.close();
        System.out.println("веса созданы");
        state = getPreparedStatement("PRAGMA synchronous = FULL;");
        state.execute();
        state = getPreparedStatement("PRAGMA journal_mode = DELETE");
        state.execute();
        state.close();
        return isCreated;
    }
}