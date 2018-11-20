package com.higaguin.uaq.Point;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

/**
 * Created by higaguin on 09/01/2017.
 */
public abstract class Point {

    public Vector3 position;
    public Vector3 dimension;
    public BoundingBox boundingBox;
    public ModelInstance instance;
    public abstract boolean checkBoundings(Vector3 inPosition);

    public Point(Vector3 position, Vector3 dimension, Color color)
    {
        this.position = position;
        this.dimension = dimension;
        ModelBuilder modelBuilder = new ModelBuilder();
        instance = new ModelInstance(modelBuilder.createBox(dimension.x, dimension.y, dimension.z,
                new Material(ColorAttribute.createDiffuse(color)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
        instance.transform.set(position, new Quaternion());

        boundingBox = new BoundingBox();
        instance.calculateBoundingBox(boundingBox);
        boundingBox.mul(instance.transform);
    }
}
