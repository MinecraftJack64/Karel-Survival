package com.karel.game.gamemodes;
import com.karel.game.GridEntity;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieFactory;

import java.util.ArrayList;
public class SpawnData{
    public ArrayList<String> spawnTypes;
    public ArrayList<GridEntity> spawn;// do not use
    public ArrayList<Integer> spawnCount;
    public SpawnData(ArrayList<String> types, ArrayList<Integer> count){
        spawnTypes = types;
        spawnCount = count;
        initializeSpawn();
    }
    public void initializeSpawn(){
        spawn = new ArrayList<GridEntity>();
        for(int i = 0; i < spawnTypes.size(); i++){
            String cls = spawnTypes.get(i);
            int amt = spawnCount.get(i);
            try{
                for(int j = 0; j < amt; j++){
                    spawn.add(ZombieFactory.createZombie(cls));
                }
            }catch(Exception e){e.printStackTrace();spawn.add(new Zombie());}
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
        String cls = spawnTypes.get(id);
        if(val==1){spawnCount.remove(id);spawnTypes.remove(id);}
        try{
            return ZombieFactory.createZombie(cls);
        }catch(Exception e){
            e.printStackTrace();
            return new Zombie();
        }
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < spawnTypes.size(); i++){
            sb.append(spawnTypes.get(i)).append(" x").append(spawnCount.get(i)).append(", ");
        }
        return sb.toString();
    }
}