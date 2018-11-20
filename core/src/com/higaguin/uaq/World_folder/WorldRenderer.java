package com.higaguin.uaq.World_folder;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;

import com.badlogic.gdx.utils.Disposable;
import com.higaguin.uaq.Camera.CameraGUI;
import com.higaguin.uaq.MovilInput;

/**
 * Created by higaguin on 20/10/2016.
 */
public class WorldRenderer implements Disposable {
    Environment environment;
    ModelBatch modelBatch;
    OrthographicCamera cameraGUI;
    SpriteBatch batch;
    BitmapFont font;
    WorldController worldController;
    com.higaguin.uaq.Loading loading;

    public WorldRenderer (WorldController worldController){
        this.worldController = worldController;
        init();
    }

    public void init()
    {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, 1f, -0.8f, 0.2f));
        modelBatch = new ModelBatch();

        cameraGUI = CameraGUI.getInstance().getCamera();

        worldController.movilInput = MovilInput.getInstance();

        batch = new SpriteBatch();
        font = new BitmapFont();

        loading = com.higaguin.uaq.Loading.getInstance();
//        for(Info info : worldController.world.infos)
//            info.setStage(cameraGUI);
    }

    public void render(){
        renderWorld(modelBatch, environment);
        renderGui(batch);
    }

    private void renderWorld(ModelBatch modelBatch, Environment environment){
        com.higaguin.uaq.Camera.CameraHelper cameraHelper = worldController.camerahelper;
        Camera camera = cameraHelper.getSelectedCamera();
        worldController.world.updateDebug(camera);
        modelBatch.begin(camera);
        worldController.builder.render(modelBatch, environment);
        worldController.world.render(modelBatch, environment);
        modelBatch.end();
    }

    private void renderGui(SpriteBatch batch){
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();
//        renderGuiFpsCounter(batch);
        if(worldController.loadInstance.loading) {
            if(worldController.loadInstance.loadingType == com.higaguin.uaq.Loading.LoadingType.TriadPoint){
                renderLoadCount(batch, worldController.loadInstance.loading_status);
            }
            else if(worldController.loadInstance.loadingType == com.higaguin.uaq.Loading.LoadingType.EntryPoint ) {
                Gdx.gl.glClearColor( 0, 0, 0, 1 );
                Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
                renderLoadCount(batch, worldController.loadInstance.loading_status);
            }
            else if(worldController.loadInstance.loadingType == com.higaguin.uaq.Loading.LoadingType.OutPoint){
                worldController.transitionScreen.draw();
            }
        }
        else if(worldController.isInfo) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            worldController.infoPressed.draw();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
        else if (Gdx.app.getType() == Application.ApplicationType.Android)
            worldController.movilInput.render(batch);

        batch.end();
    }

    private void renderGuiFpsCounter(SpriteBatch batch){
        float x = cameraGUI.viewportWidth - 100;
        float y = cameraGUI.viewportHeight - 15;
        int fps = Gdx.graphics.getFramesPerSecond();
        if(fps >= 45){
            font.setColor(0,0,204,1);
        }else if (fps >= 30){
            font.setColor(255,255,0,1);
        }else{
            font.setColor(255,0,0,1);
        }
        font.draw(batch, "FPS: " + fps + " Vel. " + com.higaguin.uaq.Character.getInstance().velocity, x, y);
        font.setColor(1, 1, 1, 1);
    }

    private void renderLoadCount(SpriteBatch batch, float loading_estatus) {
        float x = cameraGUI.viewportWidth - (cameraGUI.viewportWidth / 2) - (loading.loadFont.getRegion().getRegionWidth() / 3);
        float y = cameraGUI.viewportHeight - (cameraGUI.viewportHeight / 2) + (loading.loadFont.getRegion().getRegionWidth() / 12);

        loading.loadFont.draw(batch, String.format("%.02f", loading_estatus) + "%", x, y);
    }

    public void dispose()
    {
        batch.dispose();
        modelBatch.dispose();
        font.dispose();
    }
}
