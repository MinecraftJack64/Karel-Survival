package com.karel.game.gridobjects.gridentities.zombies.peanutcan;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.ZBullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZNutSnake extends ZBullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 200;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    
    public ZNutSnake(double rotation, GridObject source, int life)
    {
        super(rotation, source);
        setImage("Projectiles/Bullets/zbullet.png");
        setLife(life);
        setSpeed(19);
        setNumTargets(-1);
        setDamage(150);
    }
    public void die(){
        explodeOn(50, getDamage());
        super.die();
    }
}
