package com.higaguin.uaq.Point;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;


/**
 * Created by higaguin on 09/01/2017.
 */
public class OutPoint extends DualPoint {
    public OutPoint(Vector3 position, Vector3 dimension, Vector3 characterPosition, String shell_building) {
        super(position, dimension, characterPosition, shell_building, Color.BLUE);
    }
}
