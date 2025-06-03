package com.karel.game.weapons.paintgun;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.SpeedPercentageEffect;

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
    public void doHit(GridEntity other){
        super.doHit(other);
        other.applyEffect(new SpeedPercentageEffect(0.5, 90, getSource()));
    }
}
