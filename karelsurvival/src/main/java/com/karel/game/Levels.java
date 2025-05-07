package com.karel.game;
import java.util.ArrayList;

final class ZombieGroup{
    private final Class type;
    private final int count;
    public ZombieGroup(Class type, int cnt){
        this.type = type;
        this.count = cnt;
    }
    public Class getType(){
        return type;
    }
    public int getCount(){
        return count;
    }
}
final class Wave{
    private final double threshold;
    private final ZombieGroup[] groups;
    public Wave(double threshold, ZombieGroup... groups){
        this.threshold = threshold;
        this.groups = groups;
    }
    public double getThreshold(){
        return threshold;
    }
    public SpawnData getSpawnData(){
        ArrayList<Class> st = new ArrayList<Class>();
        ArrayList<Integer> sc = new ArrayList<Integer>();
        for(ZombieGroup g: groups){
            st.add(g.getType());
            sc.add(g.getCount());
        }
        return new SpawnData(st, sc);
    }
}
final class Level{
    Wave waves[];
    public Level(Wave... waves){
        this.waves = waves;
    }
    public LevelRunner getRunner(){
        return new LevelRunner(waves);
    }
}
final class LevelRunner{
    Wave waves[];
    int loc;
    public LevelRunner(Wave... waves){
        this.waves = waves;
        this.loc = 0;
    }
    public int getNumRemainingWaves(){
        return waves.length-loc;
    }
    public boolean canSpawnNextWave(int numliving, int max){
        if(max==0)return true;
        return numliving*1.0/max<=waves[loc].getThreshold();
    }
    public SpawnData nextWave(){
        return waves[loc++].getSpawnData();
    }
}
/**
 * Write a description of class Levels here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Levels  
{
    private static Level[] lvls = new Level[]{
        new Level(
            new Wave(0.5,
                new ZombieGroup(Zombie.class, 3)
            ),
            new Wave(0.5,
                new ZombieGroup(Zombie.class, 3),
                new ZombieGroup(ShieldZombie.class, 1)
            ),
            new Wave(0.5,
                new ZombieGroup(Zombie.class, 5),
                new ZombieGroup(ShieldZombie.class, 3)
            )
        ),
        new Level(
            new Wave(0.5,
                new ZombieGroup(Zombie.class, 3),
                new ZombieGroup(ShieldZombie.class, 1)
            ),
            new Wave(0.5,
                new ZombieGroup(Zombie.class, 3),
                new ZombieGroup(ExplodingZombie.class, 1)
            ),
            new Wave(0.5,
                new ZombieGroup(ShieldZombie.class, 2),
                new ZombieGroup(ExplodingZombie.class, 2)
            ),
            new Wave(0.5,
                new ZombieGroup(ShooterZombie.class, 1)
            )
        )
    };
    public static Level getLevel(int lvl){
        return lvls[lvl];
    }
    public static LevelRunner getLevelRunner(int lvl){
        return getLevel(lvl).getRunner();
    }
    public static int getNumLevels(){
        return lvls.length;
    }
}
