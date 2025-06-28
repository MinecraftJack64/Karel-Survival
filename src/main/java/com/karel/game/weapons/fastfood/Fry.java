package com.karel.game.weapons.fastfood;

import com.karel.game.Bullet;
import com.karel.game.GridObject;
import com.karel.game.Shield;
import com.karel.game.weapons.ShieldID;

public class Fry extends Bullet
{
    private int damageIncrease = 0;
    public Fry(double rotation, GridObject source, boolean isSuper)
    {
        super(rotation, source);
        setImage("Weapons/fastfood/proj.png");
        scaleTexture(20);
        //setRealRotation(rotation+45);
        setSpeed(isSuper?22:17);
        setLife(13);
        setDamage(isSuper?80:20);
        damageIncrease = isSuper?0:4;
    }
    public void applyPhysics(){
        super.applyPhysics();
        setDamage(getDamage() + damageIncrease);
    }
}
