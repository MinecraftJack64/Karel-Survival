package com.karel.game;
import java.util.HashMap;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class UpgradedBlowdart extends Blowdart
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    private HashMap<GridEntity, Integer> poisonscores;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public UpgradedBlowdart(double rotation, HashMap<GridEntity, Integer> scores, GridObject source)
    {
        super(rotation, scores, source);
        poisonscores = scores;
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        int poison = 0;
        if(poisonscores!=null&&poisonscores.containsKey(targ)){
            poison = poisonscores.get(targ);
        }
        targ.applyEffect(poison>=11?new StunEffect(200, this).setCollisionProtocol(2):new SpeedPercentageEffect(1-poison/11, 200, this).setCollisionProtocol(2));
    }
}
