package org.skytech.observer.input.model;


import java.util.Date;

public class InputDataPrice extends InputData {
    double value;
    public InputDataPrice(Date date, double value){
        super.date = date;
        this.value = value;
    }
    public double getValue(){ return value; }
}