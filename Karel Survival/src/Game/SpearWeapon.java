package Game;
import greenfoot.*;
import java.util.HashMap;

/**
 * Write a description of class SpearWeapon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SpearWeapon extends Weapon
{
    private static final int ult = 500;
    private LandedSpear spear;
    private boolean hasspear = true, spearinhand = true;
    private int ultchargecooldown = 0;
    private int reloadDelay = 0;
    public void fire(){//one full ammo deals 350 damage
        if(continueUse()){
            reloadDelay--;
            if(reloadDelay<=0){
                SpearlessPunch sp = new SpearlessPunch(getHand().getTargetRotation()+15, getHolder());
                getHolder().addObjectHere(sp);
                setContinueUse(false);
                setPlayerLockRotation(false);
                reloadDelay = 30;
            }
        }
        else if (hasspear&&reloadDelay<=0) 
        {
            ThrownSpear bullet = new ThrownSpear(false, getHolder(), this, getHand().getTargetRotation(), getHolder());
            getHolder().addObjectHere(bullet);
            //bullet.move ();
            hasspear = false;
            Sounds.play("lifestealshoot");
            reloadDelay = 45;
            //should throwing remove ult?
        }else if(!spearinhand&&reloadDelay<=0){
            //melee attack
            SpearlessPunch sp = new SpearlessPunch(getHand().getTargetRotation()-15, getHolder());
            getHolder().addObjectHere(sp);
            setContinueUse(true);
            setPlayerLockRotation(true);
            reloadDelay = 9;
        }
    }
    public void fireUlt(){
        if(hasspear){//swing in front of you
            WardingSpear ws = new WardingSpear(getHand().getTargetRotation(), false, this, getHolder());
            getHolder().addObjectHere(ws);
            hasspear = false;
        }else if(spear!=null){//pull spear back
            spear.callBack();
        }else{
            cancelUltReset();
        }
    }
    public int getUlt(){
        return ult;
    }
    public void leaveHand(){
        spearinhand = false;
        resetUltCharge();
    }
    public void enterHand(){
        spearinhand = true;
    }
    public void reload(){
        chargeUlt(1);
        if(reloadDelay>0){
            reloadDelay--;
        }
    }
    public void notifySpear(LandedSpear thing){
        spear = thing;
    }
    public void removeSpear(){
        spear = null;
    }
    public boolean throwSpear(){
        return getHand().isAttacking();
    }
    public void returnSpear(HashMap<GridEntity, Integer> s){
        if(s!=null&&!s.isEmpty()){
            //slam attack
            SlammingSpear bullet = new SlammingSpear(s, this, getHolder());
            getHolder().addObjectHere(bullet);
        }else{
            hasspear = true;
            getHolder().heal(getHolder(), 200);
        }
    }
    public SpearWeapon(ItemHolder actor){
        super(actor);
    }
    public String getName(){
        return "Spear";
    }
    public int getRarity(){
        return 6;
    }
}




