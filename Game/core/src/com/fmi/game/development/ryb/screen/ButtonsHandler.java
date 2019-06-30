package com.fmi.game.development.ryb.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.fmi.game.development.ryb.RYB;
import com.fmi.game.development.ryb.assets.Assets;
import com.fmi.game.development.ryb.assets.enums.Color;
import com.fmi.game.development.ryb.game.GameWorld;

import java.util.ArrayList;
import java.util.List;

import static com.fmi.game.development.ryb.RYB.WORLD_HEIGHT;

public class ButtonScreen {

    private final int NUMBER_OF_BUTTONS = 3;
    private final int MAX_NUMBER_OF_TOUCH_BUTTONS = 2;

    private float worldWidth;
    private Stage stage;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton button;
    private RYB ryb;
    private GameWorld gameWorld;

    public ButtonScreen(float worldWidth, Stage stage, RYB ryb, GameWorld gameWorld) {
        this.worldWidth = worldWidth;
        this.stage = stage;
        this.ryb = ryb;
        this.gameWorld = gameWorld;
        setUpButtons();
    }

    private void setUpButtons() {
        /* Sets up colour menu at the bottom */

        List<ImageButton> buttons = new ArrayList<ImageButton>(NUMBER_OF_BUTTONS);

        String[] buttonsFileNames = {Assets.redbutton, Assets.bluebutton, Assets.yellowbutton};

        for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {
            myTextureRegion = new TextureRegion(ryb.assets.manager.get(buttonsFileNames[i], Texture.class));
            myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
            button = new ImageButton(myTexRegionDrawable); //Set the button up
            buttons.add(button);
        }


        float i = 0;
        float buttonWidth = this.worldWidth / 3;
        float buttonHeight = 5;
        for (ImageButton button : buttons) {
            button.setPosition(i, -1);
            button.setSize(buttonWidth + 1, buttonHeight);
            this.stage.addActor(button);
            i = i + buttonWidth;
        }
    }

    public void changeBackground() {
        int colorValue = 0;
        /*
         RED = 1
         YELLOW = 3
         BLUE = 5
         ORANGE = 4  (RED + YELLOW)
         PURPLE = 6 (RED + BLUE)
         GREEN = 8 (BLUE + YELLOW)
         */
        boolean leftPressed = false;
        boolean rightPressed = false;
        boolean downPressed = false;

        for (int i = 0; i < MAX_NUMBER_OF_TOUCH_BUTTONS; i++) {
            // red part
            if ((Gdx.input.getX(i) < Gdx.graphics.getWidth() / 3f
                    && Gdx.input.isTouched(i)) || (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT) && !leftPressed)) {

                colorValue += 1;
                leftPressed = Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT);
            }
            //yellow part
            else if ((Gdx.input.getX(i) > (Gdx.graphics.getWidth() / 3f) * 2
                    && Gdx.input.isTouched(i)) || (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT) && !rightPressed)) {

                colorValue += 3;
                rightPressed = Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT);
            }

            // blue part
            else if (((Gdx.input.getX(i) > Gdx.graphics.getWidth() / 3f && Gdx.input.getX(i) < (Gdx.graphics.getWidth() / 3f) * 2)
                    && Gdx.input.isTouched(i)) || (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN) && !downPressed)) {

                colorValue += 5;
                downPressed = Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN);
            }
            gameWorld.getBall().setBackgroundColor(Color.COLORLESS);
        }
        flagFilterBackground(colorValue);
    }

    private void flagFilterBackground(int flag) {
        /* Helps to change backhound according to the given flag
         * Used in - changeBackground() */

        switch (flag) {
            case 1:
                helperChangeBackground(242, 4, 48, Color.RED);
                break;
            case 3:
                helperChangeBackground(242, 182, 4, Color.YELLOW);
                break;
            case 5:
                helperChangeBackground(4, 107, 242, Color.BLUE);
                break;
            case 4:
                helperChangeBackground(237, 87, 20, Color.ORANGE);
                break;
            case 6:
                helperChangeBackground(117, 60, 143, Color.PURPLE);
                break;
            case 8:
                helperChangeBackground(100, 180, 96, Color.GREEN);
                break;
        }
    }

    private void helperChangeBackground(int r, int g, int b, Color backgroundColor) {
        /* Helps to change background according to the given color
         * Used in - flagFilterBackground() */
        Gdx.gl.glClearColor(r / 255f, g / 255f, b / 255f, 1); //
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameWorld.getBall().setBackgroundColor(backgroundColor);
    }

}
