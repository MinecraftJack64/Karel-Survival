package com.karel.game.weapons.jackolantern;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Sounds;
import com.karel.game.gridobjects.collectibles.Collectible;

/*
 * A seed dropped by the JackOLantern weapon. Heals the collector and turns into a PumpkinSprout
 */
public class PumpkinSeeds extends Collectible {
    private int healing = 100;
    private GridObject my;
    public PumpkinSeeds(GridObject source)
    {
        this.my = source;
    }
    public void collect(GridObject targ){
        my.heal((GridEntity)targ, healing);
        addObjectHere(new PumpkinSprout(targ.getRotation(), my));
        super.collect(targ);
        Sounds.play("ChameleonOrb.collect");
    }
    @Override
    public String getObjectID(){
        return "pumpkinseeds";
    }
}
