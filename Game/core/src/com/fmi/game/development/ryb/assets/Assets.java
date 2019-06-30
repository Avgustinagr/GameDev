package com.fmi.game.development.ryb.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.fmi.game.development.ryb.assets.enums.Color;

public class Assets {

    private final static int BLOCKS_COUNT = 36;

    public AssetManager manager;

    public static String player = "ball/player.png";
    private static String[] blockFiles = { "enemyBlock/red/blue.png", "enemyBlock/red/green.png", "enemyBlock/red/orange.png",
            "enemyBlock/red/purple.png", "enemyBlock/red/red.png" , "enemyBlock/red/yellow.png",
            "enemyBlock/blue/blue.png", "enemyBlock/blue/green.png", "enemyBlock/blue/orange.png",
            "enemyBlock/blue/purple.png", "enemyBlock/blue/red.png" , "enemyBlock/blue/yellow.png",
            "enemyBlock/yellow/blue.png", "enemyBlock/yellow/green.png", "enemyBlock/yellow/orange.png",
            "enemyBlock/yellow/purple.png", "enemyBlock/yellow/red.png" , "enemyBlock/yellow/yellow.png",
            "enemyBlock/purple/blue.png", "enemyBlock/purple/green.png", "enemyBlock/purple/orange.png",
            "enemyBlock/purple/purple.png", "enemyBlock/purple/red.png" , "enemyBlock/purple/yellow.png",
            "enemyBlock/green/blue.png", "enemyBlock/green/green.png", "enemyBlock/green/orange.png",
            "enemyBlock/green/purple.png", "enemyBlock/green/red.png" , "enemyBlock/green/yellow.png",
            "enemyBlock/orange/blue.png", "enemyBlock/orange/green.png", "enemyBlock/orange/orange.png",
            "enemyBlock/orange/purple.png", "enemyBlock/orange/red.png" , "enemyBlock/orange/yellow.png",

    };
    private static Color[] blockColors = {Color.RED, Color.BLUE, Color.YELLOW, Color.PURPLE, Color.GREEN, Color.ORANGE};
    public static Block[] blocks = new Block[BLOCKS_COUNT];
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

        for(int i = 0; i< BLOCKS_COUNT; i++) {
            manager.load(blocks[i].getFilename(), Texture.class);
        }

        manager.load(background, Texture.class);
        manager.load(splash, Texture.class);
        manager.load(redbutton, Texture.class);
        manager.load(bluebutton, Texture.class);
        manager.load(yellowbutton, Texture.class);

    }


    private void initializeBlocks() {
        for(int i = 0; i< BLOCKS_COUNT; i++) {
            blocks[i] = new Block(blockFiles[i], blockColors[i/6]);
        }
    }

    public void dispose(){
        manager.dispose();
    }
}