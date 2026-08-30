package com.karel.game.weapons.grimoire;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.collectibles.Collectible;

/**
 * a collectible dropped by the Chameleon weapon when the player kills a zombie by pulling them, changes the Chameleon's color when collected by the player
 * 
 * @author MinecraftJack64
 * @version 1.0
 */
public class XPOrb extends Collectible
{
    private int xp;
    private Grimoire mygrim;
    public XPOrb(Grimoire mygrim, int xp)
    {
        this.mygrim = mygrim;
        this.xp = xp;
        setCooldown(15);
        setImage("lightKaro.png");
    }
    public GridObject getTarget(){
        return mygrim.getHolder();
    }
    public void collect(GridObject targ){
        mygrim.levelUp(xp);
        super.collect(targ);
    }
    @Override
    public String getObjectID(){
        return "grimoirexporb";
    }
}
