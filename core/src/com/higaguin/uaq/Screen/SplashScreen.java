package com.higaguin.uaq.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.higaguin.uaq.Camera.CameraGUI;
import com.higaguin.uaq.Constants;
import com.higaguin.uaq.MyGdxGame;

/**
 * Created by higaguin on 27/01/2017.
 */
public class SplashScreen implements Screen {

    private final MyGdxGame myGdxGame;
    public Stage stage;

    public SplashScreen(final MyGdxGame myGdxGame)
    {
        this.myGdxGame = myGdxGame;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport(CameraGUI.getInstance().getCamera()));
        Image splashImage = new Image(new Texture(Constants.TEXTURE_SPLASH));
        float width = CameraGUI.getInstance().getCamera().viewportWidth;
        float height = CameraGUI.getInstance().getCamera().viewportHeight;
        splashImage.setSize(width, (splashImage.getHeight()  * width) / splashImage.getWidth());
        splashImage.moveBy(0, (height - splashImage.getHeight()) / 2);
        SequenceAction secuencia = new SequenceAction(Actions.delay(6.0f), completeAction);
        splashImage.addAction(secuencia);
        stage.addActor(splashImage);
    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    Action completeAction = new Action(){
        public boolean act( float delta ) {
            myGdxGame.setScreen(myGdxGame.uaqScreen);
            return true;
        }
    };

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
