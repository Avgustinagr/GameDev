package com.fmi.game.development.ryb.assets;

import com.fmi.game.development.ryb.assets.enums.Color;

public class Block {

    private String filename;
    private Color color;

    public Block(String filename, Color color) {
        this.filename = filename;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color colour) {
        this.color = color;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}
