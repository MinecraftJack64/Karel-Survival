package com.karel.game.gamemodes.adventure;
import java.util.ArrayList;

import com.karel.game.Game;
import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Levels;
import com.karel.game.Levels.LevelRunner;
import com.karel.game.gamemodes.SpawnData;
import com.karel.game.gridobjects.gridentities.zombies.ZombieFactory;
import com.karel.game.gridobjects.gridentities.zombies.herald.ZombieHerald;
import com.karel.game.gridobjects.gridentities.zombies.wizard.Wizard;
import com.karel.game.Spawner;
import com.karel.game.World;

/**
 * Write a description of class AdventureSpawner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AdventureSpawner implements Spawner
{
    //private int bossphase = 0;
    //private boolean bossfight;
    public int cwavecooldown = 200;
    public GridEntity boss;
    public LevelRunner runner;
    private World myWorld = Game.world;
    public ArrayList<String> spawnTypes;
    public ArrayList<Integer> spawnCount;
    public ArrayList<GridEntity> currentlySpawned = new ArrayList<GridEntity>();
    public int level;
    private boolean complete = false;
    public AdventureSpawner(int startinglevel){
        runner = Levels.getLevelRunner(startinglevel);
        level = startinglevel;
    }
    public boolean isBossWave(){
        return false;
    }
    public int remaining(){
        int t = 0;
        for(GridEntity g:currentlySpawned){
            if(!g.isDead()){
                t++;
            }
        }
        return t;
    }
    public void checkSpawn(){
        if(runner.getNumRemainingWaves()<=0){
            if(remaining()==0)complete = true;
        }else
        if(runner.canSpawnNextWave(remaining(), currentlySpawned.size())){
            spawnZombies(runner.nextWave());
        }
    }
    public void spawnZombies(SpawnData dat)
    {
        spawnTypes = dat.spawnTypes;
        spawnCount = dat.spawnCount;//graphics, sound, spawner, tutorial
        currentlySpawned = new ArrayList<GridEntity>();
        for(int i = 0; i < spawnTypes.size(); i++){
            for(int f = 0; f < spawnCount.get(i); f++){
                try{GridEntity toSpawn = ZombieFactory.createZombie(spawnTypes.get(i));
                spawnZombie(toSpawn);currentlySpawned.add(toSpawn);}catch(Exception e){e.printStackTrace();}
            }
        }
        System.out.println("Spawning "+currentlySpawned.size()+" zombies");
    }

    public void spawnZombie(GridObject toSpawn){
        int x = Greenfoot.getRandomNumber(myWorld.gridwidth+1);
        int y = Greenfoot.getRandomNumber(myWorld.gridheight+1);
        switch(Greenfoot.getRandomNumber(4)){
            case 0:
                x = -1;
                break;
            case 1:
                x = myWorld.gridwidth;
                break;
            case 2:
                y = -1;
                break;
            case 3:
                y = myWorld.gridheight;
                break;
        }
        myWorld.addToGrid(toSpawn, x, y);
    }
    public void spawnZombieRandom(GridObject toSpawn){
        int x = Greenfoot.getRandomNumber(myWorld.gridwidth);
        int y = Greenfoot.getRandomNumber(myWorld.gridheight);
        myWorld.addToGrid(toSpawn, x, y);
    }
    public void spawnZombie(GridEntity toSpawn, int x, int y){
        myWorld.addToGrid(toSpawn, x, y);
    }
    public void heraldBossFight(){
        spawnZombie(new ZombieHerald(), myWorld.gridwidth/2, myWorld.gridheight/2);
    }
    public void startBossFight(){
        Wizard wizzie = new Wizard();
        spawnZombie(wizzie, myWorld.gridwidth/2, myWorld.gridheight/2);
        //bossphase = 1;
        boss = wizzie;
        //bossfight = true;
        myWorld.bossBG();
    }
    public void stopBossFight(){
        //bossfight = false;
        //bossphase = 0;
        myWorld.resetBG();
        boss = null;
    }
    public void setBossPhase(int p){
        //bossphase = p;
    }
    public boolean levelComplete(){
        return complete;
    }
}
