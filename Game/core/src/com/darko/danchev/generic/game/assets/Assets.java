package com.darko.danchev.generic.game.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Assets {

    private final static int enemiesCount = 7;

    public AssetManager manager;

    public static String player = "player/player.png";
    public static String[] enemies = {"enemy/enemy.png", "enemy/blue.png", "enemy/green.png", "enemy/orange.png",
            "enemy/purple.png", "enemy/red.png" , "enemy/yellow.png"};
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

        for(int i=0; i<enemiesCount; i++) {
            manager.load(enemies[i], Texture.class);
        }        manager.load(background, Texture.class);

        manager.load(splash, Texture.class);
        manager.load(redbutton, Texture.class);
        manager.load(bluebutton, Texture.class);
        manager.load(yellowbutton, Texture.class);
    }


    public void dispose(){
        manager.dispose();
    }
}
