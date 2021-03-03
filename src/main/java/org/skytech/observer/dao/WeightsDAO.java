package org.skytech.observer.dao;

import org.skytech.observer.dao.services.AbstractConnectionController;
import org.skytech.observer.perceptron.models.Perceptron;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class WeightsDAO extends AbstractConnectionController {
    public WeightsDAO() throws SQLException {
    }

    public ArrayList<ArrayList<Double>> getEntityById(Perceptron perceptron) {
        ArrayList<ArrayList<Double>> weights;
        return null;
    }

    public Object update(Object entity) {
        return null;
    }

    public boolean delete(Object id) {
        return false;
    }

    public boolean addWeights(Perceptron perceptron) throws SQLException {
        boolean isCreated = false;
        String name = perceptron.getId();
        Double[] hiddenLayersMod = perceptron.getHiddenLayerModificators();
        try{
            PreparedStatement statement = getPreparedStatement("CREATE TABLE IF NOT EXISTS \'" + name + "\'(weights real);");
            statement.execute();
        }catch (SQLException e){
            System.err.println("Ошибка при инициализации весов перцептронов");
            e.printStackTrace(); }
        ArrayList<ArrayList<Double>> weights = new ArrayList<ArrayList<Double>>();
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
        System.out.println("Мы прокричим ПУТИН ВОР " + sinapsCount + " раз!");
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