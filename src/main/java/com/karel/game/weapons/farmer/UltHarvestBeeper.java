package com.karel.game.weapons.farmer;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Sounds;
import com.karel.game.gridobjects.collectibles.Collectible;

/**
 * An invisible(TODO) beeper dropped by the Farmer weapon.
 * 
 * @author MinecraftJack64
 * @version 1.0
 */
public class UltHarvestBeeper extends Collectible
{
    private int healing = 50;
    private Farmer myFarmer;
    public UltHarvestBeeper(Farmer farmer)
    {
        myFarmer = farmer;
        setRange(150);
        setSpeed(12);
        setCooldown(10);
    }
    public GridObject getTarget(){
        return myFarmer.getHolder();
    }
    public void collect(GridObject targ){
        heal(((GridEntity)targ), healing);
        myFarmer.notifyBeeperCollect();
        super.collect(targ);
        Sounds.play("UltHarvestBeeper.collect");
    }
    @Override
    public String getObjectID(){
        return "ultharvestbeeper";
    }
}
