package org.skytech.observer.api.services;

import org.springframework.ui.Model;

public class ModelHolderForTestService {
    private Model model;
    public ModelHolderForTestService(){

    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
        System.out.println("setModel() executed");
    }
}
