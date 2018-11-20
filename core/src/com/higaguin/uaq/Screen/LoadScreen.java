package com.higaguin.uaq.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.higaguin.uaq.Asset;
import com.higaguin.uaq.Camera.CameraGUI;
import com.higaguin.uaq.MyGdxGame;

/**
 * Created by higaguin on 26/01/2017.
 */
public class LoadScreen implements Screen{
    public ShapeRenderer shapeRenderer;
    private float progress;

    private final MyGdxGame myGdxGame;

    public LoadScreen(final MyGdxGame myGdxGame)
    {
        this.myGdxGame = myGdxGame;
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        shapeRenderer.setProjectionMatrix(CameraGUI.getInstance().getCamera().combined);
        this.progress = 0f;
    }

    @Override
    public void render(float delta) {
        progress = MathUtils.lerp(progress, Asset.getInstance().getProgress(), .1f);
        if (Asset.getInstance().update() && progress >= Asset.getInstance().getProgress() - .001f)
            myGdxGame.setScreen(myGdxGame.splashScreen);

        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(Color.rgb888(0,128,255)));
        shapeRenderer.rect(0, 0, progress * (CameraGUI.getInstance().getCamera().viewportWidth), CameraGUI.getInstance().getCamera().viewportHeight);
        shapeRenderer.end();
    }

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
        shapeRenderer.dispose();
    }
}
