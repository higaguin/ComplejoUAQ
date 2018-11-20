package com.higaguin.uaq.World_folder;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.higaguin.uaq.Complex_folder.Nature;
import com.higaguin.uaq.Point.TriadPoint;

/**
 * Created by higaguin on 19/10/2016.
 */
public class World implements Disposable {
    public com.higaguin.uaq.Character character;
    public com.higaguin.uaq.Complex_folder.Complex complex;
    public Array<com.higaguin.uaq.Point.DualPoint> dualPoints;
    public Array<TriadPoint> triadPoints;
    public Array<com.higaguin.uaq.Complex_folder.Outside> outsides;
    public Nature skyBox;
    public Nature trees;
    public Array<com.higaguin.uaq.Complex_folder.Info> infos;
    public com.higaguin.uaq.Complex_folder.Building building;
    public com.higaguin.uaq.Complex_folder.Constraint constraint;
    public com.higaguin.uaq.Bullet.BulletWorld worldInstance;
    public com.higaguin.uaq.Builder builder;

    public World()
    {
        init();
    }

    public void init() {
        worldInstance = com.higaguin.uaq.Bullet.BulletWorld.getInstance();
        constraint = com.higaguin.uaq.Complex_folder.Constraint.getInstance();
        builder = com.higaguin.uaq.Builder.getInstance();
        worldInstance.init();
        constraint.init();
        builder.init();
        character = com.higaguin.uaq.Character.getInstance();
        outsides = new Array<com.higaguin.uaq.Complex_folder.Outside>();
        infos = new Array<com.higaguin.uaq.Complex_folder.Info>();
        dualPoints = new Array<com.higaguin.uaq.Point.DualPoint>();
        triadPoints = new Array<TriadPoint>();
        worldInstance.addBulletMainCharacter(character);

        building = null;

        outsides.add(new com.higaguin.uaq.Complex_folder.Outside(com.higaguin.uaq.Constants.MODEL_OUTSIDE1_TAG));
        outsides.add(new com.higaguin.uaq.Complex_folder.Outside(com.higaguin.uaq.Constants.MODEL_OUTSIDE2_TAG));
        outsides.add(new com.higaguin.uaq.Complex_folder.Outside(com.higaguin.uaq.Constants.MODEL_GRASS1_TAG));
        outsides.add(new com.higaguin.uaq.Complex_folder.Outside(com.higaguin.uaq.Constants.MODEL_BUILDING_SHELL1_TAG));
        outsides.add(new com.higaguin.uaq.Complex_folder.Outside(com.higaguin.uaq.Constants.MODEL_BUILDING_SHELL2_TAG));
        outsides.add(new com.higaguin.uaq.Complex_folder.Outside(com.higaguin.uaq.Constants.MODEL_BUILDING_SHELL3_TAG));

        infos.addAll(com.higaguin.uaq.Complex_folder.Info.constructInfoFromMap(com.higaguin.uaq.Constants.MODEL_INFO_JSON));

        dualPoints.addAll(com.higaguin.uaq.Point.DualPoint.constructPointFromMap(com.higaguin.uaq.Constants.MODEL_POINTS_JSON));

        complex = new com.higaguin.uaq.Complex_folder.Outside(com.higaguin.uaq.Constants.MODEL_COMPLEXS_EXTERIOR_TAG);

        trees = new Nature(com.higaguin.uaq.Constants.MODEL_TREES_TAG);

        skyBox = new Nature(com.higaguin.uaq.Constants.MODEL_SKYBOX_TAG);

        worldInstance.addBulletComplex(complex);

        worldInstance.addBulletConstraint(constraint);

        for(com.higaguin.uaq.Complex_folder.Complex complex : outsides)
            worldInstance.addBulletComplex(complex);

        for(com.higaguin.uaq.Complex_folder.Info info : infos)
            worldInstance.addBulletInfo(info);
    }

    public void render(ModelBatch modelBatch, Environment environment) {
        if (building != null)
            building.render(modelBatch, environment);

        if (complex != null)
            complex.render(modelBatch, environment);

        for (com.higaguin.uaq.Complex_folder.Complex complex : outsides)
            complex.render(modelBatch, environment);

        for (com.higaguin.uaq.Complex_folder.Info info : infos)
            info.render(modelBatch, environment);

        trees.render(modelBatch, environment);

        skyBox.render(modelBatch, environment);

//        constraint.render(modelBatch, environment);

//        for (Point loadPoint : dualPoints)
//            modelBatch.render(loadPoint.instance, environment);
//
//        for (Point loadPoint : triadPoints)
//            modelBatch.render(loadPoint.instance, environment);
    }

    public void updateDebug(Camera camera)
    {
        if(worldInstance.debug)
            worldInstance.updateDebugDraw(camera);
    }

    public void dispose()
    {
        worldInstance.dispose();
        character.dispose();

        for(com.higaguin.uaq.Complex_folder.Complex complex : outsides)
            complex.dispose();

        for(com.higaguin.uaq.Complex_folder.Info info : infos)
            info.dispose();

        if(building != null)
            building.dispose();

        constraint.dispose();
    }
}
