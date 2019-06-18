package com.darko.danchev.generic.game.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.darko.danchev.generic.game.assets.enums.Color;

public class Assets {

    private final static int blocksCount = 4;

    public AssetManager manager;

    public static String player = "player/player.png";
    private static String[] blockFiles = {"enemy/enemy.png", "enemy/blue.png", /*"enemy/green.png", "enemy/orange.png",
            "enemy/purple.png", */"enemy/red.png" , "enemy/yellow.png"};
    private static Color[] blockColors = {Color.BLUE, Color.BLUE, Color.RED, Color.YELLOW};
    public static Block[] blocks = new Block[blocksCount];
    public static String background = "ui/background.png";
    public static String splash = "splash/start.png";
    public static String redbutton = "menu/red.png";
    public static String bluebutton = "menu/blue.png";
    public static String yellowbutton = "menu/yellow.png";

    public Assets(){
        manager = new AssetManager();
    }

    public void load(){

        initializeBlocks();

        manager.load(player, Texture.class);

        for(int i=0; i<blocksCount; i++) {
            manager.load(blocks[i].getFilename(), Texture.class);
        }
        manager.load(background, Texture.class);
        manager.load(splash, Texture.class);
        manager.load(redbutton, Texture.class);
        manager.load(bluebutton, Texture.class);
        manager.load(yellowbutton, Texture.class);
    }


    private void initializeBlocks() {
        for(int i =0; i<blocksCount; i++) {
            blocks[i] = new Block(blockFiles[i], blockColors[i]);
        }
    }

    public void dispose(){
        manager.dispose();
    }
}
