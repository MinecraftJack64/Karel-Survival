package com.karel.game.weapons.drone;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.AerialBullet;
import com.karel.game.trackers.ComboTracker;

/**
 * A straight bullet shot from the drone weapon
 * 
 * @author Poul Henriksen
 */
public class DroneBullet extends AerialBullet
{
    ComboTracker combo;
    public DroneBullet(double rotation, double height, double speed, double reduction, GridObject source)
    {
        super(rotation, height, speed, reduction, source);
        setDamage(75);
        setRange(40);
    }
    public DroneBullet(double rotation, double height, double speed, double reduction, GridObject source, ComboTracker ct)
    {
        this(rotation, height, speed, reduction, source);
        combo = ct;
    }
    public void die(){
        if(combo!=null)setDamage(getDamage()+combo.get());
        super.die();
        if(getHitStory().size()>0&&combo!=null){
            combo.change(15);
        }
    }
}
