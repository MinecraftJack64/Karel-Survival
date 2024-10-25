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
    private boolean hasspear = true;
    private int ultchargecooldown = 0;
    public void fire(){//one full ammo deals 350 damage
        if (hasspear) 
        {
            ThrownSpear bullet = new ThrownSpear(false, getHolder(), this, getHolder().getTargetRotation(), getHolder());
            getHolder().addObjectHere(bullet);
            //bullet.move ();
            hasspear = false;
            Sounds.play("lifestealshoot");
            //should throwing remove ult?
        }
    }
    public void fireUlt(){
        if(hasspear){//swing in front of you
            //SwingingSpear
            //hasspear = false;
        }else if(spear!=null){//pull spear back
            spear.callBack();
        }else{
            cancelUltReset();
        }
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        chargeUlt(1);
    }
    public void notifySpear(LandedSpear thing){
        spear = thing;
    }
    public void removeSpear(){
        spear = null;
    }
    public boolean throwSpear(){
        return getHolder().isAttacking();
    }
    public void returnSpear(HashMap<GridEntity, Integer> s){
        if(s!=null&&!s.isEmpty()){
            //slam attack
            SlammingSpear bullet = new SlammingSpear(s, this, getHolder());
            getHolder().addObjectHere(bullet);
        }else{
            hasspear = true;
        }
    }
    public SpearWeapon(GridObject actor){
        super(actor);
    }
    public String getName(){
        return "Spear";
    }
    public int getRarity(){
        return 6;
    }
}
