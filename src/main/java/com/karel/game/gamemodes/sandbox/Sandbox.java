package com.karel.game.gamemodes.sandbox;

import com.karel.game.Game;
import com.karel.game.GameMode;
import com.karel.game.Player;
import com.karel.game.Spawner;
import com.karel.game.Teams;
import com.karel.game.ZombieSpawner;
import com.karel.game.ui.SandboxUI;
/**
 * Write a description of class Protect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sandbox extends GameMode
{
    private Player player;
    private Teams teams;
    public ZombieSpawner spawner;
    private String status;
    private static int highscore;
    private int respawncooldown;
    private SandboxUI ui;
    private String selectedZombieID;
    private int selectedZombie;
    /**
     * Constructor for objects of class Sandbox
     */
    public Sandbox()
    {
        ui = new SandboxUI();
    }
    public void tick(){
        getSpawner().checkSpawn();
        if(player.isDead()){
            respawncooldown--;
            if(respawncooldown<=0){
                Player np = new Player();
                getWorld().addObject(np, player.getX(), player.getY());
                np.setRotation(player.getRotation());
                getWorld().removeObject(player);
                player = np;
                respawncooldown = 60;
            }
        }
        Game.setUIScreenScaleAndOffset(ui);
        ui.update();
        ui.render();
    }
    public void startGame(){
        teams = new Teams();
        teams.addTeam("player");
        teams.addTeam("zombie");
        teams.addTeam("lootbox");
        teams.setAggro("player", "zombie", true);
        teams.setAggro("player", "lootbox", false);
        teams.setAlly("zombie", "zombie", false);
        teams.setAlly("player", "player", false);
        Player rocket = new Player();
        getWorld().addToGrid(rocket, 12, 8);
        player = rocket;
        spawner = new ZombieSpawner();
        spawner.spawnZombies(1, 1);
        status = "running";
        resetScore();
        ui.create();
        System.out.println(teams);
        respawncooldown = 60;
    }
    public int getSelectedZombie(){
        return selectedZombie;
    }
    public String getSelectedZombieID(){
        return selectedZombieID;
    }
    public void setSelectedZombie(int selectedZombie, String id){
        this.selectedZombie = selectedZombie;
        this.selectedZombieID = id;
    }
    public Teams getTeams(){
        return teams;
    }
    public Spawner getSpawner(){
        return spawner;
    }
    public Player getPlayer(){
        return player;
    }
    public String getStatus(){
        return status;
    }
    public int getHighScore(){
        return highscore;
    }
}
