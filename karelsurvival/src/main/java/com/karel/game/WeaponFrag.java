package com.karel.game;
/**
 * A weapon fragment that can be collected by the player, increases the players weapon frag count
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WeaponFrag extends Collectible
{
    public void collect(GridObject targ){
        getWorld().getGame().collectWeaponFrag();
        Sounds.play("weaponfragcollect");
        super.collect(targ);
    }
    public GridObject getTarget(){
        return getWorld().getPlayer();
    }
}
