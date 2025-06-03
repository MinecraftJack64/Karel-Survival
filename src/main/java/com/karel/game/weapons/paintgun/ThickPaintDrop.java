package com.karel.game.weapons.paintgun;
import com.karel.game.GridObject;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ThickPaintDrop extends PaintDrop
{
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;

    public ThickPaintDrop(double rotation, GridObject source)
    {
        super(rotation, false, source);
        setSpeed(20);
    }
}
