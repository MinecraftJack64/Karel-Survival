package com.karel.game.gridobjects.collectibles;

import com.karel.game.Game;
import com.karel.game.GridObject;
import com.karel.game.Sounds;
import com.karel.game.particles.WeaponFragComponent;
import com.karel.game.Greenfoot;

/**
 * A weapon fragment that can be collected by the player, increases the players weapon frag count
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WeaponFrag extends Collectible
{
    public WeaponFrag(){
        setImage("Objects/WeaponFrags/"+Greenfoot.getRandomNumber(8)+".png");
        setRange(50);
    }
    public void notifyWorldAdd(){
        super.notifyWorldAdd();
        for(int i = 0; i < 3+Greenfoot.getRandomNumber(2); i++){
            WeaponFragComponent c = new WeaponFragComponent(this);
            double deg = Greenfoot.getRandomNumber(360);
            double dist = Greenfoot.getRandomNumber(30);
            getWorld().addObject(c, getX()+getBranchX(deg, dist), getY()+getBranchY(deg, dist));
        }
    }
    public void collect(GridObject targ){
        Game.getGame().collectWeaponFrag();
        Sounds.play("weaponfragcollect");
        super.collect(targ);
    }
    public GridObject getTarget(){
        return getWorld().getPlayer();
    }
}
