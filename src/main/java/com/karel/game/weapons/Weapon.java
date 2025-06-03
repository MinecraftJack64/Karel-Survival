package com.karel.game.weapons;
import com.karel.game.Game;
import com.karel.game.GridEntity;
import com.karel.game.Item;
import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.Tickable;
import com.karel.game.ui.bars.AmmoBar;
import com.karel.game.IAmmoManager;

/*
 * weapons:
 * Minigun(Gun) - shoots long range pellets quickly
 * ult: unleashes a proton wave that fries zombies
 * Rock(RockCatapult) - launches large rocks over walls and zombies
 * ult: shoots an asteroid that splits when it hits an enemy
 * Gale(Blower) - blows a fast narrow cone of wind that slows zombies in it, pushing them back if they're light or slow enough
 * ult: blasts the ground pushing all nearby zombies away
 * Blade(Sword) - sweeps over a wide area right in front of it and damages zombies
 * ult: slices the sword quickly around you, damaging nearby enemies
 * Slicer(Saw) - throws a spinning circular saw blade like a boomerang, damaging zombies both ways. Returns faster and deals more damage if it hits a wall or shield
 * ult: shoots a saw that chains on multiple enemies
 * Flash(Flashlight) - light that sees in the dark that attacks by flashing in a cone in front of it, making zombies catch fire during the day and stun them temporarily at night
 * ult: turn into a high range high damage laser that turns slowly and lights up everything around it
 * Crossbow(PoisonBow) - shoots 3 poisonous arrows that can be charged up to be more focused and have more range
 * ult: shoots multiples arrows into the air that rain down in an area
 * Shotgun(Shotgun) - shoots in a spray pattern(deals most damage at point blank range)
 * ult: shoots a harpoon that drags in a zombie
 * Mousetrap(Trap) - drops a mouse trap that will disappear after a long time. If a zombie steps on it they will be damaged over time and stunned for a while
 * ult: throw a box of mousetraps that quickly lays them out nearby
 */
