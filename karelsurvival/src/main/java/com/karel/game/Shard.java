package com.karel.game;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Shard extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    private int bonus;
    private boolean isSuper;
    public Shard(double rotation, GridObject source, boolean isSuper)
    {
        super(rotation, source);
        setSpeed(13);
        setLife(30);
        setDamage(200);
        setNumTargets(-1);
        bonus = 60;
        this.isSuper = isSuper;
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        if(face(targ, false)%360>getDirection()%360){
            setDirection(getDirection()-90);
            if(isSuper){
                addObjectHere(new Echo(getDirection()-45, this));
            }
        }else{
            setDirection(getDirection()+90);
            if(isSuper){
                addObjectHere(new Echo(getDirection()+45, this));
            }
        }
        setRealRotation(getRealRotation()+90);
        addLife(bonus);
        bonus/=2;
    }
}
