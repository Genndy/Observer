package org.skytech.observer.input.model;

import org.joda.time.DateTime;

import java.util.Date;

public class InputDataValue extends InputData {
    double value;
    public InputDataValue(DateTime date, double value){
        super.date = date;
        this.value = value;
    }
    public double getValue(){
        return value;
    }
}