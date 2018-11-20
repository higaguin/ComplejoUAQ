package com.higaguin.uaq;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by higaguin on 16/12/2016.
 */
public class Reference {
    Vector3 position;
    float degrees;
    ModelInstance instance;
    String model_name;

    public Reference(Vector3 position, float degrees, ModelInstance instance, String model_name)
    {
        this.position = position;
        this.degrees = degrees;
        this.instance = instance;
        this.model_name = model_name;
    }
}
