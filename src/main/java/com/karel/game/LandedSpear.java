package com.karel.game;
import java.util.HashMap;

/**
 * represents the Spear weapon when it is on the ground. When collected by the player, it returns to their hand
 * 
 * @author MinecraftJack64
 * @version 1.0
 */
public class LandedSpear extends Collectible
{
    private SpearWeapon myspear;
    private HashMap<GridEntity, Integer> hs;
    public LandedSpear(SpearWeapon sp, HashMap<GridEntity, Integer> scores)
    {
        myspear = sp;
        sp.notifySpear(this);
        hs = scores;
        setRange(60);
        setTeam(myspear.getHolder().getTeam());
    }
    public GridObject getTarget(){
        return myspear.getHolder();
    }
    public void kAct(){
        int ct = 3;
        for(GridEntity g:getGEsInRange(60)){
            if(isAggroTowards(g)){
                damage(g, 1);
                ct--;
            }
            if(ct<=0){
                break;
            }
        }
        super.kAct();
    }
    public void collect(GridObject targ){
        myspear.returnSpear(hs);
        myspear.removeSpear();
        myspear.enterHand();
        super.collect(targ);
        Sounds.play("LandedSpear.collect");
    }
    public void callBack(){
        ThrownSpear bullet = new ThrownSpear(true, myspear.getHolder(), myspear, face(myspear.getHolder(), false), this);
        bullet.injectScores(hs);
        myspear.removeSpear();
        addObjectHere(bullet);
        super.die();
    }
    public void notifyDamage(GridEntity target, int amt){
        if(!myspear.getHolder().isDead()){
            myspear.getHolder().notifyDamage(target, amt);
        }else{
            super.notifyDamage(target, amt);
        }
    }
}


