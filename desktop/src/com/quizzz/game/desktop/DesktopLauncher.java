package com.quizzz.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.quizzz.game.quizzz;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new quizzz(), config);
		config.width = quizzz.WIDTH;
		config.height = quizzz.HEIGHT;
		config.title = quizzz.TITLE;
	}
}
