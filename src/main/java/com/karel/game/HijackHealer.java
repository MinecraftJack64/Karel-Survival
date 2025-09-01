package com.karel.game;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class HijackHealer extends FlyingProjectile
{
    GridEntity h;
    public HijackHealer(double rotation, double targetdistance, double height, GridObject source, GridEntity myMount)
    {
        super(rotation, targetdistance, height, source);
        setRange(120);
        setDamage(300);
        h = myMount;
    }
    public void doHit(GridEntity g){
        g.addObjectHere(new HealCharge(0, getSource(), h, getDamage()));
        super.doHit(g);
    }
    public double getGravity(){
        return 2;
    }
}
