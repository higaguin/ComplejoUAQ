package com.higaguin.uaq;

import com.badlogic.gdx.Game;
import com.higaguin.uaq.Screen.LoadScreen;
import com.higaguin.uaq.Screen.SplashScreen;

public class MyGdxGame extends Game {
	public UAQScreen uaqScreen;
	public LoadScreen loadScreen;
	public SplashScreen splashScreen;
	//public CameraGUI cameraGui;

	@Override
	public void create () {
		uaqScreen = new UAQScreen();
		loadScreen = new LoadScreen(this);
		splashScreen = new SplashScreen(this);
		com.higaguin.uaq.Camera.CameraGUI.getInstance().init();
		Asset.getInstance().init();
		this.setScreen(loadScreen);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose() {
		Asset.getInstance().dispose();
		loadScreen.dispose();
		uaqScreen.dispose();
		splashScreen.dispose();
	}
}
