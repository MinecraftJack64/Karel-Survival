package com.karel.game.gridobjects.gridentities.zombies.gatherer;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.collectibles.Beeper;

/**
 * a collectible usually dropped from zombies when killed that can be collected by the player, healing them
 * 
 * @author MinecraftJack64
 * @version 1.0
 */
public class Zombeeper extends Beeper
{
    private int healing = 50;
    private GridEntity target;
    public Zombeeper(GridEntity targ)
    {
        super(0);
        target = targ;
        setRange(1000);
    }
    public GridObject getTarget(){
        return target;
    }
    public void collect(GridObject targ){
        heal(((GridEntity)targ), healing);
        super.collect(targ);
    }
}
