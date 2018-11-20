package com.higaguin.uaq.Complex_folder;

/**
 * Created by higag on 06/08/2017.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

/**
 * Created by higaguin on 19/10/2016.
 */
public abstract class Complex extends AbstractBulletModelObject {
    public String model_name;
    //protected abstract String[][] models_allowed();

    public Complex(String model_name)
    {
        String model_allowed = checkModels(model_name);
        model = com.higaguin.uaq.Asset.getInstance().getModel(model_allowed);
        instance = new ModelInstance(model);
        instance.transform.rotate(1, 0, 0, -90);
        this.model_name = model_name;
        for(com.badlogic.gdx.graphics.Mesh mesh: model.meshes)
        {
            Gdx.app.log(this.getClass().getName(), model_name + ": " + Integer.toString(mesh.getNumVertices()));
        }
    }

//    public void render(Camera camera, ModelBatch modelBatch, Environment environment)
//    {
//        if (isVisible(camera, instance)) {
//            modelBatch.render(instance, environment);
//        }
//    }

    public void render(ModelBatch modelBatch, Environment environment)
    {
        modelBatch.render(instance, environment);
    }

//    private boolean isVisible(final Camera cam, final ModelInstance instance) {
//        Vector3 dimensions = new Vector3();
//        BoundingBox boundingBox = new BoundingBox();
//        instance.calculateBoundingBox(boundingBox);
//        boundingBox.getDimensions(dimensions);
//        return cam.frustum.boundsInFrustum(new Vector3(instance.transform.val[12], instance.transform.val[13], instance.transform.val[14]), dimensions);
//    }
}
