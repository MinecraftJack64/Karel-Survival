package com.karel.game.weapons.grenade;

import com.karel.game.GridObject;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class UpgradedGrenade extends Grenade
{
    public UpgradedGrenade(double rotation, double targetdistance, double height, GridObject source)
    {
        super(rotation, targetdistance, height, source);
    }
    public void die(){
        for(int i = 0; i <= 360; i+=30){
            ExtraGrenadeFragment wb = new ExtraGrenadeFragment(i+getDirection()+15, this);
            addObjectHere(wb);
        }
        super.die();
    }
}
