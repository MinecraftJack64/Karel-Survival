package com.karel.game.weapons.farmer;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.HarvestBeeper;
import com.karel.game.Melee;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Hoe extends Melee
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int life = 30;
    private double degtotarg, radius;
    private boolean clockwise;
    private boolean issuper;
    private Farmer myFarmer;
    
    public Hoe(double rotation, boolean issuper, Farmer farmer, GridObject source)
    {
        super(rotation, source);
        setSpeed(15);
        setLife(life);
        setDamage(100);
        setNumTargets(-1);
        degtotarg = rotation+90;
        clockwise = true;
        this.radius = 110;
        this.issuper = issuper;
        myFarmer = farmer;
    }
    
    public void applyPhysics(){
        if(life <= 0) {
            expire();
        } 
        else {
            life--;
            double centerx = getSource().getX(), centery = getSource().getY();
            setLocation(centerx+radius*Math.cos(degtotarg*Math.PI/180), centery+radius*Math.sin(degtotarg*Math.PI/180));
            setRotation(degtotarg);
            if(clockwise)degtotarg+=12;
            else degtotarg-=12;
            checkHit();
        }
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        if(targ.distanceTo(getSource())>100){ // critical hit
            super.doHit(targ);
        }
        if(targ.isDead()&&targ.willNotify(this)){
            addObjectHere(new HarvestBeeper(myFarmer));
        }
    }
    public double damageSecrecy(){
        return super.damageSecrecy()*(issuper?1.33:1);
    }
}
