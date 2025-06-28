package com.karel.game.weapons.blade;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Melee;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Sword extends Melee
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int life = 15;
    private double degtotarg, radius;
    private boolean clockwise;
    private boolean issuper;
    
    public Sword(double rotation, int strength, boolean dir, boolean issuper, GridObject source)
    {
        super(rotation, source);
        setImage("Weapons/blade/proj.png");
        scaleTexture(70);
        setSpeed(15);
        setLife(life);
        setDamage(200+strength*50);
        setNumTargets(-1);
        degtotarg = rotation+(dir?180:0);
        clockwise = dir;
        this.radius = 110;
        this.issuper = issuper;
    }
    
    public void applyPhysics(){
        if(life <= 0) {
            expire();
        } 
        else {
            life--;
            double centerx = getSource().getRealX(), centery = getSource().getRealY();
            setRealLocation(centerx+radius*Math.cos(degtotarg*Math.PI/180), centery+radius*Math.sin(degtotarg*Math.PI/180));
            setRealRotation(degtotarg+45);
            if(clockwise)degtotarg+=12;
            else degtotarg-=12;
            checkHit();
        }
    }
    @Override
    public int getDamage(GridEntity targ){
        return issuper?(int)((90-getSource().getFacingDistance(targ))*3/5+getDamage()):getDamage();
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        if(targ.isDead()&&targ.willNotify(this)){
            notifyDamage(targ, getDamage(targ)*5);
            if(getSource() instanceof GridEntity ge) {
                heal(ge, Math.min(targ.getMaxHealth()*2, ge.getMaxHealth()/5));
            }
        }
    }
}
