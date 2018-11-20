package com.higaguin.uaq.Complex_folder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;

/**
 * Created by higaguin on 09/01/2017.
 */
public class Nature extends AbstractModelObject {
    public String model_name;
    public Model model;
    public ModelInstance instance;

    @Override
    public String[][] models_allowed() {
        return new String [][] {
                { com.higaguin.uaq.Constants.MODEL_TREES_TAG, com.higaguin.uaq.Constants.MODEL_TREES },
                { com.higaguin.uaq.Constants.MODEL_SKYBOX_TAG, com.higaguin.uaq.Constants.MODEL_SKYBOX }
        };
    }

    public Nature(String model_name)
    {
        String model_allowed = checkModels(model_name);
        model = com.higaguin.uaq.Asset.getInstance().getModel(model_allowed);
        for(Material material : model.materials) {
            material.set(FloatAttribute.createAlphaTest(0.90f));
        }
        instance = new ModelInstance(model);
        instance.transform.rotate(1, 0, 0, -90);
        this.model_name = model_name;
        for(com.badlogic.gdx.graphics.Mesh mesh: model.meshes)
        {
            Gdx.app.log(this.getClass().getName(), model_name + ": " + Integer.toString(mesh.getNumVertices()));
        }
    }

    public void render(ModelBatch modelBatch, Environment environment)
    {
        modelBatch.render(instance, environment);
    }

//    public void dispose()
//    {
//        model.dispose();
//    }
}
