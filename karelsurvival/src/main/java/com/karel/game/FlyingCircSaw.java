package com.karel.game;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class FlyingCircSaw extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public FlyingCircSaw(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(22);
        setLife(30);
        setDamage(200);
        setNumTargets(5);
        setMultiHit(false);
    }
    public void animate(){
        setRealRotation(getRealRotation()+30);
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        //point to next target
        GridEntity next = null;
        for(GridEntity g: getWorld().allEntities()){
            if(targ!=g&&!getHitStory().contains(g)&&isAggroTowards(g)&&(next==null||distanceTo(g)<distanceTo(next))){
                next = g;
            }
        }
        if(next==null){
            setNumTargets(0);
        }else{
            double monangle = face(next, false);
            setDirection(monangle);
            setLife(15);
        }
    }
    public double damageSecrecy(){
        return super.damageSecrecy()*0.5;
    }
}
