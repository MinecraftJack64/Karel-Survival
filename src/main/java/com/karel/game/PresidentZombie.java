package com.karel.game;
import java.util.List;
import java.util.ArrayList;

/**
 * Write a description of class PresidentZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PresidentZombie extends Zombie
{
    private static final int gunReloadTime = 5;         // The minimum delay between firing the gun.

    private int reloadDelayCount;               // How long ago we fired the gun the last time.

    public String getStaticTextureURL(){return "presidentzareln.png";} 
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int ammo = 0;
    private int damage = 400;
    private int nbg = 4;//number of bodyguards
    BodyguardZombie guards[] = new BodyguardZombie[nbg];
    GridEntity gtargets[] = new GridEntity[nbg];
    private int healerid;
    ArrayList<GridEntity> targets = new ArrayList<GridEntity>();
    /**
     * Initilise this rocket.
     */
    public PresidentZombie()
    {
        reloadDelayCount = 5;
        setSpeed(2);
        startHealth(300);
        applyShield(new ArmorShield(new ShieldID(this), 300));
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        reloadDelayCount++;
        double monangle = face(getTarget(), canMove());
        //setRotation(getRotation()-1);
        if(!canAttack()){
            return;
        }
        for(int i = 0; i < guards.length; i++){
            if(guards[i]==null||guards[i].isDead()){
                guards[i] = new BodyguardZombie(this, i);//replace dead bodyguards
                getWorld().addObject(guards[i], getRealX(), getRealY());
            }
        }
        if(distanceTo(getTarget())>140)walk(monangle, 1);
        else{
            explodeOn(150, "enemy", (g)->{
                if(!targets.contains(g)){
                    targets.add(g);
                }
            }, null);
        }
        for(int i = targets.size()-1; i >=0; i--){
            if(targets.get(i).isDead()){
                targets.remove(i);
            }
        }
        int ng;
        if(getHealth()*1.0/getMaxHealth()<0.6){
            ng = nbg-1;
            gtargets[0] = this;//make a bg heal me
        }else{
            ng = nbg;
        }
        int d = (int)Math.ceil(ng*1.0/targets.size());
        int nd = d;
        int c = 0;
        if(targets.size()>0){
            for(int i = nbg-ng; i < gtargets.length; i++){
                gtargets[i] = targets.get(c);
                nd--;
                if(nd==0){
                    nd = d;
                    c++;
                }
            }
        }else{
            for(int i = nbg-ng; i < gtargets.length; i++){
                gtargets[i] = null;
            }
        }
    }
    public GridEntity getTarget(int id){
        return gtargets[id];
    }
    
    public void hit(int amt, GridObject source){
        super.hit(amt, source);
        GridEntity sourc;
        if(source instanceof Projectile){
            sourc = (GridEntity)((Projectile)source).getSource();
        }else if(source instanceof GridEntity){
            sourc = (GridEntity) source;
        }else{
            return;
        }
        if(isAggroTowards(sourc)&&!targets.contains(sourc)){
            targets.add(sourc);
        }
    }
    
    //ovveride this
    public int getXP(){
        return 1000;
    }

    public void die(GridObject source){
        try{
            //explode if not stunned
            Nuke bomb = new Nuke(this);
            getWorld().addObject(bomb, getTarget().getRealX(), getTarget().getRealX());
            super.die(source);
        }catch(IllegalStateException e){
            //
        }
    }
    public String getName(){
        return "President Zombie";
    }
}
