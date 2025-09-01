package com.karel.game;

import com.karel.game.ui.GameUI;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class GameMode here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class GameMode implements Tickable  
{
    private GameUI myui;
    private int score;
    private int weaponfrags;
    private int beeperbagsize = 10;
    public abstract Player getPlayer();
    public void resetScore(){
        score = 0;
        weaponfrags = 0;
    }
    public int getScore(){
        return score;
    }
    public abstract int getHighScore();
    public abstract void startGame();
    public abstract Teams getTeams();
    public abstract Spawner getSpawner();
    public World getWorld(){
        return Game.world;
    }
    public void setUI(GameUI ui){
        myui = ui;
    }
    public String getStatus(){
        return "inactive";
    }
    public GameUI gameUI(){
        return myui;
    }
    public void stopGame(){
        //Remove player and zombies
        getWorld().allEntities().clear();
        getWorld().allGridObjects.clear();
        //Game.removeUI();
    }
    public void increaseScore(int amt){
        score+=amt;
        gameUI().increaseScore(score);
    }
    public void collectWeaponFrag(){
        if(!beeperbagfull())
        weaponfrags++;
        gameUI().showWeaponFrags(weaponfrags);
    }
    public boolean beeperbagfull(){
        return weaponfrags>=beeperbagsize;
    }
    public Weapon getCraft(){
        Class cls = Weapons.attemptWeaponCrafting(weaponfrags, getPlayer().getInventory());
        if(cls!=null){
            try{return (Weapon)(cls.getConstructors()[0].newInstance(getPlayer()));}catch(Exception e){e.printStackTrace();return null;}
        }else{
            return null;
        }
    }
    public void craftWeapon(){
        if(weaponfrags<2){
            Sounds.play("emptycraft");
            gameUI().showWeaponFragsFailed(weaponfrags);
            return;
        }
        Weapon w = getCraft();
        weaponfrags = 0;
        if(w!=null){
            getPlayer().acceptItem(w);
            Sounds.play("craft");
        }else{
            Sounds.play("craftfailed");
        }
        gameUI().showWeaponFrags(w, weaponfrags);
    }
}
