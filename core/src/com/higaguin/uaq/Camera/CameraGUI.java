package com.higaguin.uaq.Camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by higaguin on 15/11/2016.
 */
public class CameraGUI {
    private static final CameraGUI cameraGUI = new CameraGUI();
    private OrthographicCamera othographicCamera;

    public static CameraGUI getInstance() { return cameraGUI; }

    private CameraGUI()
    {
        init();
    }

    public void init()
    {
        othographicCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getWidth());
        othographicCamera.position.set(othographicCamera.viewportWidth / 2f, othographicCamera.viewportHeight / 2f, 0);
        othographicCamera.update();
    }

    public OrthographicCamera getCamera() { return othographicCamera; }
}
