package com.karel.game;
/**
 * Write a description of class Adventure here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sandbox extends GameMode
{
    private Player player;
    private Teams teams;
    public TutorialSpawner spawner;
    private String status;
    private static int highscore;
    private static int level = 0;
    private boolean tutorialcomplete = false;
    private int tutorialphase = 0;
    private double playerogx, playerogy;
    /**
     * Constructor for objects of class Adventure
     */
    public Sandbox()
    {
    }
    public void tick(){
        //
        if(player.isDead()||tutorialcomplete){
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
        playerogx = rocket.getX();
        playerogy = rocket.getY();
        player = rocket;
        spawner = new TutorialSpawner();
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
        return 0;
    }
    public void gameOver() 
    {
        if(tutorialcomplete){
            status = "win";
            gameUI().gameOver("tutorial", true);
            level++;
        }else{
            gameUI().gameOver("tutorial", false);
            status = "lose";
        }
    }
}
