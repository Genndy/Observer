package org.skytech.observer.input.model;

import org.joda.time.DateTime;

import java.util.Date;

public abstract class InputData {
    protected DateTime date;
    public DateTime getDate(){
        return date;
    }
}