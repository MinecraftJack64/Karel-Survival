package com.karel.game.weapons.critters;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class CritterSlime extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    private int animFrame;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public CritterSlime(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Weapons/critters/proj0");
        setRotation(getRotation()-180);
        setSpeed(15);
        setLife(20);
        setDamage(40);
        setNumTargets(2);
    }
    public void animate(){
        setImage("Weapons/critters/proj"+(animFrame)+".png");
        animFrame++;
        if(animFrame>2){
            animFrame = 0;
        }
        super.animate();
    }
}
