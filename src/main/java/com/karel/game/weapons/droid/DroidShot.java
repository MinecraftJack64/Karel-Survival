package com.karel.game.weapons.droid;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.PullEffect;
import com.karel.game.gridobjects.hitters.Bullet;

public class DroidShot extends Bullet
{
    
    public DroidShot(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Weapons/shotgun/proj.png");
        scaleTexture(25);
        setRotation(rotation+45);
        setSpeed(17);
        setLife(12);
        setDamage(7);
    }
    public void doHit(GridEntity g){
        g.applyEffect(new PullEffect(getSource().face(g, false), getSpeed()/2, getLife()/2, this));
        super.doHit(g);
    }
}
