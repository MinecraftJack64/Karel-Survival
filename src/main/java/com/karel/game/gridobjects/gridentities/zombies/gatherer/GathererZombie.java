package com.karel.game.gridobjects.gridentities.zombies.gatherer;

import com.karel.game.BeeperAccepter;
import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.collectibles.Beeper;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.gridobjects.gridentities.zombies.chief.ITribal;

/**
 * Picks up beepers and gives them to teammates on request
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GathererZombie extends Zombie implements ITribal, BeeperAccepter
{
    private static final ZombieClass[] classes = new ZombieClass[]{ZombieClass.support, ZombieClass.pressurer};
    private static final int reloadTime = 30;
    private static final int beeperbagSize = 10;
    private double ammo = 0;
    private int beeperbag = 10;
    private int collectCooldown = 20;
    public String getStaticTextureURL(){
        return "warriorzareln.png";
    }
    public GathererZombie()
    {
        setSpeed(2);
        startHealth(500);
    }
    public void behave(){
        ammo+=getReloadMultiplier();
        collectCooldown--;
        if(collectCooldown>0)return;
        GridObject target = getAnyTarget();
        boolean is = target instanceof GridEntity;
        GridEntity et = is?(GridEntity)target:null;
        if(is&&isAggroTowards(target)){
            super.behave();
        }else if(is&&isAlliedWith(et)){
            if(distanceTo(et)>200){
                walk(face(et, true), 1.5);
            }else{
                attemptFire(et);
            }
        }else{
            walk(face(target, true), 1);
        }
        if(getPercentHealth()<1){
            attemptFire(this);
        }
    }
    public GridObject getAnyTarget(){
        //search for nearby zombies that need healing, then players, then beepers, then other aggroed
        GridEntity bestTribal = null;
        GridEntity bestEnemy = null;
        GridObject bestBeeper = null;
        for(GridObject go: getWorld().allObjects()){
            if(beeperbag>0&&go instanceof ITribal i&&i.requestingHeal()&&(bestTribal==null||distanceTo(bestTribal)>distanceTo(go))){
                bestTribal = (GridEntity)go;
            }else if(go instanceof GridEntity ge&&isAggroTowards(ge)&&(bestEnemy==null||distanceTo(bestEnemy)>distanceTo(go))){
                bestEnemy = ge;
            }else if(beeperbag<beeperbagSize&&go instanceof Beeper&&(bestBeeper==null||distanceTo(bestBeeper)>distanceTo(go))){
                bestBeeper = go;
            }
        }
        if(bestTribal!=null){
            return bestTribal;
        }else if(bestEnemy!=null&&bestBeeper!=null){
            return distanceTo(bestEnemy)<distanceTo(bestBeeper)?bestEnemy:bestBeeper;
        }else if(bestEnemy!=null){
            return bestEnemy;
        }else if(bestBeeper!=null){
            return bestBeeper;
        }
        return getTarget();
    }
    public void attemptFire(GridEntity target){
        if(ammo>=reloadTime&&beeperbag>0){
            ammo = 0;
            beeperbag--;
            addObjectHere(new BeeperShot(target, this));
        }
    }
    public boolean requestingHeal(){
        return getPercentHealth()<1&&beeperbag<beeperbagSize;
    }
    public void collectBeeper(){
        beeperbag++;
        if(beeperbag>beeperbagSize){
            beeperbag = beeperbagSize;
        }
        collectCooldown = 20;
    }
    public void die(GridObject source){
        for(int i = 0; i < beeperbag; i++){
            addObjectHere(new BeeperScatter(Greenfoot.getRandomNumber(360), Greenfoot.getRandomNumber(50), 10, source));
        }
        beeperbag = 0;
        super.die(source);
    }
    public boolean acceptingBeepers(){
        return beeperbag<beeperbagSize&&collectCooldown<=0;
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 0;
    }
    public String getName(){
        return "Gatherer Zombie";
    }
    @Override
    public String getZombieID(){
        return "gatherer";
    }
}
