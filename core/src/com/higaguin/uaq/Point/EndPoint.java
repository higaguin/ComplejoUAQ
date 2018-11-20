package com.higaguin.uaq.Point;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by higaguin on 09/01/2017.
 */
public class EndPoint extends TriadPoint {
    private int floor;
    private int part;

    public EndPoint(Vector3 position, Vector3 dimension, int floor, int part) {
        super(position, dimension);
        this.floor = floor;
        this.part = part;
    }

    public int getFloor() { return floor; }

    public int getPart() { return part; }
}
