package com.karel.game;

import com.karel.game.weapons.EffectID;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ChameleonTongue extends Boomerang
{
    /** The damage this bullet will deal */
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private GridEntity target;
    private EffectID pullStun, afterStun;
    private boolean hasTarget;
    private boolean hashitfirst = false;
    private boolean sticky;
    
    public ChameleonTongue(double rotation, boolean ispurple, boolean issticky, GridEntity source)
    {
        super(rotation, source);
        setSpeed(25);
        setReturnSpeed(25);
        setLife(18);
        setDamage(60);
        pullStun = new EffectID(this, "pull");
        afterStun = new EffectID(this, "pullfinish");
        sticky = issticky;
        if(ispurple){
            setNumTargets(-1);
        }
    }
    public GridEntity target(){
        return target;
    }
    /**
     * The bullet will damage asteroids if it hits them.
     */
    public void doReturn()
    {
        if(!hasTarget){
            super.doReturn();
        }else{
            super.doReturn();
            if(!target.isDead()){
                //target.stun(pullStun);
                if(!target.pullTo(getRealX(), getRealY())){
                    target.unstun(pullStun);
                    target = null;
                    hasTarget = false;
                }
            }
        }
    }
    public void dieForReal(){
        if(target!=null&&!target.isDead()){
            target.unstun(pullStun);
            target.applyEffect(new StunEffect(30, getSource(), afterStun));
        }
        super.dieForReal();
    }
    public GridEntity getSource(){
        return (GridEntity)(super.getSource());
    }
    
    /**
     * Check whether we have hit an asteroid.
     */
    public void doHit(GridEntity asteroid)
    {
        super.doHit(asteroid);
        if(sticky)asteroid.applyEffect(new SpeedPercentageEffect(0.5, 10, this));
        if(getNumTargets()>=0&&(asteroid.getPercentHealth()<=(sticky?0.45:0.3)||asteroid.getHealth()<=getPower()*100)){
            asteroid.stun(pullStun);
            target = asteroid;
            hasTarget = true;
            setReturnSpeed(18);
            Sounds.play("lassotighten");
        }else{
            //
        }
        hashitfirst = true;
    }
    public boolean covertDamage(){
        return hashitfirst;
    }
}
