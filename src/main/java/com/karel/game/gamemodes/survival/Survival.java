package com.karel.game.gamemodes.survival;
import com.karel.game.Game;
import com.karel.game.Player;
import com.karel.game.Spawner;
import com.karel.game.Teams;
import com.karel.game.ZombieSpawner;
import com.karel.game.gamemodes.GameMode;

/**
 * Write a description of class Survival here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Survival extends GameMode
{
    private Player player;
    private Teams teams;
    public ZombieSpawner spawner;
    private String status;
    private static int highscore;
    /**
     * Constructor for objects of class Survival
     */
    public Survival()
    {
    }
    public void tick(){
        spawner.checkSpawn();
        Game.gameUI().waveCounter.setValue(spawner.wavelevel);
        if(player.isDead()){
            gameOver();
        }
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
        teams.addTeam("god");
        teams.setAggro("god", "zombie", false);
        teams.setAggro("god", "player", false);
        teams.setAggro("god", "lootbox", false);
        Player rocket = new Player();
        getWorld().addToGrid(rocket, 12, 8);
        player = rocket;
        spawner = new ZombieSpawner();
        spawner.spawnZombies(1, 1);
        status = "running";
        resetScore();
        System.out.println(teams);
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
    public void gameOver() 
    {
        boolean beaths = getScore()>highscore;
        if(beaths){
            highscore = getScore();
        }
        gameUI().gameOver(beaths);
        status = "lose";
    }
}
