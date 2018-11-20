package com.higaguin.uaq.Camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by higaguin on 21/10/2016.
 */
public class CameraDebug {
    private static final CameraDebug ourInstance = new CameraDebug();
    public OrthographicCamera camOrthogonal;
    public Vector2 positionOrthogonal;
    public float velocityOrthogonal;

    public static CameraDebug getInstance() { return ourInstance; }

    private CameraDebug() { }

    public void init()
    {
        positionOrthogonal = new Vector2(0, 600);
        camOrthogonal = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camOrthogonal.position.set(positionOrthogonal, 0);
        camOrthogonal.lookAt(0, 0, 0);
        camOrthogonal.near = 1f;
        camOrthogonal.far = 10000f;
        camOrthogonal.update();
    }

    public void zoomIn() {
        camOrthogonal.zoom  += 0.1 * velocityOrthogonal;
        Gdx.app.log(this.getClass().getName(), Float.toString(camOrthogonal.zoom));
    }

    public void zoomOut() {
        camOrthogonal.zoom  -= 0.1 * velocityOrthogonal;
        Gdx.app.log(this.getClass().getName(), Float.toString(camOrthogonal.zoom));}

    public void moveLeft()
    {
        camOrthogonal.translate(-2 * velocityOrthogonal, 0, 0);
    }

    public void moveRigth()
    {
        camOrthogonal.translate(6 * velocityOrthogonal, 0, 0);
    }

    public void moveDown()
    {
        camOrthogonal.translate(0, 0, 2 * velocityOrthogonal);
    }

    public void moveUp() { camOrthogonal.translate(0, 0, -2 * velocityOrthogonal); }
}
