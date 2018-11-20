package com.higaguin.uaq;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by higaguin on 14/11/2016.
 */
public class MovilInput {
    private static MovilInput ourInstance = new MovilInput();
    public OrthographicCamera cameraGUI;
    public Sprite left_input, right_input, top_input, bottom_input, cornerleft_foward_input, cornerright_foward_input, cornerleft_back_input, cornerright_back_input;
    public static MovilInput getInstance() {
        return ourInstance;
    }

    private MovilInput(){  }

    public void init()
    {
        this.cameraGUI = com.higaguin.uaq.Camera.CameraGUI.getInstance().getCamera();
        left_input = new Sprite(new Texture("sidestouch.png"));
        left_input.setPosition(0, cameraGUI.viewportHeight / 4f);
        left_input.setSize(cameraGUI.viewportWidth / 5f, cameraGUI.viewportHeight / 2);

        right_input = new Sprite(new Texture("sidestouch.png"));
        right_input.setPosition(cameraGUI.viewportWidth - (cameraGUI.viewportWidth / 5f), cameraGUI.viewportHeight / 4f);
        right_input.setSize(cameraGUI.viewportWidth / 5f, cameraGUI.viewportHeight / 2);
        right_input.setFlip(true, false);

        top_input = new Sprite(new Texture("topstouch.png"));
        top_input.setPosition(cameraGUI.viewportWidth / 5f, cameraGUI.viewportHeight - (cameraGUI.viewportHeight / 4f));
        top_input.setSize(cameraGUI.viewportWidth - ((cameraGUI.viewportWidth / 5f) * 2), cameraGUI.viewportHeight / 4f);

        bottom_input = new Sprite(new Texture("topstouch.png"));
        bottom_input.setPosition(cameraGUI.viewportWidth / 5f, 0);
        bottom_input.setSize(cameraGUI.viewportWidth - ((cameraGUI.viewportWidth / 5f) * 2), cameraGUI.viewportHeight / 4f);
        bottom_input.setFlip(false, true);

        cornerleft_foward_input = new Sprite(new Texture("cornerfoward.png"));
        cornerleft_foward_input.setPosition(0, cameraGUI.viewportHeight - (cameraGUI.viewportHeight / 4f));
        cornerleft_foward_input.setSize(cameraGUI.viewportWidth / 5f, cameraGUI.viewportHeight / 4f);

        cornerright_foward_input = new Sprite(new Texture("cornerfoward.png"));
        cornerright_foward_input.setPosition(cameraGUI.viewportWidth - (cameraGUI.viewportWidth / 5f), cameraGUI.viewportHeight - (cameraGUI.viewportHeight / 4f));
        cornerright_foward_input.setSize(cameraGUI.viewportWidth / 5f, cameraGUI.viewportHeight / 4f);
        cornerright_foward_input.setFlip(true, false);

        cornerleft_back_input = new Sprite(new Texture("cornerback.png"));
        cornerleft_back_input.setPosition(0, 0);
        cornerleft_back_input.setSize(cameraGUI.viewportWidth / 5f, cameraGUI.viewportHeight / 4f);

        cornerright_back_input = new Sprite(new Texture("cornerback.png"));
        cornerright_back_input.setPosition(cameraGUI.viewportWidth - (cameraGUI.viewportWidth / 5f), 0);
        cornerright_back_input.setSize(cameraGUI.viewportWidth / 5f, cameraGUI.viewportHeight / 4f);
        cornerright_back_input.setFlip(true, false);
    }

    public Vector3 getRealCoordenates(int screenX, int screenY)
    {
        Vector3 result = new Vector3();
        cameraGUI.unproject(result.set(screenX, screenY, 0));
        return result;
    }

    public void render(SpriteBatch batch)
    {
        left_input.draw(batch);
        right_input.draw(batch);
        top_input.draw(batch);
        bottom_input.draw(batch);
        cornerleft_foward_input.draw(batch);
        cornerright_foward_input.draw(batch);
        cornerleft_back_input.draw(batch);
        cornerright_back_input.draw(batch);
    }
}
