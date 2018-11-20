package com.higaguin.uaq.Complex_folder;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.higaguin.uaq.Asset;
import com.higaguin.uaq.Constants;

/**
 * Created by higaguin on 04/02/2017.
 */

public class Constraint  extends com.higaguin.uaq.Bullet.BulletObject {

    private static final Constraint ourInstance = new Constraint();

    public static Constraint getInstance() {
        return ourInstance;
    }

    private Constraint(){}

    public void init()
    {
        model = Asset.getInstance().getModel(Constants.MODEL_CONSTRAINT);
        instance = new ModelInstance(model);
        instance.transform.rotate(1, 0, 0, -90);
    }

    public void render(ModelBatch modelBatch, Environment environment)
    {
        modelBatch.render(instance, environment);
    }
}
