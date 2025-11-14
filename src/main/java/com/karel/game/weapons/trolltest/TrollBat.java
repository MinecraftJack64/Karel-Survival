package com.karel.game.weapons.trolltest;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Melee;
import com.karel.game.effects.EffectID;

/**
 * Used by TrollBridge weapon
 * 
 * @author Poul Henriksen
 */
public class TrollBat extends Melee
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int life = 12;
    private double degtotarg, radius;
    private int cooldown = 30;
    
    public TrollBat(double rotation, boolean issuper, GridObject source)
    {
        super(rotation, source);
        setLife(life);
        setDamage(issuper?425:375);
        setNumTargets(-1);
        degtotarg = rotation;
        this.radius = issuper?160:130;
    }
    
    public void applyPhysics(){
        if(life <= 0) {
            expire();
        } 
        else {
            if(cooldown>0){
                cooldown--;
                return;
            }
            life--;
            double centerx = getSource().getX(), centery = getSource().getY();
            setLocation(centerx+radius*Math.cos(degtotarg*Math.PI/180), centery+radius*Math.sin(degtotarg*Math.PI/180));
            setRotation(degtotarg);
            degtotarg-=15;
            checkHit();
        }
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        if(targ.hasEffect(new EffectID("trolltest-riddle"))&&targ.willNotify(this)){
            notifyDamage(targ, getDamage(targ)*5);
        }
    }
}
