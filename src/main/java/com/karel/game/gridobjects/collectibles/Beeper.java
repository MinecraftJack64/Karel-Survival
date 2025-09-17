package com.karel.game.gridobjects.collectibles;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Sounds;

/**
 * a collectible usually dropped from zombies when killed that can be collected by the player, healing them
 * 
 * @author MinecraftJack64
 * @version 1.0
 */
public class Beeper extends Collectible
{
    private int animFrame = 0; // 120
    private int healing = 50;
    private int xp;
    public Beeper(int xp)
    {
        this.xp = xp;
    }
    public GridObject getTarget(){
        return getWorld().getPlayer();
    }
    public void animate(){
        animFrame++;
        if(animFrame>90){
            setImage("Objects/beeper1.png");
        }else{
            setImage("Objects/beeper0.png");
        }
        if(animFrame>120)animFrame = 0;
        super.animate();
    }
    public void onCollecting(){
        animFrame = 90;
    }
    public void collect(GridObject targ){
        heal(((GridEntity)targ), healing);
        super.collect(targ);
        Sounds.play("Beeper.collect"+xp);
    }
}
