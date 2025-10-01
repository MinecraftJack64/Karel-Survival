package com.karel.game.weapons.farmer;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Sounds;
import com.karel.game.gridobjects.collectibles.Collectible;

/**
 * a collectible usually dropped from zombies when killed that can be collected by the player, healing them
 * 
 * @author MinecraftJack64
 * @version 1.0
 */
public class HarvestBeeper extends Collectible
{
    private int healing = 50;
    private Farmer myFarmer;
    public HarvestBeeper(Farmer farmer)
    {
        myFarmer = farmer;
        setRange(150);
        setSpeed(12);
        setCooldown(5);
    }
    public GridObject getTarget(){
        return myFarmer.getHolder();
    }
    public void collect(GridObject targ){
        heal(((GridEntity)targ), healing);
        myFarmer.notifyBeeperCollect();
        super.collect(targ);
        Sounds.play("HarvestBeeper.collect");
    }
}
