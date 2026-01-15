package com.karel.game.gridobjects.collectibles;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.Tinkerer;
import com.karel.game.Greenfoot;

/**
 * A weapon fragment that can be collected by the player, increases the players weapon frag count
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TinkerWeaponFrag extends WeaponFrag
{
    private Tinkerer lookfor;
    public TinkerWeaponFrag(){
        setImage("Objects/WeaponFrags/"+Greenfoot.getRandomNumber(8)+".png");
        setRange(10000);
        setActive(false);
    }
    public void collect(GridObject targ){
        ((Tinkerer)targ).receiveFrag();
        getWorld().removeObject(this);
    }
    public void setTinkerer(Tinkerer t){
        lookfor = t;
    }
    public GridObject getTarget(){
        return lookfor;
    }
}
