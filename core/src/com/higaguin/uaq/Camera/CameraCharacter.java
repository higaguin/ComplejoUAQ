package com.higaguin.uaq.Camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by higaguin on 21/10/2016.
 */
public class CameraCharacter {
    private static final CameraCharacter ourInstance = new CameraCharacter();
    PerspectiveCamera camPerspectve;

    public static CameraCharacter getInstance() { return ourInstance; }

    private CameraCharacter() { }

    public void init()
    {
        camPerspectve = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camPerspectve.position.set(91.19676f, -34.04187f, -14.452217f);
        camPerspectve.lookAt(0, 0, 0);
        camPerspectve.near = 0.1f;
        camPerspectve.far = 10000f;
    }

    public void moveLeft(Vector3 position)
    {
        camPerspectve.rotateAround(position, new Vector3(0, 1, 0), 5f);
        camPerspectve.update();
    }

    public void moveRight(Vector3 position)
    {
        camPerspectve.rotateAround(position, new Vector3(0, 1, 0), -5f);
    }

    public void moveDown()
    {
        camPerspectve.rotate(camPerspectve.direction.cpy().crs(Vector3.Y), 5f);
    }

    public void moveUp()
    {
        camPerspectve.rotate(camPerspectve.direction.cpy().crs(Vector3.Y), -5f);
    }

    public void updateCamera(Vector3 position)
    {
        camPerspectve.position.set(position);
    }
}
