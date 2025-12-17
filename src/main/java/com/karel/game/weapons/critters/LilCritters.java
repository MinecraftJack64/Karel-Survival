package com.karel.game.weapons.critters;

import com.karel.game.ArmorShield;
import com.karel.game.GridObject;
import com.karel.game.ItemHolder;
import com.karel.game.Shield;
import com.karel.game.shields.ShieldID;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class LilCreatures here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
//TODO: Make custom stats bar showing critters state and health
public class LilCritters extends Weapon
{
    private static final int ult = 1200;
    private ICritter[] ic = new ICritter[7];//0-5: clockwise starting from front, 6: center
    private Shield myS;
    private double regenshield = 90;//shield
    private double regencritter = 120;//cooldown for regeneration
    private int focus = 0;//which critter is being regenerated
    private int reloadtime = 7;//for firing critters
    private int reload = 0;
    private int reloadslime = 40;//for regular critter slime
    private ShieldID msshield = new ShieldID(this, "lilcreatures mucus shield");
    public void fire(){
        if(reload>reloadtime&&fireCritter()){
            reload = 0;
        }else if(reload>reloadslime){
            reload = 0;
            CritterSlime cs = new CritterSlime(getHand().getTargetRotation(), getHolder());
            getHolder().addObjectHere(cs);
            CritterSlime cs1 = new CritterSlime(getHand().getTargetRotation()-30, getHolder());
            getHolder().addObjectHere(cs1);
            CritterSlime cs2 = new CritterSlime(getHand().getTargetRotation()+30, getHolder());
            getHolder().addObjectHere(cs2);
        }
    }

    public void fireUlt(){
        //toggle critter callbacks
        boolean used = false;
        for(ICritter i: ic){
            if(i instanceof Critter&&!((Critter)i).isDead()){
                ((Critter)i).toggleCallBack();
                used = true;
            }
        }
        if(!used){
            cancelUltReset();
        }
    }

    public int getUlt(){
        return ult;
    }

    public void reload(double speed){
        cleanLists();
        if(getHolder().hasShield(msshield)){
            processRegeneration(speed);
        }else{
            if(regenshield==90){
                shieldBroke();
            }
            regenshield-= speed;
            if(regenshield<=0){
                regenshield = 90;
                myS = new MucusShield(msshield, 600);
                getHolder().applyShield(myS);
            }
        }
        reload++;
    }
    
    public void cleanLists(){//get rid of dead critters
        for(int i = 0; i < ic.length; i++){
            if(ic[i] instanceof Critter){
                if(((Critter)ic[i]).isDead()){
                    ic[i] = null;
                }
            }
        }
    }
    public boolean fireCritter(){
        int nfocus = focus-1;
        if(nfocus<0){
            nfocus = 6;
        }
        while(!(ic[focus] instanceof PassiveCritter)&&focus!=nfocus){
            focus++;
            if(focus>=7){
                focus = 0;
            }
        }
        if(ic[focus] instanceof PassiveCritter){
            ((PassiveCritter)ic[focus]).deploy();
            return true;
        }else{
            return false;//failed because no critters
        }
    }
    public void processRegeneration(double speed){
        int nfocus = focus-1;
        if(nfocus<0){
            nfocus = 6;
        }
        while(ic[focus]!=null&&focus!=nfocus){
            focus++;
            if(focus>=7){
                focus = 0;
            }
        }
        if(ic[focus]==null){
            regencritter-= speed;
            if(regencritter<=0){
                regencritter = 120;
                regenerate(focus);
            }
        }
    }
    public void regenerate(int id){
        int am = getSyncAmmo();
        if(id<6){
            PassiveCritter c = new PassiveCritter(id, id*60, 35, this, getHand());
            ic[id] = c;
            getHolder().addObjectHere(c);
        }else{
            PassiveCritter c = new PassiveCritter(id, 0, 0, this, getHand());
            ic[id] = c;
            getHolder().addObjectHere(c);
        }
        ((PassiveCritter)ic[id]).syncAmmo(am);
    }
    public void onGadgetActivate(){
        for(int i = 0; i < ic.length; i++){
            if(ic[i]!=null){
                ic[i].gadget();
            }
        }
    }
    public int defaultGadgets(){
        return 1;
    }
    public int getSyncAmmo(){
        for(ICritter i: ic){
            if(i instanceof PassiveCritter){
                return ((PassiveCritter)i).getAmmo();
            }
        }
        return 0;
    }
    public void shieldBroke(){
        //kill passive critters and deter any returning critters
        for(int i = 0; i < ic.length; i++){
            if(ic[i] instanceof PassiveCritter){
                getHolder().getWorld().removeObject((GridObject)ic[i]);
                ic[i] = null;
            }else if(ic[i] instanceof Critter){
                if(((Critter)ic[i]).isCalledBack()){
                    ((Critter)ic[i]).toggleCallBack();
                }
            }
        }
    }
    public void removePassiveCritters(){
        for(int i = 0; i < ic.length; i++){
            if(ic[i] instanceof PassiveCritter){
                getHolder().getWorld().removeObject((GridObject)ic[i]);
            }
        }
    }
    public void reinstatePassiveCritters(){
        for(int i = 0; i < ic.length; i++){
            if(ic[i] instanceof PassiveCritter){
                getHolder().addObjectHere((GridObject)ic[i]);
            }
        }
    }
    public void notifyCritterPhaseChange(int id, ICritter n){
        ic[id] = n;
    }

    public void equip(){
        super.equip();
        if(myS!=null&&myS.getHealth()>0){
            getHolder().applyShield(myS);
        }
        reinstatePassiveCritters();
    }

    public void unequip(){
        removePassiveCritters();
        if(getHolder().hasShield(msshield)){
            getHolder().removeShield(msshield);
        }
        super.unequip();
    }

    public LilCritters(ItemHolder actor){
        super(actor);
    }

    public String getName(){
        return "L'il Critters";
    }

    public int getRarity(){
        return 7;
    }
    
    class MucusShield extends ArmorShield{
        public MucusShield(ShieldID myG, int health){
            super(myG, health);
        }
        public void remove(){
            getHolder().explodeOn(70, 100);
            super.remove();
        }
        public int processDamage(int amt, GridObject source){
            return super.processDamage(amt, source)+amt/3;
        }
        public void tick(){
            super.tick();
            changeHealth(10);
        }
    }
    public BotGuide getBotGuide(){
        return new BotGuide();
    }
    public class BotGuide extends Weapon.BotGuide{
        public int getEffectiveRange(){
            return /*false?500:*/300;
        }
        public int getNumTargets(){
            return /*false?2:*/1;
        }
        public int getUltEffectiveRange(){
            return 1000;
        }
        public boolean shouldUseUlt(){
            return regencritter<2;
        }
    }
}




