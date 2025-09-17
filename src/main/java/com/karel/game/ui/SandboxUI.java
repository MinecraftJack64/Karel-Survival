package com.karel.game.ui;

import java.util.ArrayList;
import com.karel.game.Game;
import com.karel.game.Greenfoot;
import com.karel.game.gamemodes.sandbox.Sandbox;
import com.karel.game.gridobjects.gridentities.zombies.ZombieFactory;
import com.karel.game.GridEntity;
import com.karel.game.ItemFactory;
import com.karel.game.ui.buttons.Button;
import com.karel.game.ui.buttons.ImageButton;
import com.karel.game.ui.text.TextDisplay;
import com.raylib.Raylib;
import com.raylib.Texture;

public class SandboxUI extends UI {
    Button backButton;
    Button stepWorldButton;
    TextDisplay teamLabel;
    ArrayList<Button> modeButtons;
    ArrayList<ImageButton> zombieButtons;
    ArrayList<ImageButton> weaponButtons;
    ArrayList<Button> teamButtons;
    Sandbox mode;
    public SandboxUI() {
        backButton = new Button(50, 50, "Back", Raylib.GREEN){
            public void click() {
                Game.goToMenu();
            }
        };
        backButton.setActive(false);
        stepWorldButton = new Button(50, 50, "Step", Raylib.ORANGE){
            public void click(){
                Game.getGame().getWorld().doUpdate();
            }
        };
        stepWorldButton.setActive(false);
        String[] zombieIDs = ZombieFactory.getZombieTypes();
        zombieButtons = new ArrayList<ImageButton>();
        for(int i = 0; i < zombieIDs.length; i++){
            GridEntity z = ZombieFactory.createZombie(zombieIDs[i]);
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
        String[] weaponIDs = ItemFactory.getItemTypes();
        weaponButtons = new ArrayList<ImageButton>();
        for(int i = 0; i < weaponIDs.length; i++){
            String textureURL = "Weapons/blade/icon.png";
            try{
                Texture texture = Greenfoot.loadTexture("Weapons/"+weaponIDs[i]+"/icon.png");
                texture.getClass();
                textureURL = "Weapons/"+weaponIDs[i]+"/icon.png";
            }catch(Exception e){
                System.out.println("Failed to load weapon texture: " + weaponIDs[i]);
            }
            int id = i;
            ImageButton weaponButton = new ImageButton(50, 50, textureURL, weaponIDs[i]/*TODO w.getName()*/){
                public void click() {
                    weaponButtons.get(mode.getSelectedWeapon()).setActive(true);
                    setActive(false);
                    mode.setSelectedWeapon(id, weaponIDs[id]);
                }
            };
            weaponButtons.add(weaponButton);
        }
        modeButtons = new ArrayList<Button>();
        String[] modeNames = {"None", "Damage", "Heal", "Move", "Teleport", "Give Weapon", "Give Effect", "Apply Team", "Summon"};
        for(int i = 0; i < modeNames.length; i++){
            int i2 = i;
            Button modeButton = new Button(50, 50, modeNames[i], Raylib.GREEN){
                public void click() {
                    modeButtons.get(mode.getSelectedMode()).setActive(true);
                    exitMode(mode.getSelectedMode());
                    setActive(false);
                    mode.setSelectedMode(i2);
                    enterMode(i2);
                }
            };
            modeButtons.add(modeButton);
        }
        modeButtons.get(0).setActive(false);
        teamLabel = new TextDisplay("Teams", 10);
    }
    public void create(){
        mode = (Sandbox)Game.getGame();
        ArrayList<String> teamIDs = mode.getTeams().getAllTeams();
        teamButtons = new ArrayList<Button>();
        for(int i = 0; i < teamIDs.size(); i++){
            String id = teamIDs.get(i);
            int i2 = i;
            Button teamButton = new Button(50, 50, id, Raylib.GREEN){
                public void click() {
                    teamButtons.get(mode.getSelectedTeam()).setActive(true);
                    setActive(false);
                    mode.setSelectedTeam(i2, id);
                }
            };
            teamButtons.add(teamButton);
        }

        getWorld().addToGrid(backButton, 1, 1);

        getWorld().addToGrid(stepWorldButton, 3, 1);
        //Create team options
        getWorld().addToGrid(teamLabel, getWorld().gridwidth-1, 2);
        for(int i = 0; i < teamButtons.size(); i++){
            getWorld().addToGrid(teamButtons.get(i), getWorld().gridwidth-1, 3+i);
        }
        //Create mode options
        for(int i = 0; i < modeButtons.size(); i++){
            getWorld().addToGrid(modeButtons.get(i), 1+i, 15);
        }
        teamButtons.get(0).click();
        zombieButtons.get(0).click();
    }
    public void exitMode(int mode){
        if(mode==8){
            removeSummonOptions();
        }else if(mode==5){
            removeWeaponOptions();
        }
    }
    public void enterMode(int mode){
        if(mode==8){
            addSummonOptions();
        }else if(mode==5){
            addWeaponOptions();
        }
    }
    public void addSummonOptions(){
        //Create zombie options
        int row = 0;
        int baserow = zombieButtons.size()/(getWorld().gridwidth-2);
        for(int i = 0; i < zombieButtons.size(); i++){
            getWorld().addToGrid(zombieButtons.get(i), 1+i%(getWorld().gridwidth-2), 14-baserow+row);
            if((getWorld().gridwidth-2)==(1+i%(getWorld().gridwidth-2))){
                row++;
            }
        }
    }
    public void removeSummonOptions(){
        for(int i = 0; i < zombieButtons.size(); i++){
            getWorld().removeObject(zombieButtons.get(i));
        }
    }
    public void addWeaponOptions(){
        int row = 0;
        int baserow = weaponButtons.size()/(getWorld().gridwidth-2);
        for(int i = 0; i < weaponButtons.size(); i++){
            getWorld().addToGrid(weaponButtons.get(i), 1+i%(getWorld().gridwidth-2), 14-baserow+row);
            if((getWorld().gridwidth-2)==(1+i%(getWorld().gridwidth-2))){
                row++;
            }
        }
    }
    public void removeWeaponOptions(){
        for(int i = 0; i < weaponButtons.size(); i++){
            getWorld().removeObject(weaponButtons.get(i));
        }
    }
    public void setBackButtonActive(boolean active){
        backButton.setActive(active);
        stepWorldButton.setActive(active);
    }
}
