package com.karel.game;
import java.util.ArrayList;

import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.doctor.DoctorZombie;
import com.karel.game.gridobjects.gridentities.zombies.firebreather.FirebreatherZombie;
import com.karel.game.gridobjects.gridentities.zombies.fungal.FungalZombie;
import com.karel.game.gridobjects.gridentities.zombies.hivemind.HivemindZombie;
import com.karel.game.gridobjects.gridentities.zombies.laser.LaserZombie;
import com.karel.game.gridobjects.gridentities.zombies.rocket.RocketZombie;
import com.karel.game.gridobjects.gridentities.zombies.shooter.ShooterZombie;
import com.karel.game.gridobjects.gridentities.zombies.weedwacker.WeedwackerZombie;

class SpawnData{
    public ArrayList<Class> spawnTypes;
    public ArrayList<GridEntity> spawn;
    public ArrayList<Integer> spawnCount;
    public SpawnData(ArrayList<Class> types, ArrayList<Integer> count){
        spawnTypes = types;
        spawnCount = count;
        initializeSpawn();
    }
    public void initializeSpawn(){
        spawn = new ArrayList<GridEntity>();
        for(Class cls: spawnTypes){
            try{spawn.add((GridEntity)cls.newInstance());}catch(Exception e){e.printStackTrace();spawn.add(new Zombie());}
        }
    }
    public boolean isClear(){
        for(int i: spawnCount){
            if(i>0)return false;
        }
        return true;
    }
    public int size(){
        return spawnCount.size();
    }
    public int numSpawns(){
        int i = 0;
        for(int c: spawnCount){
            i+=c;
        }
        return i;
    }
    public int totalHealth(){
        int i = 0;
        for(GridEntity c: spawn){
            i+=c.getHealth();
        }
        return i;
    }
    public int count(int i){
        return spawnCount.get(i);
    }
    public GridEntity pop(int id){
        int val = spawnCount.get(id);
        if(val>0)spawnCount.set(id, val-1);
        return spawn.get(id);
    }
}
/**
 * Write a description of class ZombieSpawner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SpawnCalculator
{
    public Class[] spawnables = new Class[]{
        Zombie.class, ShieldZombie.class, ShooterZombie.class, ExplodingZombie.class, HardHatZombie.class, HivemindZombie.class,
        MarksmanZombie.class, RocketZombie.class, FungalZombie.class, NinjaZombie.class, DoctorZombie.class, HornetNeckZombie.class,
        LaserZombie.class, EasterZombie.class, RussianDollZombie.class, WeedwackerZombie.class, SplitterZombie.class, /*Shaman*/Zombie.class,
        TractorBeamZombie.class, WarriorZombie.class, FirebreatherZombie.class, PresidentZombie.class, JokerZombie.class, JackITBZombie.class, CannonZombie.class, StuntZombie.class, JailBreakZombie.class,
        PortalZombie.class, WatermelonZombie.class, ArsonZombie.class, AssassinZombie.class, IroncladZombie.class, SteakZombie.class, ExorcistZombie.class, GuardianAngelZombie.class,
        TorpedoZombie.class};
    public int[] minlevels = new int[]{1, 3, 5, 7, 8, 9, 10, 13, 15, 17, 18, 19, 21, 23, 25, 27, 32, 33, 35, 39, 45, 47, 49, 49, 51, 53, 54, 55, 56, 58, 61, 63, 65, 67, 68, 69};//18 is doctor, 49 is JITB
    public int[] maxnums = new int[]{8, 5, 3, 4, 2, 3, 2, 3, 2, 2, 4, 1, 1, 2, 2, 2, 3, 1, 3, 2, 1, 3, 3, 1, 4, 4, 2, 2, 2, 1, 2, 2, 5, 3, 3, 2};
    public ArrayList<Integer> canSpawn = new ArrayList<Integer>(), forceSpawn = new ArrayList<Integer>();
    public ArrayList<Integer> spawnTypes = new ArrayList<Integer>(), spawnCount = new ArrayList<Integer>();
    public SpawnData calculateSpawn(int count, int wave)
    {
        resetSpawnableZombies(wave);
        int nc = selectSpawnableZombies(count);
        calculateSpawnCounts(nc, wave);
        ArrayList<Class> tres = new ArrayList<Class>();
        for(int type: spawnTypes){
            tres.add(spawnables[type]);
        }
        return new SpawnData(tres, spawnCount);
    }
    
    public void resetSpawnableZombies(int wave){
        canSpawn.clear();
        forceSpawn.clear();
        for(int i = 0; i < spawnables.length; i++){
            if(minlevels[i]<wave){ // all possible zombies that can be spawned
                canSpawn.add(i);
            }else if(minlevels[i]==wave){ // when a zombie debuts in a wave, at least one has to be spawned
                forceSpawn.add(i);
            }
        }
    }
    public int selectSpawnableZombies(int count){
        spawnTypes.clear();
        spawnCount.clear();
        // make sure at least one of each forced spawn spawns
        for(Integer i: forceSpawn){
            spawnTypes.add(i);
            spawnCount.add(1);
        }
        count-=forceSpawn.size();
        if(count<=0){
            return count;
        }
        for(int i = 0; i < randomDescend(count); i++){
            if(canSpawn.size()==0){
                break;
            }
            spawnTypes.add(canSpawn.remove(Greenfoot.getRandomNumber(canSpawn.size())));
            spawnCount.add(1);
            count--;
        }
        return count;
    }
    public void calculateSpawnCounts(int count, int wave){
        ArrayList<Integer> incstoselect = new ArrayList<Integer>();
        while(count>0){
            // choose spawns to increase
            for(int i = 0; i < spawnTypes.size(); i++){
                if(spawnCount.get(i)<getMaxOf(spawnTypes.get(i), wave)){
                    incstoselect.add(i);
                }
            }
            if(incstoselect.size()==0){
                return;
            }
            //select some to increase
            for(int i: incstoselect){
                if(count<=0)break;
                spawnCount.set(i, spawnCount.get(i)+1);
                count--;
            }
            incstoselect.clear();
        }
    }
    public int getMaxOf(int type, int wave){
        return Math.min(maxnums[type], wave+1-minlevels[type]);
    }
    // for some reason, returns a random number from 1-max
    public static int randomDescend(int max){
        int c = max-1;
        while(c>=1&&Greenfoot.getRandomNumber(c)<c-1){
            c--;
        }
        return c;
    }
}
