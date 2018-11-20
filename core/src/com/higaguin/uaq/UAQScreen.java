package com.higaguin.uaq;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by higaguin on 26/01/2017.
 */
public class UAQScreen implements Screen {
    com.higaguin.uaq.World_folder.WorldController worldController;
    com.higaguin.uaq.World_folder.WorldRenderer worldRenderer;

    @Override
    public void show() {
        worldController = new com.higaguin.uaq.World_folder.WorldController();
        worldRenderer = new com.higaguin.uaq.World_folder.WorldRenderer(worldController);
    }

    @Override
    public void render(float delta) {
        worldController.update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        //Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        worldRenderer.render();
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
        worldController.dispose();
        worldRenderer.dispose();
        //Asset.getInstance().dispose();
    }
}
