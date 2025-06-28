package com.karel.game;
/**
 * Write a description of class Protect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Protect extends GameMode
{
    private Player player;
    private Baby baby;
    private Teams teams;
    public ZombieSpawner spawner;
    private String status;
    private static int highscore;
    private int respawncooldown;
    /**
     * Constructor for objects of class Protect
     */
    public Protect()
    {
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
        if(baby.isDead()){
            player.die(null);
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
        Player rocket = new Player();
        getWorld().addToGrid(rocket, 12, 8);
        Baby obj = new Baby();
        getWorld().addToGrid(obj, 12, 8);
        baby = obj;
        player = rocket;
        spawner = new ZombieSpawner();
        spawner.spawnZombies(1, 1);
        status = "running";
        resetScore();
        System.out.println(teams);
        respawncooldown = 60;
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
    public Baby getBaby(){
        return baby;
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
