package com.karel.game.weapons.critters;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.HealCharge;
import com.karel.game.ItemHolder;
import com.karel.game.SubAffecter;

/**
 * Write a description of class PassiveCritter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PassiveCritter extends GridObject implements SubAffecter, ICritter
{
    private ItemHolder source;
    private static final int reloadtime = 30;
    private int ammo = 0;//60
    private int id;
    private double deg, dist;
    private LilCritters spawner;
    public PassiveCritter(int id, double deg, double dist, LilCritters spawner, ItemHolder source){
        this.source = source;//do not set power
        this.id = id;
        this.deg = deg;
        this.dist = dist;
        this.spawner = spawner;
    }
    public GridEntity getSource(){
        return source.getHolder();
    }
    public void update(){
        branchOut(source.getHolder(), deg+source.getTargetRotation(), dist);
        ammo++;
        face(source.getTargetX(), source.getTargetY(), true);
        if(ammo>=reloadtime&&getSource().canAttack()&&!getSource().isDead()){
            attack();
            ammo = 0;
        }
    }
    public int getAmmo(){
        return ammo;
    }
    public void syncAmmo(int am){
        ammo = am;
    }
    public void attack(){
        CritterSlime bullet = new CritterSlime(getRotation(), getSource());
        addObjectHere(bullet);
    }
    public void deploy(){
        face(source.getTargetX(), source.getTargetY(), true);
        FlyingCritter bullet = new FlyingCritter(getRotation(), getSource(), spawner, id);
        addObjectHere(bullet);
        spawner.notifyCritterPhaseChange(id, bullet);
        die();
    }
    public void gadget(){
        addObjectHere(new HealCharge(0, getSource(), getSource().getMaxHealth()/10));
        setPower(1.5);
    }
    public void die(){
        getWorld().removeObject(this);
        super.die();
    }
}
