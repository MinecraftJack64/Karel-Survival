package com.karel.game.weapons.lymphcannon;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class SmallAntibody extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    private String target;
    public SmallAntibody(double rotation, String targ, GridObject source)
    {
        super(rotation, source);
        setSpeed(17);
        setLife(20);
        setDamage(4);
        setNumTargets(-1);
        target = targ;
        if(targ==null)setImage("Weapons/lymphcannon/proj.png");
        else setImage("Weapons/lymphcannon/projTarg.png");
        setRotation(getRotation()+90);
    }
    public void doHit(GridEntity targ){
        if(target!=null&&target.equals(targ.getEntityID())){
            damage(targ, getDamage()*4);
        }
        super.doHit(targ);
    }
}
