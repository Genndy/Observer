package org.skytech.observer.perceptron.models;
import org.joda.time.DateTime;

public class Perceptron {
    private String id;
    private String name;
    private int inputCount;
    private int outputCount;
    private Double[] hiddenLayerModificators;// количество слоёв определяется длиной этого массива
    private String specialization;
    private DateTime createdDate;
    private int generation;
    private double errorDegree;
    private String perceptronType;
    private String status;
    public static Object Builder;

    @Override
    public String toString(){
        String str ="id:" + id + "\n" +
                "name: " + name + "\n" +
                "inputCount: " + inputCount + "\n" +
                "outputCount: " + outputCount + "\n" +
                "hiddenLayerModificators: " + hiddenLayerModificators + "\n" +
                "specialization: " + specialization + "\n" +
                "createdDate: " + createdDate + "\n" +
                "generation: " + generation + "\n" +
                "errorDegree: " + errorDegree + "\n" +
                "perceptronType: " + perceptronType + "\n" +
                "status: " + status + "\n";
        return str;
    }

    public Perceptron(String id, String name, int inputCount, int outputCount, Double[] hiddenLayerModificators, String specialization, DateTime createdDate, int generation, double errorDegree, String perceptronType, String status) {
        this.id = id;
        this.name = name;
        this.inputCount = inputCount;
        this.outputCount = outputCount;
        this.hiddenLayerModificators = hiddenLayerModificators;
        this.specialization = specialization;
        this.createdDate = createdDate;
        this.generation = generation;
        this.errorDegree = errorDegree;
        this.perceptronType = perceptronType;
        this.status = status;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getInputCount() {
        return inputCount;
    }
    public void setInputCount(int inputCount) {
        this.inputCount = inputCount;
    }
    public int getOutputCount() {
        return outputCount;
    }
    public void setOutputCount(int outputCount) {
        this.outputCount = outputCount;
    }
    public Double[] getHiddenLayerModificators() {
        return hiddenLayerModificators;
    }
    public void setHiddenLayerModificators(Double[] hiddenLayerModificators) {
        this.hiddenLayerModificators = hiddenLayerModificators;
    }

    public String getSpecialization() {
        return specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    public DateTime getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(DateTime createdDate) {
        this.createdDate = createdDate;
    }
    public int getGeneration() {
        return generation;
    }
    public void setGeneration(int generation) {
        this.generation = generation;
    }
    public double getErrorDegree() {
        return errorDegree;
    }
    public void setErrorDegree(double errorDegree) {
        this.errorDegree = errorDegree;
    }
    public String getPerceptronType() {
        return perceptronType;
    }
    public void setPerceptronType(String perceptronType) {
        this.perceptronType = perceptronType;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public static Builder builder(){
        return new Perceptron().new Builder();
    }
    private Perceptron() { }

    public class Builder {
        private Builder() {}
        public Builder setId(String id) { Perceptron.this.id = id; return this; }
        public Builder setName(String name) { Perceptron.this.name = name; return this; }
        public Builder setInputCount(int inputCount) { Perceptron.this.inputCount = inputCount; return this; }
        public Builder setOutputCount(int outputCount) { Perceptron.this.outputCount = outputCount; return this; }
        public Builder setHiddenLayerModificators(Double[] hiddenLayerModificators) { Perceptron.this.hiddenLayerModificators = hiddenLayerModificators; return this; }
        public Builder setSpecialization(String specialization) { Perceptron.this.specialization = specialization; return this; }
        public Builder setCreatedDate(DateTime createdDate) { Perceptron.this.createdDate = createdDate; return this; }
        public Builder setGeneration(int generation) { Perceptron.this.generation = generation; return this; }
        public Builder setErrorDegree(double error_degree) { Perceptron.this.errorDegree = error_degree; return this; }
        public Builder setPerceptronType(String perceptronType) { Perceptron.this.perceptronType = perceptronType; return this; }
        public Builder setStatus(String status) { Perceptron.this.status = status; return this; }
        public Perceptron build(){
            return Perceptron.this;
        }
    }
}