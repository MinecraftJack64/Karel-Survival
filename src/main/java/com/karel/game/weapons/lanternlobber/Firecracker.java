package com.karel.game.weapons.lanternlobber;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.AerialBullet;

/**
 * A straight bullet shot from the drone weapon
 * 
 * @author Poul Henriksen
 */
public class Firecracker extends AerialBullet
{
    public static final int range = 50;
    public Firecracker(double rotation, double height, double speed, double reduction, GridObject source)
    {
        super(rotation, height, speed, reduction, source);
        setDamage(75);
        setRange(range);
    }
}
