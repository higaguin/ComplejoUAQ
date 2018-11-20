package com.higaguin.uaq;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.IOException;

/**
 * Created by higaguin on 01/12/2016.
 */
public class Builder implements Disposable {
    private static Builder ourInstance = new Builder();
    public Array<com.higaguin.uaq.Reference> references;
    //    public Array<Model> types;
    public Array<ModelReference> model_references;
    //public BoundingBox reference;
    public ModelReference reference;
    public ModelInstance loadPoint;
    public Vector3 lodPoint_dimensions;
    private int index;
    private float degrees;
    private float degrees_load_point;
    Array<String> model_names;

    public static Builder getInstance() {
        return ourInstance;
    }

    private Builder(){}

    public void init()
    {
        index = 0;
        degrees_load_point = 0;
        lodPoint_dimensions = new Vector3(15, 15, 15);
        //reference = new ModelInstance();
        references = new Array<com.higaguin.uaq.Reference>();
        model_names = new Array<String>();
//        bounds = new Array<ModelInstance>();
//        types = new Array<Model>();
//        types.add(Asset.getInstance().getModel(Constants.MODEL_BUILDING1_1));
//        types.add(Asset.getInstance().getModel(Constants.MODEL_TABLE9));
//        types.add(Asset.getInstance().getModel(Constants.MODEL_TABLE2));
//        types.add(Asset.getInstance().getModel(Constants.MODEL_CPU));
//        types.add(Asset.getInstance().getModel(Constants.MODEL_COMPUTER1));
//        types.add(Asset.getInstance().getModel(Constants.MODEL_CHAIR1));

        model_references = new Array<ModelReference>();
        model_references.add(new ModelReference(Asset.getInstance().getModel(com.higaguin.uaq.Constants.MODEL_BUILDING1_1), com.higaguin.uaq.Constants.MODEL_BUILDING1_1_TAG));
//        model_references.add(new ModelReference(Asset.getInstance().getModel(Constants.MODEL_TABLE11), Constants.MODEL_TABLE11_TAG));
//        model_references.add(new ModelReference(Asset.getInstance().getModel(Constants.MODEL_TABLE10), Constants.MODEL_TABLE10_TAG));
//        model_references.add(new ModelReference(Asset.getInstance().getModel(Constants.MODEL_TABLE9), Constants.MODEL_TABLE9_TAG));
//        model_references.add(new ModelReference(Asset.getInstance().getModel(Constants.MODEL_TABLE7), Constants.MODEL_TABLE7_TAG));
//        model_references.add(new ModelReference(Asset.getInstance().getModel(Constants.MODEL_TABLE6), Constants.MODEL_TABLE6_TAG));
//        model_references.add(new ModelReference(Asset.getInstance().getModel(Constants.MODEL_TABLE4), Constants.MODEL_TABLE4_TAG));
//        model_references.add(new ModelReference(Asset.getInstance().getModel(Constants.MODEL_TABLE2), Constants.MODEL_TABLE2_TAG));
//        model_references.add(new ModelReference(Asset.getInstance().getModel(Constants.MODEL_TABLE1), Constants.MODEL_TABLE1_TAG));
//        model_references.add(new ModelReference(Asset.getInstance().getModel(Constants.MODEL_CPU), Constants.MODEL_CPU_TAG));
//        model_references.add(new ModelReference(Asset.getInstance().getModel(Constants.MODEL_COMPUTER1), Constants.MODEL_COMPUTER1_TAG));
//        model_references.add(new ModelReference(Asset.getInstance().getModel(Constants.MODEL_COMPUTER3), Constants.MODEL_COMPUTER3_TAG));
//        model_references.add(new ModelReference(Asset.getInstance().getModel(Constants.MODEL_RACKS), Constants.MODEL_RACKS_TAG));
//        model_references.add(new ModelReference(Asset.getInstance().getModel(Constants.MODEL_BOX), Constants.MODEL_BOX_TAG));
//        model_references.add(new ModelReference(Asset.getInstance().getModel(Constants.MODEL_DRAWER1), Constants.MODEL_DRAWER1_TAG));
//        model_references.add(new ModelReference(Asset.getInstance().getModel(Constants.MODEL_PRINTER), Constants.MODEL_PRINTER_TAG));
//        model_references.add(new ModelReference(Asset.getInstance().getModel(Constants.MODEL_EXTINTOR), Constants.MODEL_EXTINTOR_TAG));
//        model_references.add(new ModelReference(Asset.getInstance().getModel(Constants.MODEL_CHAIR1), Constants.MODEL_CHAIR1_TAG));
//        model_references.add(new ModelReference(Asset.getInstance().getModel(Constants.MODEL_CHAIR2), Constants.MODEL_CHAIR2_TAG));
//        model_references.add(new ModelReference(Asset.getInstance().getModel(Constants.MODEL_CHAIR3), Constants.MODEL_CHAIR3_TAG));
//        model_references.add(new ModelReference(Asset.getInstance().getModel(Constants.MODEL_CHAIR4), Constants.MODEL_CHAIR4_TAG));
//        model_references.add(new ModelReference(Asset.getInstance().getModel(Constants.MODEL_BANK), Constants.MODEL_BANK_TAG));
//        model_references.add(new ModelReference(Asset.getInstance().getModel(Constants.MODEL_BANK2), Constants.MODEL_BANK2_TAG));
//        model_references.add(new ModelReference(Asset.getInstance().getModel(Constants.MODEL_TRASH1), Constants.MODEL_TRASH1_TAG));
        model_references.add(new ModelReference(Asset.getInstance().getModel(com.higaguin.uaq.Constants.MODEL_INFO), com.higaguin.uaq.Constants.MODEL_INFO_TAG));
    }

