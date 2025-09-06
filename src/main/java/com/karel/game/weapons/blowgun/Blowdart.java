package com.karel.game.weapons.blowgun;
import java.util.HashMap;

import com.karel.game.Bullet;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.PoisonEffect;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Blowdart extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    private HashMap<GridEntity, Integer> poisonscores;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public Blowdart(double rotation, HashMap<GridEntity, Integer> scores, GridObject source)
    {
        super(rotation, source);
        setSpeed(17);
        setLife(20);
        setDamage(50);
        poisonscores = scores;
    }
    public void doHit(GridEntity targ){
        int poisonmultiplier = 0;
        if(poisonscores!=null){
            if(poisonscores.containsKey(targ)){
                poisonmultiplier = poisonscores.get(targ);
                if(poisonmultiplier<11)poisonscores.put(targ, poisonmultiplier+1);
                else poisonscores.remove(targ);
            }else{
                poisonscores.put(targ, 1);
            }
        }
        super.doHit(targ);
        targ.applyEffect(new PoisonEffect(30+poisonmultiplier*5, 50, 4, this));
    }
}
