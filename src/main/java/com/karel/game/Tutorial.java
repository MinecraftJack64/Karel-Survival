package com.karel.game;

import com.karel.game.weapons.Weapon;
import com.karel.game.weapons.gun.Gun;

/**
 * Write a description of class Adventure here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tutorial extends GameMode
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
    public Tutorial()
    {
    }
    public void tick(){
        if(tutorialphase>0)spawner.checkSpawn();
        if(tutorialphase==0){
            gameUI().setTutorial("Welcome to the Adventure, Karel");
            gameUI().setTutorial2("Use Arrow keys or WASD to move");
            if(getPlayer().getRealX()!=playerogx||getPlayer().getRealY()!=playerogy){
                tutorialphase++;
            }
        }else if(tutorialphase==1){
            gameUI().setTutorial("Aim with your mouse");
            gameUI().setTutorial2("Left click to attack");
            if(Game.isAttackDown()){
                tutorialphase++;
            }
        }else if(tutorialphase==2){
            gameUI().setTutorial("Aim at the zombie and attack it");
            gameUI().setTutorial2("");
            if(spawner.firstKill()){
                tutorialphase++;
            }
        }else if(tutorialphase==3){
            gameUI().setTutorial("");
            gameUI().setTutorial2("");
            if(((Weapon)(getPlayer().getHeldItem())).isUltReady()){
                tutorialphase++;
            }
        }else if(tutorialphase==4){
            gameUI().setTutorial("Your ult ability charges from dealing damage");
            gameUI().setTutorial2("Press space to use it");
            if(!((Weapon)(getPlayer().getHeldItem())).isUltReady()){
                tutorialphase++;
                SupplyCrate s = new SupplyCrate(new WeaponFrag());
                getWorld().addToGrid(s, 12, 8);
                SupplyCrate s1 = new SupplyCrate(new WeaponFrag());
                getWorld().addToGrid(s1, 8, 8);
                SupplyCrate s2 = new SupplyCrate(new WeaponFrag());
                getWorld().addToGrid(s2, 16, 8);
            }
        }else if(tutorialphase==5){
            gameUI().setTutorial("Collect weapon fragments from the loot crates");
            gameUI().setTutorial2("Press e to craft a weapon when you have at least 2");
            if(!(getPlayer().getHeldItem() instanceof Gun)){
                tutorialphase++;
            }
        }else if(tutorialphase==6){
            gameUI().setTutorial("You successfully crafted a new weapon!");
            gameUI().setTutorial2("Press x or c to switch weapons");
            if(getPlayer().getHeldItem() instanceof Gun){
                tutorialphase++;
            }
        }else{
            gameUI().setTutorial("");
            gameUI().setTutorial2("");
            tutorialcomplete = true;
        }
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
        playerogx = rocket.getRealX();
        playerogy = rocket.getRealY();
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
