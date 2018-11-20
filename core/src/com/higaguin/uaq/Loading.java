package com.higaguin.uaq;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by higaguin on 11/01/2017.
 */
public class Loading implements Disposable{
    private static Loading ourInstance = new Loading();
    public boolean loading;
    public float loading_status;

    public enum LoadingType { NONE, OutPoint, EntryPoint, TriadPoint }
    public LoadingType loadingType;
    public boolean startLoadFloor;
    public BitmapFont loadFont;

    public static Loading getInstance() {
        return ourInstance;
    }

    private Loading(){ }

    public void init()
    {
        loading = false;
        loadingType = LoadingType.NONE;
        loading_status = 0;
        startLoadFloor = false;
        loadFont = new BitmapFont(Gdx.files.internal(Constants.FONT_ARIALNARROW));
    }

    @Override
    public void dispose() {
        loadFont.dispose();
    }
}