/**
 * Write a description of class Weapon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Weapon implements Item, Tickable
{
    private int ultcharge = 0;
    private boolean ultready;
    private ItemHolder hand;
    private IAmmoManager ammo;
    private boolean slotlocked;
    private boolean isusing;
    private boolean isulting;
    private boolean isgadgeting;
    private boolean rotlocked, movelocked;
    public int atkup;
    public int ultup;
    public boolean ultshield = false;//set to true by ult to make next attempt to reset ult charge fail
    public int ultuses = 0;
    public int ultstoretype = 0;//TODO; 0 - single use, 1 - store multiple uses(like hypercharges), 2 - split uses(like melodie)
    public int gadgets = 0;//how many gadgets
    public int gadgetscooldown = 0;//how long until next gadget can be activated
    public int currentgadgetuses = 0;//how many uses current gadget has
    public int currentgadgettimer = 0;//how long until gadget becomes inactive
    public Weapon(){
        ultready = false;
    }
    public Weapon(ItemHolder hand){
        this();
        holdWith(hand);
    }
    public boolean isMainWeapon(){
        return getHand().isMainWeapon();
    }
    public void setUltUpgrade(int id){
        atkup = id;
    }
    public int getUltUpgrade(){
        return atkup;
    }
    public void setAttackUpgrade(int id){
        atkup = id;
    }
    public int getAttackUpgrade(){
        return atkup;
    }
    public void use(){
        if((!continueUlt()||allowAttackWhileContinueUlt())&&(canAttackInAir()||getHolder().isOnGround()))fire();
    }
    public boolean allowAttackWhileContinueUlt(){
        return false;
    }
    public void tick(){
        update();
        reload();
        reloadGadget();
        if(continueGadget()){
            onGadgetContinue();
        }
        if(currentgadgettimer>0)currentgadgettimer--;
        if(gadgetscooldown>0)currentgadgettimer--;
    }
    public abstract void fire();
    public abstract void fireUlt();
    public void reload(){
        reload(getHand().getReloadSpeed());
    }
    public void reload(double amt){
        //reload ammo
        if(getAmmo()!=null){
            getAmmo().reload(amt);
            updateAmmo(getAmmo());
        }
    }
    public void update(){
        //
    }
    public IAmmoManager getAmmo(){
        return ammo;
    }
    public void setAmmo(IAmmoManager ammo){
        this.ammo = ammo;
    }
    public void onGadgetActivate(){};
    public void onGadgetContinue(){};
    public abstract int getUlt();
    public int defaultGadgets(){
        return 0;
    }
    public boolean canAttackInAir(){
        return false;
    }
    public boolean canUltInAir(){
        return false;
    }
    public int getUltMaxUses(){
        return 1;
    }
    public int getUltUses(){
        return ultuses;
    }
    public int getUltCharge(){
        return ultcharge;
    }
    public boolean ultReady(){
        return ultready;
    }
    public boolean ult(){
        if(continueUlt()){
            fireUlt();
            return ultready;
        }
        if(ultready){
            fireUlt();
            if(getGadgets()<defaultGadgets())donateGadgets(1); //TODO remove
            if(!ultshield)ultuses--;
            if(ultuses<=0){
                resetUltCharge();
            }
            ultshield = false;
            return true;
        }else{
            return false;
        }
    }
    public void resetUltCharge(){
        if(!ultshield){
            ultcharge = 0;
            ultready = false;
            ultuses = 0;
            if(isMainWeapon())Game.setUltCharge(ultcharge);
        }else{
            ultshield = false;
        }
    }
    /** Use exclusively inside fireUlt() to stop the ult charge from being used up */
    public void cancelUltReset(){
        ultshield = true;
    }
    public void holdWith(ItemHolder hand){
        this.hand = hand;
    }
    public GridEntity getHolder(){
        return hand.getHolder();
    }
    public ItemHolder getHand(){
        return hand;
    }
    public void chargeUlt(int amt){
        if(ultuses>0){
            return;
        }
        ultcharge+=amt;
        if(ultcharge>=getUlt()){
            ultcharge = getUlt();
            ultready = true;
            ultuses = getUltMaxUses();
        }
        if(isMainWeapon())Game.setUltCharge(ultcharge);
    }
    public void dischargeUlt(int amt){
        ultcharge-=amt;
        if(ultcharge<getUlt()&&ultready){
            ultready = false;
            ultuses = 0;
        }
        if(ultcharge<0){
            ultcharge = 0;
        }
        if(isMainWeapon())Game.setUltCharge(ultcharge);
    }
    public void setUltCharge(int amt){
        if(amt<ultcharge){
            dischargeUlt(ultcharge-amt);
        }else if(amt>ultcharge){
            chargeUlt(amt-ultcharge);
        }
    }
    public void updateAmmo(int amt){
        if(isMainWeapon())Game.gameUI().setAmmo(amt);
    }
    public void updateAmmo(IAmmoManager ammo){
        updateAmmo(ammo.getAmmoBar());
    }
    public void newAmmo(int max, int value){
        if(isMainWeapon())Game.gameUI().newAmmo(max, value);
    }
    public void newAmmo(int max, int value, int phases){
        if(isMainWeapon())Game.gameUI().newAmmo(max, value, phases);
    }
    public void newAmmo(IAmmoManager ammo){
        if(isMainWeapon())Game.gameUI().newAmmo(ammo);
    }
    public void disableAmmo(){
        if(isMainWeapon())Game.gameUI().disableAmmo();
    }
    public void equip(){
        Sounds.play("equip");
        if(!isMainWeapon())return;
        Game.newUltCharge(getUlt(),ultcharge);
        if(getAmmo()!=null){
            Game.gameUI().newAmmo(getAmmo());
        }
    }
    public boolean isUltReady(){
        return ultready;
    }
    public void unequip(){
        if(!isMainWeapon())return;
        Game.disableUltCharge();
        Game.gameUI().disableAmmo();
    }
    public AmmoBar getAmmoBar(){
        return Game.gameUI().getAmmoBar();
    }
    public void setLocked(boolean t){
        slotlocked = t;
    }
    public void setPlayerLockRotation(boolean t){
        rotlocked = t;
        getHand().setRotationLock(t);
        getHand().setTargetLock(t);
    }
    public void setPlayerLockMovement(boolean t){
        movelocked = t;
        getHand().setMovementLock(t);
    }
    public boolean isLocked(){
        return slotlocked||rotlocked||movelocked||isusing||isulting;
    }
    public boolean isPlayerRotationLocked(){
        return rotlocked;
    }
    public boolean isPlayerMovementLocked(){
        return movelocked;
    }
    public boolean continueUse(){
        return isusing;
    }
    public boolean continueUlt(){
        return isulting;
    }
    public boolean continueGadget(){
        return isgadgeting;
    }
    public void setContinueUse(boolean v){
        isusing = v;
    }
    public void setContinueUlt(boolean v){
        isulting = v;
    }
    public void setContinueGadget(boolean v){
        isgadgeting = v;
    }
    
    public void donateGadgets(int amt){
        gadgets+=amt;
    }
    public int getGadgets(){
        return gadgets;
    }
    public int getGadgetsCooldown(){
        return gadgetscooldown;
    }
    public int defaultGadgetsCooldown(){
        return 60;
    }
    public void setGadgetsCooldown(int i){
        gadgetscooldown = i;
    }
    public void reloadGadget(){
        if(getGadgetsCooldown()>0)setGadgetsCooldown(getGadgetsCooldown()-1);
        if(getGadgetTimer()>0)setGadgetTimer(getGadgetTimer()-1);
    }
    public boolean canActivateGadget(){
        return getGadgets()>0&&getGadgetsCooldown()==0;
    }
    public void setGadgetTimer(int t){
        currentgadgettimer = t;
    }
    public int getGadgetTimer(){
        return currentgadgettimer;
    }
    public int getGadgetCount(){
        return currentgadgetuses;
    }
    public void setGadgetCount(int amt){
        currentgadgetuses = amt;
    }
    public boolean activateGadget(){
        //use gadget and return true if successfully used
        if(!isUsingGadget()&&canActivateGadget()){
            gadgets--;
            onGadgetActivate();
            setGadgetsCooldown(defaultGadgetsCooldown());
            return true;
        }
        return false;
    }
    public boolean isUsingGadget(){
        return getGadgetCount()>0||getGadgetTimer()>0||isgadgeting;
    }
    public boolean useGadget(){
        //return true if last gadget use is still active and use a gadget counter if yes
        boolean i = isUsingGadget();
        if(i&&getGadgetCount()>0){
            setGadgetCount(getGadgetCount()-1);
        }
        return i;
    }
    /*public BotGuide getBotGuide(){
        return new BotGuide();
    }
    public class BotGuide{
        public static int getEffectiveRange(){
            return 100;
        }
        public static int getIdealRange(){
            return getEffectiveRange();
        }
        public static int getUltEffectiveRange(){
            return 0;
        }
        public static int getUltIdealRange(){
            return getUltEffectiveRange();
        }
        //how many degrees to turn from a target to lead shots based on distance
        public static double getLead(double d){
            return 0;
        }
        public static double getUltLead(double d){
            return 0;
        }
        //if weapon should use ult/attack if available
        public boolean shouldUse(){
            return true;
        }
        public boolean shouldUseUlt(){
            return true;
        }
    }*/
}


