import greenfoot.*;
import java.util.ArrayList;

class SpawnData{
    public ArrayList<Class> spawnTypes;
    public ArrayList<Integer> spawnCount;
    public SpawnData(ArrayList<Class> types, ArrayList<Integer> count){
        spawnTypes = types;
        spawnCount = count;
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
        LaserZombie.class, EasterZombie.class, RussianDollZombie.class, /*WeedWhacker*/Zombie.class, SplitterZombie.class, /*Shaman*/Zombie.class,
        WarriorZombie.class, FlamethrowerZombie.class, PresidentZombie.class, JokerZombie.class, JackITBZombie.class, CannonZombie.class, StuntZombie.class, PortalZombie.class,
        WatermelonZombie.class};
    public int[] minlevels = new int[]{1, 3, 5, 7, 8, 9, 10, 13, 15, 17, 18, 19, 21, 23, 25, 27, 32, 33, 35, 39, 45, 47, 49, 51, 53, 55, 56};//18 is doctor
    public int[] maxnums = new int[]{8, 5, 3, 4, 2, 3, 2, 3, 2, 2, 4, 1, 1, 2, 2, 2, 3, 1, 3, 2, 1, 3, 3, 4, 8, 2, 2};
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
            if(minlevels[i]<wave){
                canSpawn.add(i);
            }else if(minlevels[i]==wave){
                forceSpawn.add(i);
            }
        }
    }
    public int selectSpawnableZombies(int count){
        spawnTypes.clear();
        spawnCount.clear();
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
        }
    }
    public int getMaxOf(int type, int wave){
        return Math.min(maxnums[type], wave+1-minlevels[type]);
    }
    public int randomDescend(int max){
        int c = max-1;
        while(c>=1&&Greenfoot.getRandomNumber(c)<c-1){
            c--;
        }
        return c;
    }
}
