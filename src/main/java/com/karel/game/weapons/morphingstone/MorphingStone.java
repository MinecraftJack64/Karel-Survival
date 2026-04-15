package com.karel.game.weapons.morphingstone;

import com.karel.game.GridEntity;
import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.effects.TeamSwitchEffect;
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
    private int hypnoMaxDuration = 0;
    GridEntity hypno;
    TeamSwitchEffect hypnoEffect;
    public void fire(){//one full ammo deals 350 damage
        if (getAmmo().hasAmmo()) 
        {
            EssenceStone bullet = new EssenceStone(getHand().getTargetRotation(), getHolder(), this);
            getHolder().getWorld().addObject(bullet, getHolder().getX(), getHolder().getY());
            getAmmo().useAmmo();
            Sounds.play("lifestealshoot");
        }
    }
    public void fireUlt(){
        Hypnotizer bullet = new Hypnotizer(getHand().getTargetRotation(), getHolder(), this);
        getHolder().getWorld().addObject(bullet, getHolder().getX(), getHolder().getY());
        Sounds.play("swirl");
    }
    public int getUlt(){
        return ult;
    }
    public void update(){
        if(hypno!=null&&(hypno.isDead()||!hypno.getTeam().equals(getHolder().getTeam()))){
            hypno = null;
            hypnoEffect = null;
            disableSpecial();
        }
        if(hypnoEffect!=null){
            updateSpecial(hypnoEffect.getDuration());
        }
    }
    public void notifyHypno(GridEntity targ, TeamSwitchEffect effect){
        hypno = targ;
        hypnoEffect = effect;
        hypnoMaxDuration = hypnoEffect.getDuration();
        newSpecial(hypnoEffect.getDuration(), hypnoEffect.getDuration());
    }
    public void notifyHit(){
        //
    }
    public void notifyKill(GridEntity t){
        //
    }
    public int defaultGadgets(){
        return 1;
    }
    public void equip(){
        super.equip();
        if(hypnoEffect!=null){
            newSpecial(hypnoMaxDuration, hypnoEffect.getDuration());
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




