package com.karel.game.ui;

import java.util.ArrayList;
import com.karel.game.Game;
import com.karel.game.ZombieFactory;
import com.karel.game.gamemodes.sandbox.Sandbox;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.ui.buttons.Button;
import com.karel.game.ui.buttons.ImageButton;
import com.raylib.Raylib;

public class SandboxUI extends UI {
    Button backButton;
    ArrayList<ImageButton> zombieButtons;
    Sandbox mode;
    public SandboxUI() {
        backButton = new Button(50, 50, "Back", Raylib.GREEN){
            public void click() {
                Game.goToMenu();
            }
        };
        backButton.setActive(false);
        String[] zombieIDs = ZombieFactory.getZombieTypes();
        zombieButtons = new ArrayList<ImageButton>();
        for(int i = 0; i < zombieIDs.length; i++){
            Zombie z = ZombieFactory.createZombie(zombieIDs[i]);
            System.out.println(z.getImageURL());
            int id = i;
            ImageButton zombieButton = new ImageButton(50, 50, z.getImageURL(), z.getName()){
                public void click() {
                    zombieButtons.get(mode.getSelectedZombie()).setActive(true);
                    setActive(false);
                    mode.setSelectedZombie(id, zombieIDs[id]);
                }
            };
            zombieButtons.add(zombieButton);
        }
        resetBG();
    }
    public void create(){
        mode = (Sandbox)Game.getGame();
        getWorld().addToGrid(backButton, 1, 1);
        int row = 0;
        for(int i = 0; i < zombieButtons.size(); i++){
            getWorld().addToGrid(zombieButtons.get(i), 1+i%(getWorld().gridwidth-2), 14-row);
            if((getWorld().gridwidth-2)==(1+i%(getWorld().gridwidth-2))){
                row++;
            }
        }
    }
}
