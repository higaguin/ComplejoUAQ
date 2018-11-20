package com.higaguin.uaq.Complex_folder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.higaguin.uaq.Asset;
import com.higaguin.uaq.Camera.CameraGUI;
import com.higaguin.uaq.Constants;

/**
 * Created by higaguin on 12/01/2017.
 */
public class Info extends com.higaguin.uaq.Bullet.BulletObject implements Disposable {
    com.higaguin.uaq.World_folder.WorldController worldController;
    public Vector3 position;
    public String title;
    public String text;
    public String model_name;

    private Stage stage;
    private Stack stack;
    private Camera camera;
    private ShapeRenderer shapeRenderer;

    public Info(Vector3 position, String title, String text)
    {
        model = Asset.getInstance().getModel(Constants.MODEL_INFO);
        model.materials.get(0).set(FloatAttribute.createAlphaTest(0.90f));
        instance = new ModelInstance(model);
        instance.transform.rotate(1, 0, 0, -90);
        instance.transform.trn(position);
        this.position = position;
        this.title = title;
        this.text = text;
        this.camera = CameraGUI.getInstance().getCamera();

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        stage = new Stage(new ScreenViewport(camera));
        stack = new Stack();
        stack.setSize(camera.viewportWidth, camera.viewportHeight);
        //Gdx.input.setInputProcessor(stage);
        stage.addActor(stack);
    }

    public void createControls(com.higaguin.uaq.World_folder.WorldController worldController)
    {
        this.worldController = worldController;
        stack.add(createTexts());
        stack.add(createOutButton());
        Gdx.input.setInputProcessor(stage);
    }

    private Table createTexts()
    {
        Label.LabelStyle mediumText = new Label.LabelStyle();
        mediumText.font = new BitmapFont(Gdx.files.internal(Constants.FONT_ARIALNARROW_MEDIUM));
        Label.LabelStyle smallText = new Label.LabelStyle();
        smallText.font = new BitmapFont(Gdx.files.internal(Constants.FONT_ARIALNARROW_SMALL));

        Table layer = new Table();
        layer.left().top().padLeft(camera.viewportWidth / 12).padTop(camera.viewportHeight / 12);
        Label title_label = new Label(title, mediumText);
        Label text_label = new Label(text, smallText);
        text_label.setWrap(true);
        text_label.setWidth(100);
        text_label.pack();
        layer.add(title_label);
        layer.row();
        layer.add(text_label).width(camera.viewportWidth - (camera.viewportWidth / 6));
        return layer;
    }

    private Table createOutButton()
    {
        Table layer = new Table();
        layer.right().bottom().padRight(camera.viewportWidth / 12).padBottom(camera.viewportHeight / 12);
        TextButton.TextButtonStyle mediumText = new TextButton.TextButtonStyle();
        mediumText.font = new BitmapFont(Gdx.files.internal(Constants.FONT_ARIALNARROW_MEDIUM));
        TextButton outButton = new TextButton("Salir", mediumText);
        outButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                outOfInfo();
            }
        });
        layer.add(outButton);
        return layer;
    }

    private void outOfInfo()
    {
        worldController.isInfo = false;
    }

    public void render(ModelBatch modelBatch, Environment environment)
    {
        modelBatch.render(instance, environment);
    }

    public void draw() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.5f);
        shapeRenderer.rect(0, 0, camera.viewportWidth, camera.viewportHeight);
        shapeRenderer.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public static Array<Info> constructInfoFromMap(String info_json)
    {
        Array<Info> result_infos = new Array<Info>();
        FileHandle point_map = Gdx.files.internal(info_json);
        JsonValue infos =  new JsonReader().parse(point_map).get("info");
        if(infos != null) {
            for (JsonValue info : infos) {
                JsonValue info_position = info.get("position");
                String title = info.getString("title");
                String text = info.getString("text");
                result_infos.add(new Info(new Vector3(info_position.getFloat("x"), info_position.getFloat("y"), info_position.getFloat("z")), title, text));
            }
        }
        return result_infos;
    }

    public void dispose()
    {
        stage.dispose();
        shapeRenderer.dispose();
    }
}