    public void changeReference()
    {
        reference = model_references.get(index);
        Gdx.app.log(this.getClass().getName(), "Referencia: " + model_references.get(index).model_name);
        index++;
        index = (index >= model_references.size) ? 0 : index;
    }

    public void addBound()
    {
//        ModelBuilder builder = new ModelBuilder();
//        Model boxModel = builder.createBox(reference.getWidth(),reference.getHeight(),reference.getDepth(),new Material(ColorAttribute.createDiffuse(Color.GREEN)),
//                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        references.add(new com.higaguin.uaq.Reference(new Vector3(0,0,0), 0, new ModelInstance(reference.model), reference.model_name));
        if(!model_names.contains(reference.model_name, true))
            model_names.add(reference.model_name);
//        bounds.add(new ModelInstance(reference));
    }

    public void removeBound()
    {
//        if(bounds.size > 0)
//            bounds.removeIndex(bounds.size - 1);
        if(references.size > 0)
            references.removeIndex(references.size - 1);
    }

    public void update(Vector3 position)
    {
        if(references.size > 0) {
            references.get(references.size - 1).instance.transform.set(position, new Quaternion());
            references.get(references.size - 1).instance.transform.rotate(1, 0, 0, -90);
            references.get(references.size - 1).degrees = 0;
            references.get(references.size - 1).position = position;
        }
//        if(bounds.size > 0) {
//            bounds.get(bounds.size - 1).transform.set(position, new Quaternion());
//            bounds.get(bounds.size - 1).transform.rotate(1, 0, 0, -90);
//            degrees = 0;
//        }
        //rectangle.transform.trn(position);
//        rectangle.transform.set(position, new Quaternion());
//        Gdx.app.log(this.getClass().getName(), "X2: " + rectangle.transform.val[12] + ", Y2: " + rectangle.transform.val[13] + ", Z2: " + rectangle.transform.val[14]);
    }

    public void rotateLeft()
    {
        if(references.size > 0) {
            references.get(references.size - 1).degrees-=2;
            references.get(references.size - 1).instance.transform.rotate(new Vector3(0, 0, 1), -2);
            Gdx.app.log(this.getClass().getName(), "Degrees: " + degrees);
        }
//        if(bounds.size > 0) {
//            degrees-=2;
//            bounds.get(bounds.size - 1).transform.rotate(new Vector3(0, 0, 1), -2);
//            Gdx.app.log(this.getClass().getName(), "Degrees: " + degrees);
//        }
    }

    public void rotateRight()
    {
        if(references.size > 0) {
            references.get(references.size - 1).degrees+=2;
            references.get(references.size - 1).instance.transform.rotate(new Vector3(0, 0, 1), 2);
            Gdx.app.log(this.getClass().getName(), "Degrees: " + degrees);
        }
//        if(bounds.size > 0) {
//            degrees+=2;
//            bounds.get(bounds.size - 1).transform.rotate(new Vector3(0, 0, 1), 2);
//            Gdx.app.log(this.getClass().getName(), "Degrees: " + degrees);
//        }
    }

    public void rotateLeftLoadPoint()
    {
        if(loadPoint != null)
        {
            degrees_load_point-=2;
            loadPoint.transform.rotate(new Vector3(0, 1, 0), -2);
            Gdx.app.log(this.getClass().getName(), "Degrees: " + degrees_load_point);
        }
    }

