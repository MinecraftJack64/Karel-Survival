package com.karel.game;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Scissors extends Boomerang
{
    /** The damage this bullet will deal */
    private int damage = 25;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int life = 50;
    private double speed;
    private boolean isSingleTarget;
    private int phase;
    private GridEntity targ;
    private boolean hasreturned;
    
    public Scissors(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(27);
        setReturnSpeed(25);
        setNumTargets(-1);
        phase = 1;
        hasreturned = false;
        setDamage(115);
        setLife(4);
        setDamageOnReturn(1);
    }
    public void doHit(GridEntity targ){
        //
        super.doHit(targ);
    }
}
