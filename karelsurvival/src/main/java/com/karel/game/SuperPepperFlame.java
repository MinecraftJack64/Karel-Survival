package com.karel.game;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class SuperPepperFlame extends Bullet
{
    /** The damage this bullet will deal */
    private CapsaicinTorch torch;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public SuperPepperFlame(double rotation, GridObject source, CapsaicinTorch toNotify)
    {
        super(rotation, source);
        setNumTargets(-1);
        torch = toNotify;
    }
    
    public void doHit(GridEntity targ){
        targ.applyEffect(new PoisonEffect(1,1,7,this));
        super.doHit(targ);
        torch.notifyHit();
    }
}
