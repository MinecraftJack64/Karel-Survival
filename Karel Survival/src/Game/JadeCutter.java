package Game;
import greenfoot.*;
/**
 * Write a description of class JadeCutter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class JadeCutter extends Weapon
{
    private static final int ult = 1680;
    private static final int reloadTime = 15;
    private int ammo = 15;
    private boolean nextUltUpgraded;
    private StaticJadeBlade lasso;
    public void fire(){//one full ammo deals 350 damage
        if (lasso!=null&&lasso.canAttack()) 
        {
            lasso.fire(getHand().getTargetRotation(), getAttackUpgrade()==1);
            if(continueGadget())setContinueGadget(false);
            lasso = null;
        }
    }
    public void fireUlt(){
        if(lasso!=null){
            lasso.upgrade(getUltUpgrade()==1);
            if(getUltUpgrade()==1)nextUltUpgraded = true;
        }else{
            cancelUltReset();
        }
    }
    public int getUlt(){
        return ult;
    }
    public void onGadgetActivate(){
        if(lasso!=null){
            lasso.gadget();
            setContinueGadget(true);
        }
    }
    public int defaultGadgets(){
        return 4;
    }
    public void reload(){
        if(lasso==null){
            if(ammo>=reloadTime){
                lasso = new StaticJadeBlade(getHolder(), nextUltUpgraded);
                if(nextUltUpgraded)nextUltUpgraded = false;
                getHolder().addObjectHere(lasso);
                getHolder().mount(lasso, 0, 0);
                ammo = 0;
            }else ammo++;
        }else if(!lasso.isInWorld()){
            if(continueGadget())setContinueGadget(false);
            lasso = null;
        }
    }
    public JadeCutter(ItemHolder actor){
        super(actor);
    }
    public void equip(){
        super.equip();
        if(lasso==null&&ammo>=reloadTime){
            lasso = new StaticJadeBlade(getHolder(), nextUltUpgraded);
            if(nextUltUpgraded)nextUltUpgraded = false;
            ammo = 0;
        }
        if(lasso!=null&&!lasso.isInWorld()){
            getHolder().addObjectHere(lasso);
            getHolder().mount(lasso, 0, 0);
        }
    }
    public void unequip(){
        if(lasso!=null&&lasso.isInWorld()){
            getHolder().getWorld().removeObject(lasso);
            getHolder().unmount(lasso);
        }
        super.unequip();
    }
    public String getName(){
        return "Jade Cutter";
    }
    public int getRarity(){
        return 3;
    }
}