    public void rotateRightLoadPoint()
    {
        if(loadPoint != null)
        {
            degrees_load_point+=2;
            loadPoint.transform.rotate(new Vector3(0, 1, 0), 2);
            Gdx.app.log(this.getClass().getName(), "Degrees: " + degrees_load_point);
        }
    }

    public void up()
    {
        if(references.size > 0) {
            references.get(references.size - 1).instance.transform.trn(0, 1, 0);
            Gdx.app.log(this.getClass().getName(), "Y: " + references.get(references.size - 1).instance.transform.val[13]);
        }
//        if(bounds.size > 0) {
//            bounds.get(bounds.size - 1).transform.trn(0, 1, 0);
//            Gdx.app.log(this.getClass().getName(), "Y: " + references.get(references.size - 1).instance.transform.val[13]);
//        }
    }

    public void down()
    {
        if(references.size > 0) {
            references.get(references.size - 1).instance.transform.trn(0, -1, 0);
            Gdx.app.log(this.getClass().getName(), "Y: " + references.get(references.size - 1).instance.transform.val[13]);
        }
//        if(bounds.size > 0) {
//            bounds.get(bounds.size - 1).transform.trn(0,-1,0);
//            Gdx.app.log(this.getClass().getName(), "Y: " + bounds.get(bounds.size - 1).transform.val[13]);
//        }
    }

    public void upLoadPoint()
    {
        if(loadPoint != null)
        {
            loadPoint.transform.trn(0, 1, 0);
            Gdx.app.log(this.getClass().getName(), "Y: " + loadPoint.transform.val[13]);
        }
    }

    public void downLoadPoint()
    {
        if(loadPoint != null)
        {
            loadPoint.transform.trn(0, -1, 0);
            Gdx.app.log(this.getClass().getName(), "Y: " + loadPoint.transform.val[13]);
        }
    }

