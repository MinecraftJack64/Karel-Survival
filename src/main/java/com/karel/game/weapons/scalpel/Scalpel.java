package com.karel.game.weapons.scalpel;

import com.karel.game.GridEntity;
import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.trackers.SimpleAmmoManager;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Gun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Scalpel extends Weapon
{
    private static final int gunReloadTime = 5;
    private int attackCharge = 30; // 30
    private GridEntity lastEnemy;
    private static final int ult = 1800;
    public void fire(){
        if (getAmmo().hasAmmo()) 
        {
            boolean charged = attackCharge>=30;
            attackCharge = 0;
            ScalpelBlade bullet = new ScalpelBlade(getHand().getTargetRotation(), this, charged, getAttackUpgrade()==1);
            getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
            //bullet.move ();
            Sounds.play("gunshoot");
            getAmmo().useAmmo();
        }
    }
    public void fireUlt(){
        //TODO
        getHolder().applyEffect(new SpeedPercentageEffect(2.5, 90, getHolder()));
        getHolder().addObjectHere(new Anesthetizer(getHand().getTargetRotation(), getHolder()));
    }
    public int notifyHeal(int amt){
        if(getHolder().getPercentHealth()<1)chargeUlt(amt);
        return amt;
    }
    public int getUlt(){
        return ult;
    }
    public void onGadgetActivate(){
        setGadgetTimer(360);//TODO
        trackGadget();
    }
    public void setLastEnemy(GridEntity t){
        lastEnemy = t;
    }
    public GridEntity getLastEnemy(){
        return lastEnemy;
    }
    public void update(){
        if(attackCharge<30){
            attackCharge++;
            updateSpecial(attackCharge);
        }
        super.update();
    }
    public void equip(){
        super.equip();
        newSpecial(30, attackCharge);
    }
    public Scalpel(ItemHolder actor){
        super(actor);
        setAmmo(new SimpleAmmoManager(gunReloadTime, 1));
    }
    public int defaultGadgets(){
        return 3;
    }
    public String getName(){
        return "Scalpel";
    }
    public int getRarity(){
        return 2;
    }
    public BotGuide getBotGuide(){
        return new BotGuide();
    }
    public class BotGuide extends Weapon.BotGuide{
        public int getEffectiveRange(){
            return 125;
        }
        public int getUltEffectiveRange(){
            return 600;
        }
        //AVERAGE SPEED OF PROJECTILE
        public double getLead(double d){
            return 15;
        }
        //TODO: requires number of nearby enemies, more when farther, just one when close
        public boolean shouldUseUlt(){
            return true;
        }
    }
}




