package com.higaguin.uaq;

import com.badlogic.gdx.graphics.g3d.Model;

/**
 * Created by higaguin on 17/12/2016.
 */
public class ModelReference {
    Model model;
    String model_name;

    public ModelReference(Model model, String model_name)
    {
        this.model = model;
        this.model_name = model_name;
    }

    public void dispose()
    {
        model.dispose();
    }
}
