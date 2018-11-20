package com.higaguin.uaq.Point;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by higaguin on 09/01/2017.
 */
public class EntryPoint extends DualPoint {
    private String building;
    private String splash_image;

    public EntryPoint(Vector3 position, Vector3 dimension, Vector3 characterPosition, String building, String splash_image, String shell_building) {
        super(position, dimension, characterPosition, shell_building, Color.GREEN);
        this.building = building;
        this.splash_image = splash_image;
    }

    public String getBuilding() { return building; }

    public String getSplashImage() { return splash_image; }
}
