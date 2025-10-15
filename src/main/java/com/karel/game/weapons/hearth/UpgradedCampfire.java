package com.karel.game.weapons.hearth;

import com.karel.game.GridEntity;
import com.karel.game.effects.BurnEffect;

/**
 * A campfire that constantly heals allies and damages enemies.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class UpgradedCampfire extends Campfire
{
    public UpgradedCampfire(GridEntity hive)
    {
        super(hive);
    }
    public void notifyWorldAdd(){
        super.notifyWorldAdd();
        explodeOn(150, (g)->{
            if(isAggroTowards(g))g.applyEffect(new BurnEffect(100, 30, 5, this));
            else if(isAlliedWith(g))heal(g, 350);
        }, null);
    }
    public void die(){
        explodeOn(150, (g)->{
            if(isAggroTowards(g))g.applyEffect(new BurnEffect(100, 30, 5, this));
            else if(isAlliedWith(g))heal(g, 350);
        }, null);
        super.die();
    }
    public String getPetID(){
        return "hearth-campfire+";
    }
}
