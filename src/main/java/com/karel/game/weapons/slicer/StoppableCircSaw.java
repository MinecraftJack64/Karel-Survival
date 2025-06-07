package com.karel.game.weapons.slicer;
import com.karel.game.GridObject;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class StoppableCircSaw extends CircSaw
{
    private Slicer weapon;
    
    public StoppableCircSaw(double rotation, GridObject source, Slicer weapon)
    {
        super(rotation, source);
        this.weapon = weapon;
    }
    /**
     * The bullet will damage asteroids if it hits them.
     */
    public void applyPhysics()
    {
        if(!weapon.getHand().isAttacking()&&getLife()<=10&&getPhase()==1){
            expire();
        }
        super.applyPhysics();
    }
}
