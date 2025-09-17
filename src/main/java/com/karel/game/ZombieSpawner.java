package com.karel.game;
import java.util.ArrayList;
import java.util.Random;

import com.karel.game.gamemodes.SpawnData;
import com.karel.game.gridobjects.collectibles.WeaponFrag;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.easter.EasterZombie;
import com.karel.game.gridobjects.gridentities.zombies.herald.ZombieHerald;
import com.karel.game.gridobjects.gridentities.zombies.ninja.NinjaZombie;
import com.karel.game.gridobjects.gridentities.zombies.wizard.Wizard;


/**
 * Write a description of class ZombieSpawner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ZombieSpawner implements Spawner
{
    private World myWorld = Game.world;

    public int waveHealth = 0;
    public int waveMaxHealth = 0;
    public int wavelevel = 1;
    
    public QueueMap<Integer, Boss> bosses;
    private int nextBossWave;
    public int cwavecooldown = 200;
    private int bossphase = 0;
    private boolean bossfight;
    public Boss boss;
    
    SpawnCalculator calculator = new SpawnCalculator();
    private SpawnData toSpawn;
    private int nextSpawn;
    private int remainingSections;
    private int forceNextSpawn; // 450
    private int lastMaxHealth = 0;
    public ArrayList<GridEntity> currentlySpawned;
    
    public ZombieSpawner(){
        bosses = new QueueMap<Integer, Boss>();
        bosses.add(31, new Wizard());
        bosses.add(45, new CloudServer());
        try{nextBossWave = bosses.peek().getKey();}
        catch(Exception e){nextBossWave = Integer.MAX_VALUE;}
    }
    public boolean isBossWave(){
        return wavelevel==nextBossWave||wavelevel==nextBossWave-1;
    }

    public boolean allSpawnedDied(){
        for(GridEntity g:currentlySpawned){
            if(!g.isDead()){
                return false;
            }
        }
        return true;
    }

    public int getNumLivingAndActive(){
        int c = 0;
        for(GridEntity g:currentlySpawned){
            if(!g.isDead()&&g.isActive()){
                c++;
            }
        }
        return c;
    }
    public int totalHealth(){
        int c = 0;
        for(GridEntity g:currentlySpawned){
            if(!g.isDead())c+=g.getHealth();
        }
        return c;
    }

    public void checkSpawn(){
        if(!bossfight){
            if(toSpawn!=null){
                if(getNumLivingAndActive()<=nextSpawn||forceNextSpawn<=0){
                    remainingSections--;
                    spawnZombies(toSpawn, remainingSections);
                }
                forceNextSpawn--;
                if(toSpawn.isClear()){
                    toSpawn = null;
                }
            }
            if(toSpawn==null&&allSpawnedDied()){
                wavelevel++;
                if(!isBossWave()){
                    int count = Greenfoot.getRandomNumber(Math.min(wavelevel, 7))+wavelevel/2;
                    if(count<1)count = 1;
                    remainingSections = count/3;
                    spawnZombies(count, remainingSections);
                    if(Greenfoot.getRandomNumber(3)<2){
                        SupplyCrate thing = new SupplyCrate(new WeaponFrag());
                        spawnZombieRandom(thing);
                    }
                }else{
                    if(wavelevel==nextBossWave){
                        startBossFight(bosses.remove().getValue());
                        try{nextBossWave = bosses.peek().getKey();}
                        catch(Exception e){nextBossWave = Integer.MAX_VALUE;}
                    }
                    else heraldBossFight();
                }
            }
            if(!Game.isPaused()){
                for(GridEntity ge: currentlySpawned){
                    if(ge instanceof Zombie){
                        //System.out.println(ge.getName()+" health: "+ge.getHealth()+" isDead? "+ge.isDead());
                    }
                }
                //System.out.println(toSpawn);
                //System.out.println("Max health: "+lastMaxHealth);
                //System.out.println("total health: "+totalHealth()+" time until next spawn: "+forceNextSpawn+"\n");
            }
            Game.gameUI().updateWaveHealth(totalHealth());
        }
        else{
            if(bossphase < 6){
                cwavecooldown--;
                if(cwavecooldown<=0){
                    for(int i = 0; i < bossphase; i++)spawnZombie(new Zombie());
                    if(bossphase>=3)spawnZombie(new NinjaZombie());
                    if(bossphase==5)spawnZombie(new NinjaZombie());
                    if(bossphase>=4&&Greenfoot.getRandomNumber(4)==0){
                        spawnZombie(new EasterZombie());
                    }
                    cwavecooldown = Greenfoot.getRandomNumber(300)+600;
                }
                setBossPhase(boss.getPhase());
            }else{
                if(boss.isDead()){
                    stopBossFight();
                    wavelevel++;
                    //System.out.println("Boss defeated, wave level now: "+wavelevel);
                }
            }
        }
    }

    public void spawnZombies(int count, int sections)
    {
        toSpawn = calculator.calculateSpawn(count, wavelevel);
        lastMaxHealth = toSpawn.totalHealth();
        Game.gameUI().newWave(toSpawn.totalHealth());
        //System.out.println("first Spawn");
        spawnZombies(toSpawn, sections);
        if(toSpawn.isClear()){
            toSpawn = null;
        }
    }

    public void spawnZombies(SpawnData dat, int sections){
        currentlySpawned = new ArrayList<GridEntity>();
        int total = 0;
        for(int i = 0; i < dat.size(); i++){
            int amt = randomCenter(dat.count(i)); // TODO
            if(sections==1){
                amt = dat.count(i);
            }
            total+= amt;
            for(int f = 0; f < amt; f++){
                //try{
                    GridEntity toSpawn = dat.pop(i);
                    spawnZombie(toSpawn);
                    currentlySpawned.add(toSpawn);
                //}catch(Exception e){e.printStackTrace();}
            }
        }
        if(total==0){
            //System.out.println("No zombies spawned, spawning random zombie");
            //System.out.println(dat+" "+dat.size()+" "+dat.isClear());
            //try{
                GridEntity toSpawn = dat.pop(Greenfoot.getRandomNumber(dat.size()));
                spawnZombie(toSpawn);
                currentlySpawned.add(toSpawn);
            //}catch(Exception e){e.printStackTrace();}
        }
        nextSpawn = currentlySpawned.size()/2;
        forceNextSpawn = 450;
        //System.out.println("Spawning "+currentlySpawned.size()+" zombies");
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
        ZombieHerald h = new ZombieHerald();
        spawnZombie(h, myWorld.gridwidth/2, myWorld.gridheight/2);
        currentlySpawned.add(h);
        Game.gameUI().newWave(totalHealth());
    }

    public void startBossFight(Boss wizzie){
        spawnZombie(wizzie, myWorld.gridwidth/2, myWorld.gridheight/2);
        bossphase = 1;
        boss = wizzie;
        bossfight = true;
        myWorld.bossBG();
    }

    public void stopBossFight(){
        bossfight = false;
        bossphase = 0;
        myWorld.resetBG();
        boss = null;
    }

    public void setBossPhase(int p){
        bossphase = p;
    }

    public int randomCenter(int max){
        return Math.max(Math.min((int)((new Random()).nextGaussian()*max/3+max/2), max), 0);
    }
}
