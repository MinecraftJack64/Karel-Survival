package com.karel.game;
import java.util.List;

/**
 * Write a description of class PetMole here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PetMole extends Weapon
{
    private static final int ult = 1800;
    private Mole zap;
    public void fire(){
        if(zap.getWorld()!=null){
            if(zap.inUlt()){
                zap.stopUlt();
            }
            zap.attackAt(getHand().getTargetX(), getHand().getTargetY(), getAttackUpgrade()==1, getUltUpgrade()==1);
        }
        //show the lightning
        //Sounds.play("electicity");
    }
    public void fireUlt(){
        if(zap.getWorld()!=null){
            if(zap.inUlt()){
                cancelUltReset();
            }
            zap.startUlt();
        }
    }
    public int getUlt(){
        return ult;
    }
    public void onGadgetActivate(){
        if(zap!=null){
            zap.gadget();
        }
    }
    public boolean isUsingGadget(){
        return zap!=null&&!zap.canGadget();
    }
    public int defaultGadgets(){
        return 7;
    }
    public void reload(){
        if(zap.getWorld()!=null)
            zap.target(getHand().getTargetX(), getHand().getTargetY());
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().addObject(zap, getHolder().getRealX(), getHolder().getRealY());
    }
    public void unequip(){
        if(zap.inUlt()){
            zap.stopUlt();
        }
        getHolder().getWorld().removeObject(zap);
        super.unequip();
    }
    public PetMole(ItemHolder actor){
        super(actor);
        zap = new Mole(getHolder());
    }
    public String getName(){
        return "Mole";
    }
    public int getRarity(){
        return 3;
    }
}





