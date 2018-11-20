package com.higaguin.uaq.Camera;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;

/**
 * Created by higaguin on 11/09/2016.
 */
public class CameraHelper {
    private static final CameraHelper ourInstance = new CameraHelper();

    private Camera selectedCamera;
    private PerspectiveCamera camPerspectve;
    private OrthographicCamera camOrthogonal;
    public boolean debug;

    public static CameraHelper getInstance() { return ourInstance; }

    private CameraHelper(){}

    public void init(CameraDebug cameraDebug, CameraCharacter cameraCharacter)
    {
        camOrthogonal = cameraDebug.camOrthogonal;
        camPerspectve = cameraCharacter.camPerspectve;
        setCharacterCamera();
    }

    public void setTopCamera()
    {
        camOrthogonal.position.set(0, 600, 0);
        camOrthogonal.lookAt(0, 0, 0);
        selectedCamera = camOrthogonal;
        debug = true;
    }

    public void setRightCamera()
    {
        camOrthogonal.position.set(0, 0, -100);
        camOrthogonal.lookAt(0, 0, 0);
        selectedCamera = camOrthogonal;
        debug = true;
    }

    public void setCharacterCamera()
    {
        selectedCamera = camPerspectve;
        debug = false;
    }

    public Camera getSelectedCamera() { return selectedCamera; }
}