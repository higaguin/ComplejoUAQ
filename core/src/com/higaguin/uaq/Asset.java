package com.higaguin.uaq;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by higaguin on 19/10/2016.
 */
public class Asset implements Disposable, AssetErrorListener {
    private static Asset ourInstance = new Asset();
    private AssetManager assetManager;

    public static Asset getInstance() {
        return ourInstance;
    }

    private Asset() {
    }

    public void init()
    {
        assetManager = new AssetManager();
        assetManager.setErrorListener(this);

        loadAsset(Constants.MODEL_CONSTRAINT, Model.class);
        loadAsset(Constants.MODEL_OUTSIDE1, Model.class);
        loadAsset(Constants.MODEL_OUTSIDE2, Model.class);
        loadAsset(Constants.MODEL_GRASS1, Model.class);

        loadAsset(Constants.MODEL_COMPLEXS_BUILDING1_FLOOR1_PART1, Model.class);
        loadAsset(Constants.MODEL_COMPLEXS_BUILDING1_FLOOR1_PART2, Model.class);
        loadAsset(Constants.MODEL_COMPLEXS_BUILDING1_FLOOR2_PART1, Model.class);
        loadAsset(Constants.MODEL_COMPLEXS_BUILDING1_FLOOR3_PART1, Model.class);
        loadAsset(Constants.MODEL_COMPLEXS_BUILDING2_FLOOR1_PART1, Model.class);
        loadAsset(Constants.MODEL_COMPLEXS_BUILDING2_FLOOR1_PART2, Model.class);
        loadAsset(Constants.MODEL_COMPLEXS_BUILDING2_FLOOR2_PART1, Model.class);
        loadAsset(Constants.MODEL_COMPLEXS_BUILDING2_FLOOR2_PART2, Model.class);
        loadAsset(Constants.MODEL_COMPLEXS_BUILDING2_FLOOR3_PART1, Model.class);
        loadAsset(Constants.MODEL_COMPLEXS_BUILDING3_FLOOR1_PART1, Model.class);
        loadAsset(Constants.MODEL_COMPLEXS_EXTERIOR, Model.class);
        loadAsset(Constants.MODEL_BUILDING_SHELL3, Model.class);
        loadAsset(Constants.MODEL_BUILDING_SHELL2, Model.class);
        loadAsset(Constants.MODEL_BUILDING_SHELL1, Model.class);
        loadAsset(Constants.MODEL_BUILDING1_1, Model.class);
        loadAsset(Constants.MODEL_BUILDING2, Model.class);
        loadAsset(Constants.MODEL_BUILDING3, Model.class);
//        loadAsset(Constants.MODEL_TABLE11, Model.class);
//        loadAsset(Constants.MODEL_TABLE10, Model.class);
//        loadAsset(Constants.MODEL_TABLE9, Model.class);
//        loadAsset(Constants.MODEL_TABLE7, Model.class);
//        loadAsset(Constants.MODEL_TABLE6, Model.class);
//        loadAsset(Constants.MODEL_TABLE4, Model.class);
//        loadAsset(Constants.MODEL_TABLE2, Model.class);
//        loadAsset(Constants.MODEL_TABLE1, Model.class);
//        loadAsset(Constants.MODEL_CPU, Model.class);
//        loadAsset(Constants.MODEL_COMPUTER1, Model.class);
//        loadAsset(Constants.MODEL_COMPUTER2, Model.class);
//        loadAsset(Constants.MODEL_COMPUTER3, Model.class);
//        loadAsset(Constants.MODEL_CHAIR1, Model.class);
//        loadAsset(Constants.MODEL_CHAIR2, Model.class);
//        loadAsset(Constants.MODEL_CHAIR3, Model.class);
//        loadAsset(Constants.MODEL_CHAIR4, Model.class);
//        loadAsset(Constants.MODEL_BANK, Model.class);
//        loadAsset(Constants.MODEL_BOX, Model.class);
//        loadAsset(Constants.MODEL_DRAWER1, Model.class);
//        loadAsset(Constants.MODEL_PRINTER, Model.class);
//        loadAsset(Constants.MODEL_EXTINTOR, Model.class);
//        loadAsset(Constants.MODEL_RACKS, Model.class);
//        loadAsset(Constants.MODEL_BANK2, Model.class);
//        loadAsset(Constants.MODEL_TRASH1, Model.class);
        loadAsset(Constants.MODEL_SKYBOX, Model.class);
        loadAsset(Constants.MODEL_TREES, Model.class);
        loadAsset(Constants.MODEL_INFO, Model.class);
        loadAsset(Constants.MUSIC, Music.class);
    }

    private void loadAsset(String name, Class clase)
    {
        assetManager.load(name, clase);
        //assetManager.finishLoadingAsset(name);
        //Gdx.app.debug(Asset.class.getName(), "# of assets loaded:" + assetManager.getAssetNames().size);
    }

    public Music getMusic() { return assetManager.get(Constants.MUSIC, Music.class); }

    public float getProgress()
    {
        return assetManager.getProgress();
    }

    public boolean update()
    {
        return assetManager.update();
    }

    public Model getModel(String name)
    {
        return assetManager.get(name, Model.class);
    }

    //public String getName(Model model){ return assetManager.getAssetFileName(model); }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(Asset.class.getName(), "No se puede cargar asset '" + asset.fileName + "'", (Exception)throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
