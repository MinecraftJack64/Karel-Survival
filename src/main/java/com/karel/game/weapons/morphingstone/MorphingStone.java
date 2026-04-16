package com.karel.game.weapons.morphingstone;

import com.karel.game.GridEntity;
import com.karel.game.ItemHolder;
import com.karel.game.PercentageShield;
import com.karel.game.Sounds;
import com.karel.game.shields.ShieldID;
import com.karel.game.trackers.AmmoManager;
import com.karel.game.trackers.SimpleAmmoManager;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Necromancer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MorphingStone extends Weapon
{
    private static final int ult = 1000;
    private int charges = 0; // 18
    GuardianBeast beast;
    public void fire(){//one full ammo deals 350 damage
        if (getAmmo().hasAmmo()) 
        {
            getHolder().addObjectHere(charges<=18?new EssenceStone(getHand().getTargetRotation(), getHolder(), this):new Morpher(getHand().getTargetRotation(), getHolder(), this));
            getAmmo().useAmmo();
            Sounds.play("lifestealshoot");
        }
    }
    public void fireUlt(){
        beast = new GuardianBeast(getHolder(), getHolder(), true, this);
        newSpecial(beast.getDuration(), beast.getDuration());
        getHolder().addObjectHere(beast);
        if(getUltUpgrade()==1){
            beast.applyShield(new PercentageShield(new ShieldID(this), 0.5, 90));
        }
    }
    public int getUlt(){
        return ult;
    }
    public void update(){
        if(beast!=null&&(beast.isDead()||!beast.getTeam().equals(getHolder().getTeam()))){
            beast = null;
            disableSpecial();
        }
        if(beast!=null){
            updateSpecial(beast.getDuration());
        }
    }
    public boolean notifyHypno(GridEntity targ){
        if(charges<=18||targ.getPercentHealth()-200>0.5){
            return false;
        }else{
            charges = 0;
        }
        beast = new GuardianBeast(getHolder(), targ, getAttackUpgrade()==1, null);
        targ.addObjectHere(beast);
        newSpecial(beast.getDuration(), beast.getDuration());
        return true;
    }
    public void setSpecialAmmo(AmmoManager m){
        if(m==null){
            setAmmo(new SimpleAmmoManager(60, 0));
        }else{
            setAmmo(m);
        }
    }
    public void notifyHit(){
        charges++;
    }
    public void notifyKill(GridEntity t){
        charges+=6;
    }
    public int defaultGadgets(){
        return 1;
    }
    public boolean canActivateGadget(){
        return canActivateGadget()&&beast!=null;
    }
    public void onGadgetActivate(){
        setGadgetTimer(beast.getDuration());
    }
    public void equip(){
        super.equip();
        if(beast!=null){
            newSpecial(beast.getMaxDuration(), beast.getDuration());
        }
    }
    public MorphingStone(ItemHolder actor){
        super(actor);
        setAmmo(new SimpleAmmoManager(60, 1));
    }
    public String getName(){
        return "Morphing Stone";
    }
    public int getRarity(){
        return 6;
    }
}




