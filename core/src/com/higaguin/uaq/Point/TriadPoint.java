package com.higaguin.uaq.Point;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by higaguin on 09/01/2017.
 */
public class TriadPoint extends Point {
    private boolean colliding;

    public TriadPoint(Vector3 position, Vector3 dimension) {
        super(position, dimension, Color.GREEN);
    }

    @Override
    public boolean checkBoundings(Vector3 inPosition) {
        boolean result = false;
        if (boundingBox.contains(inPosition)) {
            if (!colliding)
                result = true;
            colliding = true;
        } else {
            colliding = false;
        }
        return result;
    }
}
