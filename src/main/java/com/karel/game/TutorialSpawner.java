package com.karel.game;
import java.util.ArrayList;

/**
 * Write a description of class AdventureSpawner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TutorialSpawner implements Spawner
{
    public boolean firstKill = false;
    public int cwavecooldown = 200;
    public GridEntity boss;
    public World myWorld = Game.world;
    public LevelRunner runner;
    public ArrayList<Class> spawnTypes;
    public ArrayList<Integer> spawnCount;
    public ArrayList<GridEntity> currentlySpawned = new ArrayList<GridEntity>();
    public int level;
    private boolean complete = false;
    Zombie myz;
    public TutorialSpawner(){
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
        if(myz==null||myz.isDead()){
            if(myz!=null&&myz.isDead()){
                firstKill = true;
            }
            myz = new ShieldZombie();
            spawnZombie(myz);
        }
    }
    public void spawnZombies(SpawnData dat)
    {
        spawnTypes = dat.spawnTypes;
        spawnCount = dat.spawnCount;//graphics, sound, spawner, tutorial
        currentlySpawned = new ArrayList<GridEntity>();
        for(int i = 0; i < spawnTypes.size(); i++){
            for(int f = 0; f < spawnCount.get(i); f++){
                try{GridEntity toSpawn = (GridEntity)(spawnTypes.get(i).getDeclaredConstructor().newInstance());
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
    public boolean levelComplete(){
        return complete;
    }
    public boolean firstKill(){
        return firstKill;
    }
}