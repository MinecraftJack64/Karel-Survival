package com.karel.game.gridobjects.gridentities.zombies.steak;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Sounds;
import com.karel.game.effects.PoisonEffect;
import com.karel.game.effects.PowerPercentageEffect;
import com.karel.game.gridobjects.collectibles.Collectible;

/**
 * a collectible usually dropped from zombies when killed that can be collected by the player, healing them
 * 
 * @author MinecraftJack64
 * @version 1.0
 */
public class Steak extends Collectible
{
    private int healing = 100;
    private int life = 150;
    public Steak(GridObject source){
        setImage("Projectiles/Throws/steak.png");
        scaleTexture(50);
        inherit(source);
        setRange(100);
    }
    public boolean isPotentialTarget(GridEntity t){return isAlliedWith(t)||isAggroTowards(t);}
    public void update(){
        super.update();
        life--;
        if(life<=0){
            die();
        }
    }
    public void collect(GridObject targ){
        GridEntity t = (GridEntity)targ;
        if(isAlliedWith(t)){
            heal(t, healing);
            t.applyEffect(new PowerPercentageEffect(1.25, 90, this));
        }
        else if(isAggroTowards(t))t.applyEffect(new PoisonEffect(healing/4, 30, 4, this));
        super.collect(targ);
        Sounds.play("Steak.collect");
    }
}
