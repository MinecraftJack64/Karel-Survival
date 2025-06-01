package com.karel.game.weapons.critters;

import com.karel.game.Bullet;
import com.karel.game.GridEntity;
import com.karel.game.HealCharge;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class FlyingCritter extends Bullet implements ICritter
{
    private LilCritters master;
    private int id;
    public FlyingCritter(double rotation, GridEntity source, LilCritters spawner, int id)
    {
        super(rotation, source);
        setLife(25);
        setSpeed(20);
        setDamage(100);
        master = spawner;
        this.id = id;
    }
    public void gadget(){
        addObjectHere(new HealCharge(0, (GridEntity)getSource(), ((GridEntity)getSource()).getMaxHealth()/10));
        setPower(1.5);
    }
    public void die(){
        Critter spawn = new Critter(id, master, (GridEntity)getSource());
        addObjectHere(spawn);
        master.notifyCritterPhaseChange(id, spawn);
        super.die();
    }
}
