package com.karel.game;

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
        setSpeed(15);
        setLife(life);
        setDamage(20);
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
            double centerx = getSource().getRealX(), centery = getSource().getRealY();
            setRealLocation(centerx+xmax*Math.sin(degtotarg*Math.PI/180), centery+ymax*Math.sin(degtotarg*Math.PI/180));
            //setRealRotation(degtotarg);
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
            notifyDamage(targ, getDamage(targ)*100);
        }
    }
}
