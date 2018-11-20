package com.higaguin.uaq.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.higaguin.uaq.Camera.CameraGUI;

/**
 * Created by higaguin on 10/01/2017.
 */
public class TransitionScreen implements Disposable {
    private Stage stage;
    private boolean finish;
    private Image splashImage;
    private SequenceAction secuencia;


    public TransitionScreen(Camera camera, String texture_transition)
    {
        stage = new Stage(new ScreenViewport(camera));
        splashImage = new Image(new Texture(texture_transition));
        float width = CameraGUI.getInstance().getCamera().viewportWidth;
        float height = CameraGUI.getInstance().getCamera().viewportHeight;
        splashImage.setSize(width, (splashImage.getHeight()  * width) / splashImage.getWidth());
        splashImage.moveBy(0, (height - splashImage.getHeight()) / 2);
        finish = false;
        stage.addActor(splashImage);
    }

    public void draw(){
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    Action completeAction = new Action(){
        public boolean act( float delta ) {
            finish = true;
            return true;
        }
    };

    private SequenceAction fadeOut = Actions.sequence(
            Actions.alpha(1),
            Actions.fadeOut(0.5f)
    );

    private SequenceAction fadeIn = Actions.sequence(
            Actions.alpha(0),
            Actions.fadeIn(0.2f)
    );

    private SequenceAction delay = Actions.sequence(
            Actions.delay(3.0f)
    );

    public void reset() {
        fadeIn.reset();
        delay.reset();
        fadeOut.reset();
        completeAction.reset();
        secuencia = new SequenceAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.2f), Actions.delay(3.0f), Actions.fadeOut(0.5f), completeAction));
        finish = false;
        stage.addAction(secuencia);
    }

    public boolean isFinish(){
        return finish;
    }

    public void dispose() {
        stage.dispose();
    }
}
