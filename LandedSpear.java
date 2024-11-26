import greenfoot.*;
import java.util.HashMap;

/**
 * Write a description of class LandedSpear here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LandedSpear extends Collectible
{
    /** The damage this bullet will deal */
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
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
    public void kAct(){
        int ct = 3;
        for(GridEntity g:getGEsInRange(60)){
            if(isAggroTowards(g)){
                g.hit(1, this);
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
