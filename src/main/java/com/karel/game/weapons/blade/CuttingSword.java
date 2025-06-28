package com.karel.game.weapons.blade;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Melee;

/**
 * Single slice used by Blade weapon ult
 * 
 * @author Poul Henriksen
 */
public class CuttingSword extends Melee
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int life = 6;
    private double degtotarg, radius;
    private double xmax, ymax;
    
    public CuttingSword(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Weapons/blade/projUlt.png");
        scaleTexture(60);
        setRotation(getRotation()-45);
        setSpeed(15);
        setLife(life);
        setDamage(0);
        setMultiHit(false);
        setNumTargets(-1);
        degtotarg = rotation;
        this.radius = 120;
        xmax = radius*Math.cos(degtotarg*Math.PI/180);
        ymax = radius*Math.sin(degtotarg*Math.PI/180);
        degtotarg = 0;
    }
    
    public void applyPhysics(){
        if(life <= 0) {
            expire();
        } 
        else {
            life--;
            double centerx = getSource().getX(), centery = getSource().getY();
            setLocation(centerx+xmax*Math.sin(degtotarg*Math.PI/180), centery+ymax*Math.sin(degtotarg*Math.PI/180));
            //setRotation(degtotarg);
            degtotarg+=30;
            checkHit();
        }
    }
    @Override
    public int getDamage(GridEntity targ){
        return getDamage();
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        if(targ.isDead()&&targ.willNotify(this)){
            notifyDamage(targ, 1000);
            if(getSource() instanceof GridEntity ge) {
                heal(ge, Math.min(targ.getMaxHealth()*2, ge.getMaxHealth()/5));
            }
        }
    }
}
