package com.karel.game.gridobjects.gridentities.zombies.cannon;
import java.util.List;

import com.karel.game.BombDropper;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Sounds;
import com.karel.game.Target;
import com.karel.game.TickingTimeBomb;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;

import java.util.HashSet;

/**
 * Write a description of class CannonZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CannonZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.support, ZombieClass.penetrator};
    public String getStaticTextureURL(){return "cannonzareln.png";}
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private double ammo = 0;
    private int damage = 400;
    private GridEntity scammed;
    private GridEntity hold;
    private int getnearbycooldown = 0;
    private int fuse = 0;
    private double aimdir;
    private double aimdist;
    private boolean fuseLit;
    private Target target;
    private int keeptargetcooldown;
    private boolean isgrounded = true;
    private int bombcooldown = 550;
    private boolean bombing = false;
    HashSet<GridEntity> blacklist = new HashSet<GridEntity>();
    /**
     * Initilise this rocket.
     */
    public CannonZombie()
    {
        setSpeed(1.5);
        startHealth(500);
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        if(bombing){
            if(getnearbycooldown<=0||(scammed!=null&&scammed.isDead())){
                getNearbyZombie();
                getnearbycooldown = 15;
            }else{
                getnearbycooldown--;
            }
        }
        if(hold==null&&!bombing){
            //Pull nearest zombie in and hold = that zombie
            if(canMove()){
                if(getnearbycooldown<=0||(scammed!=null&&scammed.isDead())){
                    getNearbyZombie();
                    getnearbycooldown = 15;
                }else{
                    getnearbycooldown--;
                }
                if(scammed!=null&&!scammed.isDead()){
                    if(!scammed.canBePulled()){
                        blacklist.add(scammed);
                    }
                    scammed.pullTowards(this, 10);
                    if(distanceTo(scammed)<=10){
                        if(scammed.trap()){
                            getWorld().removeObject(scammed);
                            hold = scammed;
                            scammed = null;
                        }else{
                            blacklist.add(scammed);
                            scammed = null;
                        }
                    }
                }
            }
        }else{
            //Light fuse if enough ammo
            if(!fuseLit&&ammo<=0&&canAttack()&&isgrounded){
                fuseLit = true;
                fuse = 30;
                setAim();
            }
            //Adjust aim while fuse lit
            if(fuseLit){
                adjustAim();
            }
            //Shoot
            if(fuseLit&&fuse<=0){
                shoot();
                fuseLit = false;
                ammo = 120;
            }
        }
        double monangle = face(getTarget(), canMove());
        if(ammo>0)ammo-=getReloadMultiplier();
        if(fuse>0)fuse--;
        if(scammed==null&&hold==null){
            bombcooldown--;
        }else{
            bombcooldown = 550;
        }
        bombing = bombcooldown<=0;
        if(keeptargetcooldown>0){
            keeptargetcooldown--;
            if(keeptargetcooldown==0){
                if(target!=null){
                    getWorld().removeObject(target);
                }
            }
        }
        if(distanceTo(getTarget())>600&&canMove()){
            walk(monangle, 1);
            isgrounded = false;
        }
        else{
            if(canMove())isgrounded = true;
        }
    }
    public void getNearbyZombie(){
        List<GridEntity> l = getGEsInRange(70);
        scammed = null;
        for(GridEntity g:l){
            if(isAlliedWith(g)&&!blacklist.contains(g)){
                scammed = g;
                break;
            }
        }
    }
    public void setAim(){
        aimdist = distanceTo(getTarget());
        aimdir = face(getTarget(), true);
        target = new Target();
        addKActorHere(target);
        setTargetLocation();
    }
    public void adjustAim(){
        if(distanceTo(getTarget())<aimdist-1.5){
            aimdist-=2;
        }else if(distanceTo(getTarget())>aimdist+1.5){
            aimdist+=2;
        }
        if(aimdir<face(getTarget(), false)-0.5){
            aimdir+=0.5;
        }else if(aimdir>face(getTarget(), false)+0.5){
            aimdir-=0.5;
        }
        setRotation(aimdir-90);
        setTargetLocation();
    }
    public void setTargetLocation(){
        target.setLocation(getX()+Math.cos((aimdir-90)*Math.PI/180)*aimdist, getY()+Math.sin((aimdir-90)*Math.PI/180)*aimdist);
    }
    @Override
    public boolean prioritizeTarget(){
        return true;
    }
    public void shoot(){
        if(hold!=null){
            hold.untrap();
            addObjectHere(hold);
            if(hold.knockBack(aimdir, Math.min(aimdist, 700), 400, this)){
                keeptargetcooldown = (int)hold.getPhysicsArc().getDuration();
            }else{
                keeptargetcooldown = 0;
                getWorld().removeObject(target);
            }
            
            hold = null;
        }else{
            BombDropper bd = new BombDropper(aimdir, Math.min(aimdist, 700), 400, new TickingTimeBomb(this), this);
            addObjectHere(bd);
            keeptargetcooldown = (int)bd.getPath().getDuration();
        }
        explodeOn(70, damage/2);
        Sounds.play("explode");
    }
    
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 800;
    }

    public void die(GridObject source){
        if(target!=null){
            getWorld().removeObject(target);
        }
        if(hold!=null){
            hold.untrap();
            addObjectHere(hold);
        }
        try{
            //explode if not stunned
            super.die(source);
            explodeOn(70, damage);
            Sounds.play("explode");
        }catch(IllegalStateException e){
            //
        }
    }
    public boolean canBePulled(){
        return !isgrounded;
    }
    public boolean isWall(){
        return isgrounded;
    }
    public String getName(){
        return "Cannon Zombie";
    }
}
