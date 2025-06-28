package com.karel.game;

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
    private boolean clockwise;
    
    public TrollBat(double rotation, boolean issuper, GridObject source)
    {
        super(rotation, source);
        setLife(life);
        setDamage(issuper?450:400);
        setNumTargets(-1);
        degtotarg = 0;
        this.radius = issuper?160:130;
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
            degtotarg-=15;
            checkHit();
        }
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        if(!targ.isDead()&&targ.willNotify(this)){
            notifyDamage(targ, getDamage(targ)*5);
        }
    }
}
