package com.karel.game.gamemodes;

import com.karel.game.Game;
import com.karel.game.ItemFactory;
import com.karel.game.Player;
import com.karel.game.Sounds;
import com.karel.game.Spawner;
import com.karel.game.Teams;
import com.karel.game.Tickable;
import com.karel.game.WeaponsOld;
import com.karel.game.World;
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
    public void defaultTeams(){
        Teams teams = getTeams();
        teams.addTeam("player");
        teams.addTeam("zombie");
        teams.addTeam("lootbox");
        teams.addTeam("punchingbag");
        teams.setAggro("player", "zombie", true);
        teams.setAggro("player", "lootbox", false);
        teams.setAlly("zombie", "zombie", false);
        teams.setAlly("player", "player", false);
        teams.setAggro("player", "punchingbag", false);
    }
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
        getWorld().allEntities().clear();
        getWorld().allGridObjects.clear();
    }
    /*
    * return true if game should quit as normal
    */
    public boolean quit(){
        return true;
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
        String cls = WeaponsOld.attemptWeaponCrafting(weaponfrags, getPlayer().getInventory());
        if(cls!=null){
            try{return (Weapon)(ItemFactory.createItem(cls, getPlayer().getHand()));}catch(Exception e){e.printStackTrace();return null;}
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
    public boolean usesCustomPause(){
        return false;
    }
    public void togglePause(){}
    public boolean showPauseMenu(){
        return false;
    }

}
