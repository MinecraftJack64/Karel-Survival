package com.karel.game.weapons.hearth;

import com.karel.game.Dropper;
import com.karel.game.GridObject;
import com.karel.game.particles.FlameTrail;

/**
 * Drops a campfire that heals allies and damages enemies.
 * Used by the Hearth weapon.
 * 
 * @author Poul Henriksen
 */
public class CampfireDropper extends Dropper
{
    public CampfireDropper(double rotation, double targetdistance, double height, GridObject toSpawn, GridObject source)
    {
        super(rotation, targetdistance, height, toSpawn, source);
        setImage(!(toSpawn instanceof UpgradedCampfire)?"Weapons/hearth/projUlt.png":"Weapons/hearth/projUltUpgrade.png");
        scaleTexture(50);
    }
    public void applyPhysics(){
        super.applyPhysics();
        FlameTrail ft = new FlameTrail(1);
        addObjectHere(ft);
        ft.setHeight(getHeight());
    }
}
