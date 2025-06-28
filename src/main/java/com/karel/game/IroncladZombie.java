package com.karel.game;
import java.util.List;

import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.rocket.RocketZombie;
import com.karel.game.weapons.ShieldID;

/**
 * Write a description of class IroncladZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class IroncladZombie extends Zombie
{
    private static final int gunReloadTime = 160;         // The minimum delay between firing the gun.
    private boolean canRetaliate = true;
    private int reloadDelayCount;               // How long ago we fired the gun the last time.

    public String getStaticTextureURL(){return "tankzareln.png";}
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private static double attackrange = 900;
    /**
     * Initilise this rocket.
     */
    public IroncladZombie()
    {
        reloadDelayCount = 5;
        scaleTexture(60, 60);
        setSpeed(1);
        startHealthShield(new ArmorShield(new ShieldID(this), 4500));
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        canRetaliate = true;
        reloadDelayCount++;
        double monangle = face(getTarget(), canMove());
        //setRotation(getRotation()-1);
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else if(distanceTo(getTarget())>30){
            walk(monangle, 0.4);
            fire();
        }else fire();
    }
    public boolean canBePulled(){
        return false;
    }
    public void hitIgnoreShield(int amt, double exp, GridObject source){
        super.hitIgnoreShield(amt, exp, source);
        if(source!=null&&source.isAggroTowards(this)&&canRetaliate){
            canRetaliate = false;
            explodeOn(60, amt/3);
        }
    }
    public void die(GridObject source){
        try{
            //explode if not stunned
            addObjectHere(new RocketZombie(50, 0));
            super.die(source);
            explodeOn(100, 500);
            Sounds.play("explode");
        }catch(IllegalStateException e){
            //
        }
    }
    /**
     * Check whether there are any key pressed and react to them.
     */
    private void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()){
            ZBigBullet bullet = new ZBigBullet (getRotation(), this);
            getWorld().addObject (bullet, getX(), getY());
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
        }
    }
    //ovveride this
    public int getXP(){
        return 1000;
    }
    
    public String getName(){
        return "Ironclad Zombie";
    }
}
