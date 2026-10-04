package com.karel.game.weapons.grimoire;

import com.karel.game.Greenfoot;
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
    private int animFrame = 0;
    private int xp;
    private Grimoire mygrim;
    public XPOrb(Grimoire mygrim, int xp)
    {
        this.mygrim = mygrim;
        this.xp = xp;
        setCooldown(15);
        setImage("button-green.png");
        scaleTexture(30);
        initiateJump(Greenfoot.getRandomNumber(360), Greenfoot.getRandomNumber(50), 30);
    }
    public GridObject getTarget(){
        return mygrim.getHolder();
    }
    public void collect(GridObject targ){
        mygrim.levelUp(xp);
        super.collect(targ);
    }
    public void animate(){
        animFrame++;
        int f = Math.abs(animFrame)+128;
        setTint(f, f, f);
        if(animFrame>128)animFrame = -128;
        super.animate();
    }
    public double getRange(){
        return 100;
    }
    @Override
    public String getObjectID(){
        return "grimoirexporb";
    }
}
