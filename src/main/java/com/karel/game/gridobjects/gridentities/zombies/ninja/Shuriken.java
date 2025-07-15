package com.karel.game.gridobjects.gridentities.zombies.ninja;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.ZBullet;
/**
 * A shuriken that can be thrown by a NinjaZombie.
 * 
 * @author Poul Henriksen
 * @version 1.0
 */
public class Shuriken extends ZBullet
{
    
    public Shuriken(double rotation, GridObject source)
    {
        super(rotation, source);
        setLife(10);
        setDamage(100);
        setSpeed(17);
        setImage("karo.png");
    }
    public void animate()
    {
        setRotation(getRotation()+30);
    }
}
