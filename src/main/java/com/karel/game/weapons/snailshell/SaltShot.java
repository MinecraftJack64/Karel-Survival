package com.karel.game.weapons.snailshell;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.VisionPercentageEffect;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class SaltShot extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public SaltShot(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Weapons/shotgun/proj.png");
        scaleTexture(25);
        setRotation(rotation+45);
        setSpeed(17);
        setLife(6);
        setDamage(50);
    }
    public void doHit(GridEntity g){
        g.applyEffect(new VisionPercentageEffect(0, 90, this));
        super.doHit(g);
    }
}
