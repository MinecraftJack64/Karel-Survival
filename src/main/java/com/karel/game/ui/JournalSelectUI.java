package com.karel.game.ui;

import com.karel.game.ui.buttons.Button;
import com.karel.game.ui.sliders.Slider;
import com.raylib.Raylib;
import com.karel.game.Game;

public class JournalSelectUI extends UI{
    Button backButton, zombieButton, weaponButton, karelButton, mapButton;
    public JournalSelectUI() {
        backButton = new Button(50, 50, "Back", Raylib.GREEN){
            public void click() {
                Game.goToMenu();
            }
        };
        zombieButton = new Button(150, 150, "Zombie", Raylib.GREEN);
        weaponButton = new Button(150, 150, "Weapon", Raylib.GREEN);
        karelButton = new Button(150, 150, "Karel", Raylib.GREEN);
        mapButton = new Button(150, 150, "Map", Raylib.GREEN);
        resetBG();
    }
    public void create(){
        getWorld().addToGrid(backButton, 1, 1);
        getWorld().addToGrid(zombieButton, 8, 4);
        getWorld().addToGrid(weaponButton, 15, 4);
        getWorld().addToGrid(karelButton, 8, 11);
        getWorld().addToGrid(mapButton, 15, 11);
        getWorld().addToGrid(new Slider(100, 100, 5, 20, false), 2, 2);
    }
}
