import greenfoot.*;
import java.util.ArrayList;

/**
 * Write a description of class ZombieSpawner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ZombieSpawner implements Spawner
{
    public int wavehealth = 0;
    public int wavemaxhealth = 0;
    public int wavelevel = 1;
    private int bossphase = 0;
    private boolean bossfight;
    public int cwavecooldown = 200;
    public GridEntity boss;
    SpawnCalculator calculator = new SpawnCalculator();
    public ArrayList<Class> spawnTypes;
    public ArrayList<Integer> spawnCount;
    public ArrayList<GridEntity> currentlySpawned;
    public boolean isBossWave(){
        return wavelevel==31||wavelevel==30;
    }
    public boolean allSpawnedDied(){
        for(GridEntity g:currentlySpawned){
            if(!g.isDead()){
                return false;
            }
        }
        return true;
    }
    public void checkSpawn(){
        if(!bossfight){if(allSpawnedDied()/*3.0*wavemaxhealth/4*/){
            wavelevel++;
            if(!isBossWave()){
                ((GameUI)(KWorld.me.ui)).waveCounter.setValue(wavelevel);
                spawnZombies(Greenfoot.getRandomNumber(Math.min(wavelevel, 7))+wavelevel/2);
                if(Greenfoot.getRandomNumber(3)<2){
                    SupplyCrate thing = new SupplyCrate(new WeaponFrag());
                    spawnZombieRandom(thing);
                }
            }else{
                if(wavelevel==31)startBossFight();
                else heraldBossFight();
            }
        }}
        else{
            if(bossphase < 6){
                cwavecooldown--;
                if(cwavecooldown<=0){
                    for(int i = 0; i < bossphase; i++)spawnZombie(new Zombie());
                    if(bossphase>=3)spawnZombie(new NinjaZombie());
                    if(bossphase==5)spawnZombie(new NinjaZombie());
                    if(bossphase>=4&&Greenfoot.getRandomNumber(4)==0){
                        if(Greenfoot.getRandomNumber(2)==0){
                            spawnZombie(new LaserZombie());
                        }else{
                            spawnZombie(new EasterZombie());
                        }
                    }
                    cwavecooldown = Greenfoot.getRandomNumber(300)+600;
                }
            }else{
                if(boss.isDead()){
                    stopBossFight();
                }
            }
        }
    }
    public void spawnZombies(int count)
    {
        SpawnData dat = calculator.calculateSpawn(count, wavelevel);
        spawnTypes = dat.spawnTypes;
        spawnCount = dat.spawnCount;//graphics, sound, spawner, tutorial
        currentlySpawned = new ArrayList<GridEntity>();
        for(int i = 0; i < spawnTypes.size(); i++){
            for(int f = 0; f < spawnCount.get(i); f++){
                try{GridEntity toSpawn = (GridEntity)(spawnTypes.get(i).newInstance());
                spawnZombie(toSpawn);currentlySpawned.add(toSpawn);}catch(Exception e){e.printStackTrace();}
            }
        }
        System.out.println("Spawning "+currentlySpawned.size()+" zombies");
    }

    public void spawnZombie(GridObject toSpawn){
        int x = Greenfoot.getRandomNumber(KWorld.me.gridwidth+1);
        int y = Greenfoot.getRandomNumber(KWorld.me.gridheight+1);
        switch(Greenfoot.getRandomNumber(4)){
            case 0:
                x = -1;
                break;
            case 1:
                x = KWorld.me.gridwidth;
                break;
            case 2:
                y = -1;
                break;
            case 3:
                y = KWorld.me.gridheight;
                break;
        }
        KWorld.me.addToGrid(toSpawn, x, y);
    }
    public void spawnZombieRandom(GridObject toSpawn){
        int x = Greenfoot.getRandomNumber(KWorld.me.gridwidth);
        int y = Greenfoot.getRandomNumber(KWorld.me.gridheight);
        KWorld.me.addToGrid(toSpawn, x, y);
    }
    public void spawnZombie(GridEntity toSpawn, int x, int y){
        KWorld.me.addToGrid(toSpawn, x, y);
    }
    public void heraldBossFight(){
        ZombieHerald h = new ZombieHerald();
        spawnZombie(h, KWorld.me.gridwidth/2, KWorld.me.gridheight/2);
        wavemaxhealth = wavehealth = 1;
        currentlySpawned.add(h);
    }
    public void startBossFight(){
        Wizard wizzie = new Wizard();
        spawnZombie(wizzie, KWorld.me.gridwidth/2, KWorld.me.gridheight/2);
        wavemaxhealth = wavehealth = 1;
        bossphase = 1;
        boss = wizzie;
        bossfight = true;
        KWorld.me.bossBG();
    }
    public void stopBossFight(){
        bossfight = false;
        bossphase = 0;
        KWorld.me.resetBG();
        boss = null;
    }
    public void setBossPhase(int p){
        bossphase = p;
    }
}
