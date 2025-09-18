package com.karel.game.weapons.scream;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;
public class ScreamEcho extends Bullet
{
    public ScreamEcho(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Weapons/scream/proj2.png");
        scaleTexture(60);
        setRotation(rotation);
        setSpeed(20);
        setRotation(rotation);
        setLife(10);
        setDamage(20);
        setNumTargets(1);
    }
    public boolean covertDamage(){
        return true;
    }
}