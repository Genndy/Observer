package org.skytech.observer.perceptron;

import org.skytech.observer.dao.WeightsDAO;
import java.util.ArrayList;

public class PerceptronAlgorythm {
    ArrayList<Double> inputSignals;

    ArrayList<ArrayList<Double>> weights;
    WeightsDAO weightsDAO;

    private void stepTrain(ArrayList<ArrayList<Double>> weights, ArrayList<Double> inputs, Double[] modifier, int outputCount){
        double activationFunction = 0.5;
        ArrayList<ArrayList<Double>> allSignals = new ArrayList<>();
        int allSinapsCount = 0;
        for (ArrayList<Double> asd  : weights){ for (Double d: asd){ allSinapsCount++; } }
        allSignals.add(inputs);
        int signalsOutCount = 0;
        for(int i = 0; i < weights.size(); i++){
            if(i == weights.size() - 1) {
                signalsOutCount = outputCount;
            } else {
                signalsOutCount = (int)((weights.get(i).size() / signalsOutCount));
            }
            double summ = 0.0;
            for(int j = 0; j < signalsOutCount; j++) {
                ArrayList<Double> newInputSignals = new ArrayList<>();
                for (Double signalIn : allSignals.get(i)) {
                    for (Double weight : weights.get(i)) {
                        summ = +signalIn * weight;
                    }
                    newInputSignals.add(summ / allSignals.get(i).size());
                }
                allSignals.add(newInputSignals);
            }
        }
    }

    private void stepExecute(ArrayList<ArrayList<Double>> weights, ArrayList<Double> inputs){
        double activationFunction = 0.5;

        ArrayList<ArrayList<Double>> allSignals = new ArrayList<>();
        allSignals.add(inputs);
        for(int i = 0; i < weights.size(); i++){
            ArrayList<Double> newInputSignals = new ArrayList<>();
            double summ = 0.0;
            for(Double signalIn : allSignals.get(i)){
                for(Double weight : weights.get(i - 1)){
                    summ =+ signalIn * weight;
                }
            newInputSignals.add(summ / allSignals.get(i).size());
            }
            allSignals.add(newInputSignals);
        }
    }

}