    public void expandWidthLoadPoint()
    {
        if(loadPoint != null)
        {
            Matrix4 auxMatrix = loadPoint.transform;
            ++lodPoint_dimensions.x;

            ModelBuilder modelBuilder = new ModelBuilder();
            loadPoint = new ModelInstance(modelBuilder.createBox(lodPoint_dimensions.x, lodPoint_dimensions.y, lodPoint_dimensions.z,
                    new Material(ColorAttribute.createDiffuse(Color.BLUE)),
                    VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
            loadPoint.transform.set(auxMatrix);
            Gdx.app.log(this.getClass().getName(), "Width: " + lodPoint_dimensions.x + ", Height: " + lodPoint_dimensions.y + ", Depth: " + lodPoint_dimensions.z);
        }
    }

    public void reduceWidthLoadPoint()
    {
        if(loadPoint != null)
        {
            Matrix4 auxMatrix = loadPoint.transform;
            --lodPoint_dimensions.x;

            ModelBuilder modelBuilder = new ModelBuilder();
            loadPoint = new ModelInstance(modelBuilder.createBox(lodPoint_dimensions.x, lodPoint_dimensions.y, lodPoint_dimensions.z,
                    new Material(ColorAttribute.createDiffuse(Color.BLUE)),
                    VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
            loadPoint.transform.set(auxMatrix);
            Gdx.app.log(this.getClass().getName(), "Width: " + lodPoint_dimensions.x + ", Height: " + lodPoint_dimensions.y + ", Depth: " + lodPoint_dimensions.z);
        }
    }

    public void expandHeightLoadPoint()
    {
        if(loadPoint != null)
        {
            Matrix4 auxMatrix = loadPoint.transform;
            ++lodPoint_dimensions.y;

            ModelBuilder modelBuilder = new ModelBuilder();
            loadPoint = new ModelInstance(modelBuilder.createBox(lodPoint_dimensions.x, lodPoint_dimensions.y, lodPoint_dimensions.z,
                    new Material(ColorAttribute.createDiffuse(Color.BLUE)),
                    VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
            loadPoint.transform.set(auxMatrix);
            Gdx.app.log(this.getClass().getName(), "Width: " + lodPoint_dimensions.x + ", Height: " + lodPoint_dimensions.y + ", Depth: " + lodPoint_dimensions.z);
        }
    }

    public void reduceHeightLoadPoint()
    {
        if(loadPoint != null)
        {
            Matrix4 auxMatrix = loadPoint.transform;
            --lodPoint_dimensions.y;

            ModelBuilder modelBuilder = new ModelBuilder();
            loadPoint = new ModelInstance(modelBuilder.createBox(lodPoint_dimensions.x, lodPoint_dimensions.y, lodPoint_dimensions.z,
                    new Material(ColorAttribute.createDiffuse(Color.BLUE)),
                    VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
            loadPoint.transform.set(auxMatrix);
            Gdx.app.log(this.getClass().getName(), "Width: " + lodPoint_dimensions.x + ", Height: " + lodPoint_dimensions.y + ", Depth: " + lodPoint_dimensions.z);
        }
    }

    public void expandDepthLoadPoint()
    {
        if(loadPoint != null)
        {
            Matrix4 auxMatrix = loadPoint.transform;
            ++lodPoint_dimensions.z;

            ModelBuilder modelBuilder = new ModelBuilder();
            loadPoint = new ModelInstance(modelBuilder.createBox(lodPoint_dimensions.x, lodPoint_dimensions.y, lodPoint_dimensions.z,
                    new Material(ColorAttribute.createDiffuse(Color.BLUE)),
                    VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
            loadPoint.transform.set(auxMatrix);
            Gdx.app.log(this.getClass().getName(), "Width: " + lodPoint_dimensions.x + ", Height: " + lodPoint_dimensions.y + ", Depth: " + lodPoint_dimensions.z);
        }
    }

    public void reduceDepthLoadPoint()
    {
        if(loadPoint != null)
        {
            Matrix4 auxMatrix = loadPoint.transform;
            --lodPoint_dimensions.z;

            ModelBuilder modelBuilder = new ModelBuilder();
            loadPoint = new ModelInstance(modelBuilder.createBox(lodPoint_dimensions.x, lodPoint_dimensions.y, lodPoint_dimensions.z,
                    new Material(ColorAttribute.createDiffuse(Color.BLUE)),
                    VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
            loadPoint.transform.set(auxMatrix);
            Gdx.app.log(this.getClass().getName(), "Width: " + lodPoint_dimensions.x + ", Height: " + lodPoint_dimensions.y + ", Depth: " + lodPoint_dimensions.z);
        }
    }

    public void updateLoadPoint(Vector3 position)
    {
        degrees_load_point=0;

        ModelBuilder modelBuilder = new ModelBuilder();
        loadPoint = new ModelInstance(modelBuilder.createBox(lodPoint_dimensions.x, lodPoint_dimensions.y, lodPoint_dimensions.z,
                new Material(ColorAttribute.createDiffuse(Color.BLUE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
        loadPoint.transform.set(position, new Quaternion());
    }

    public void render(ModelBatch modelBatch, Environment environment)
    {
        for(com.higaguin.uaq.Reference reference : references)
            modelBatch.render(reference.instance, environment);

        if(loadPoint != null)
            modelBatch.render(loadPoint, environment);
//        for(ModelInstance bound : bounds)
//            modelBatch.render(bound, environment);
    }

    public void doJSON() {
        try {
            Array<Array<ModelReference>> referenceCollection = new Array<Array<ModelReference>>();
            FileHandle file = Gdx.files.local("auxiliar.json");
            Json json = new Json(JsonWriter.OutputType.json);
//            String str = null;
//        Writer writer = file.writer(false);
            json.setWriter(file.writer(false));
            for(String model_name : model_names){
                for (com.higaguin.uaq.Reference reference : references){
                    if(reference.model_name == model_name){
                        json.writeObjectStart();
                        json.writeValue("model", reference.model_name);
                        json.getWriter().write(System.getProperty("line.separator"));
                        json.writeValue("position", reference.position);
                        json.getWriter().write(System.getProperty("line.separator"));
                        json.writeValue("rotation", reference.degrees);
                        json.writeObjectEnd();
                        json.getWriter().write(System.getProperty("line.separator"));
                    }
                }
                json.getWriter().write(System.getProperty("line.separator"));
            }

//        for (Reference reference : references) {
////            str += json.prettyPrint(reference.position);
////            str += json.prettyPrint(reference.degrees);
////            str += System.getProperty("line.separator");
//            json.writeObjectStart();
//            json.writeValue("model", reference.model_name);
//            json.getWriter().write(System.getProperty("line.separator"));
//            json.writeValue("position", reference.position);
//            json.getWriter().write(System.getProperty("line.separator"));
//            json.writeValue("rotation", reference.degrees);
//            json.writeObjectEnd();
//            json.getWriter().write(System.getProperty("line.separator"));
////            file.writeString(System.getProperty("line.separator"), true)
//            Gdx.app.log(this.getClass().getName(), "JSON made it");
//        }
//            file.writeString(str, false);
            Gdx.app.log(this.getClass().getName(), "JSON made it");
            json.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dispose() {
        for(ModelReference modelReference : model_references)
            modelReference.dispose();

        reference.dispose();
    }
}
