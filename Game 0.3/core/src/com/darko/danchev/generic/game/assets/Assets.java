package com.darko.danchev.generic.game.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Assets {

    public AssetManager manager;

    public static String player = "player/player.png";
    public static String enemy = "enemy/enemy.png";
    public static String background = "ui/background.png";
    public static String splash = "splash/start.png";
    public static String redbutton = "menu/red.png";
    public static String bluebutton = "menu/blue.png";
    public static String yellowbutton = "menu/yellow.png";

    public Assets(){
        manager = new AssetManager();
    }

    public void load(){
        manager.load(player, Texture.class);
        manager.load(enemy, Texture.class);
        manager.load(background, Texture.class);
        manager.load(splash, Texture.class);
        manager.load(redbutton, Texture.class);
        manager.load(bluebutton, Texture.class);
        manager.load(yellowbutton, Texture.class);
    }


    public void dispose(){
        manager.dispose();
    }
}
