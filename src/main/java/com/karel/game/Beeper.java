package com.karel.game;
/**
 * a collectible usually dropped from zombies when killed that can be collected by the player, healing them
 * 
 * @author MinecraftJack64
 * @version 1.0
 */
public class Beeper extends Collectible
{
    private int healing = 50;
    private int xp;
    public Beeper(int xp)
    {
        this.xp = xp;
    }
    public GridObject getTarget(){
        return getWorld().getPlayer();
    }
    public void collect(GridObject targ){
        heal(((GridEntity)targ), healing);
        super.collect(targ);
        Sounds.play("Beeper.collect");
    }
}
