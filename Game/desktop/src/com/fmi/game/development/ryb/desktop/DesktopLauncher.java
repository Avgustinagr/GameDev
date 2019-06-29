package com.fmi.game.development.ryb.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.fmi.game.development.ryb.RYB;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 2520 / 4;
		config.height = 4160 / 4;
		new LwjglApplication(new RYB(), config);
	}
}
