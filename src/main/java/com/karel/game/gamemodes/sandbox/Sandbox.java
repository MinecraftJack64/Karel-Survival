package com.karel.game.gamemodes.sandbox;

import com.karel.game.Game;
import com.karel.game.KActor;
import com.karel.game.Player;
import com.karel.game.Spawner;
import com.karel.game.Teams;
import com.karel.game.gamemodes.GameMode;
import com.karel.game.weapons.sudo.Sudo;
import com.karel.game.ui.Overlay;
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
    private Sudo sudo;
    private Teams teams;
    private String status;
    private static int highscore;
    private int respawncooldown;
    private SandboxUI ui;
    private String selectedZombieID;
    private String selectedTeamID;
    private String selectedWeaponID;
    private int selectedZombie;
    private int selectedTeam;
    private int selectedMode;
    private int selectedWeapon;
    /**
     * Constructor for objects of class Sandbox
     */
    public Sandbox()
    {
        ui = new SandboxUI();
    }
    public void tick(){
        if(player.isDead()){
            respawncooldown--;
            if(respawncooldown<=0){
                player.reviveWithHealth();
                respawncooldown = 60;
            }
        }
        /*Game.setUIScreenScaleAndOffset(ui);
        ui.update();
        ui.render();*/
        if(getWorld().isPaused()){//TODO use better way
            player.update();
            for(KActor k: getWorld().allKActors()){
                if(k instanceof Overlay){
                    k.update();
                }
            }
            getWorld().processRemovals();
        }
    }
    public void startGame(){
        teams = new Teams();
        defaultTeams();
        Player rocket = new Player();
        getWorld().addToGrid(rocket, 12, 8);
        player = rocket;
        sudo = new Sudo(player.getHand());
        player.giveSudo(sudo);
        status = "running";
        resetScore();
        ui.create();
        Game.ui2 = ui;
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
        sudo.setZombie(id);
    }
    public void setSelectedTeam(int selectedTeam, String id){
        this.selectedTeam = selectedTeam;
        this.selectedTeamID = id;
        sudo.setTeam(id);
    }
    public int getSelectedTeam(){
        return selectedTeam;
    }
    public String getSelectedTeamID(){
        return selectedTeamID;
    }
    public int getSelectedMode(){
        return selectedMode;
    }
    public void setSelectedMode(int selectedMode){
        this.selectedMode = selectedMode;
        if(selectedMode>0){
            sudo.setMode(selectedMode);
            if(!player.sudoActive())player.setSudoActive(true);
        }else{
            sudo.setMode(0);
            if(player.sudoActive())player.setSudoActive(false);
        }
    }
    public int getSelectedWeapon(){
        return selectedWeapon;
    }
    public String getSelectedWeaponID(){
        return selectedWeaponID;
    }
    public void setSelectedWeapon(int selectedWeapon, String id){
        this.selectedWeapon = selectedWeapon;
        this.selectedWeaponID = id;
        sudo.setWeapon(id);
    }
    public Teams getTeams(){
        return teams;
    }
    public Spawner getSpawner(){
        return null;
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
    @Override
    public boolean usesCustomPause(){
        return true;
    }
    public void togglePause(){
        getWorld().togglePause();
        if(getWorld().isPaused()){
            ui.setBackButtonActive(true);
        }else{
            ui.setBackButtonActive(false);
        }
    }
    public boolean showPauseMenu(){
        return true;
    }
}
