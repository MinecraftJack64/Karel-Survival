package com.karel.game.gridobjects.gridentities.zombies.jogger;

import com.karel.game.Greenfoot;
import com.karel.game.StrikeSurvivalShield;
import com.karel.game.shields.ShieldID;

/**
 * Allow the holder to survive a certain number of lethal hits with 1 health. Has 50% chance to increase survival times
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PropagatingStrikeSurvivalShield extends StrikeSurvivalShield
{
    public PropagatingStrikeSurvivalShield(ShieldID myG, int health){
        super(myG, propagate(health));
    }
    public static int propagate(int health){
        while(Greenfoot.getRandomNumber(2)==0){
            health++;
        }
        return health;
    }
}


