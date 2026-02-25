package com.karel.game.spawndata;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import com.karel.game.Greenfoot;
import com.karel.game.QueueMap;
import com.karel.game.gamemodes.SpawnData;
import com.karel.game.gridobjects.gridentities.zombies.Boss;
import com.karel.game.gridobjects.gridentities.zombies.ZombieFactory;
/**
 * Write a description of class ZombieSpawner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SpawnCalculator
{
    public String[] spawnables = new String[]{"shield"};
    public int[] minlevels = new int[]{1};//18 is doctor, 49 is JITB, 71 - boid zombie
    public int[] maxnums = new int[]{8};
    public QueueMap<Integer, Boss> bosses;
    public ArrayList<Integer> canSpawn = new ArrayList<Integer>(), forceSpawn = new ArrayList<Integer>();
    public ArrayList<Integer> spawnTypes = new ArrayList<Integer>(), spawnCount = new ArrayList<Integer>();
    public SpawnCalculator(){
        ArrayList<String> types = new ArrayList<String>();
        ArrayList<Integer> levels = new ArrayList<Integer>();
        ArrayList<Integer> nums = new ArrayList<Integer>();
        try{
            try (Scanner sc = new Scanner(new File("src/main/java/com/karel/game/spawndata/survival.dat"))) {
                while(sc.hasNext()){
                    String type = sc.next();
                    if(type.equals("boss")){
                        type = sc.next();
                        int wave = sc.nextInt();
                        if(bosses==null)bosses = new QueueMap<Integer, Boss>();
                        bosses.add(wave, (Boss)ZombieFactory.createZombie(type));
                    }else{
                        int level = sc.nextInt();
                        int num = sc.nextInt();
                        types.add(type);
                        levels.add(level);
                        nums.add(num);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        spawnables = types.stream().toArray(String[]::new);
        minlevels = levels.stream().mapToInt(Integer::intValue).toArray();
        maxnums = nums.stream().mapToInt(Integer::intValue).toArray();
    }
    public SpawnData calculateSpawn(int count, int wave)
    {
        System.out.println(count+" "+wave);
        resetSpawnableZombies(wave);
        int nc = selectSpawnableZombies(count);
        calculateSpawnCounts(nc, wave);
        ArrayList<String> tres = new ArrayList<String>();
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
        if(max== 1)return 1;
        int c = max-1;
        while(c>=1&&Greenfoot.getRandomNumber(c)<c-1){
            c--;
        }
        return c;
    }
}
