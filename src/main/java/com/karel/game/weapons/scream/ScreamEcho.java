package com.karel.game.weapons.scream;

import com.karel.game.Bullet;
import com.karel.game.GridObject;
public class ScreamEcho extends Bullet
{
    public ScreamEcho(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(20);
        setRealRotation(rotation);
        setLife(10);
        setDamage(20);
        setNumTargets(1);
    }
    public boolean covertDamage(){
        return true;
    }
}