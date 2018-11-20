package com.higaguin.uaq.Complex_folder;

/**
 * Created by higaguin on 12/02/2017.
 */

public abstract class AbstractModelObject implements InterfaceModelObject {

    public String checkModels(String model_name)
    {
        for(String[] model : models_allowed()) {
            if(model[0].equals(model_name)) {
                return model[1];
            }
        }
        return null;
    }
}
