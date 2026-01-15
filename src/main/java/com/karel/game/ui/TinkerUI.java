package com.karel.game.ui;

import com.karel.game.ui.buttons.Button;
import com.karel.game.ui.sliders.Slider;
import com.raylib.Raylib;
import com.karel.game.Game;

public class TinkerUI extends UI{
    Button backButton, tinkerButton;
    Slider fragNumPicker;
    public TinkerUI() {
        backButton = new Button(50, 50, "Back", Raylib.GREEN){
            public void click() {
                Game.goToMenu();
            }
        };
        tinkerButton = new Button(250, 250, "!! TINKER !!", Raylib.GREEN);
        if(Game.playerData.getNumFrags()>=100)fragNumPicker = new Slider(50*18, Game.playerData.getNumFrags()/10*10, 100, 100, 10, false);
        resetBG();
    }
    public void create(){
        getWorld().addToGrid(backButton, 1, 1);
        getWorld().addToGrid(tinkerButton, 11.5, 7.5);
        getWorld().addToGrid(fragNumPicker, 11.5, 14);
    }
}
