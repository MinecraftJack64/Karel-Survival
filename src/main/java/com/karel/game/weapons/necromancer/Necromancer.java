package com.karel.game.weapons.necromancer;

import java.util.LinkedList;

import com.karel.game.GridEntity;
import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.effects.TeamSwitchEffect;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Necromancer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Necromancer extends Weapon
{
    private static final int ult = 1000;
    private int hypnoMaxDuration = 0;
    private Lifesteal lasso;
    GridEntity hypno;
    TeamSwitchEffect hypnoEffect;
    private LinkedList<GridEntity> resurrectQueue = new LinkedList<>();
    public void fire(){//one full ammo deals 350 damage
        if (lasso==null) 
        {
            Lifesteal bullet = new Lifesteal(getHand().getTargetRotation(), hypno, getAttackUpgrade()==1, getHolder(), this);
            getHolder().getWorld().addObject(bullet, getHolder().getX(), getHolder().getY());
            lasso = bullet;
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
        if(lasso!=null&&lasso.hasReturned()){
            lasso = null;
        }
        if(hypno!=null&&(hypno.isDead()||!hypno.getTeam().equals(getHolder().getTeam()))){
            hypno = null;
            hypnoEffect = null;
            disableAmmo();
        }
        if(hypnoEffect!=null){
            updateAmmo(hypnoEffect.getDuration());
        }
    }
    public void notifyHypno(GridEntity targ, TeamSwitchEffect effect){
        hypno = targ;
        hypnoEffect = effect;
        hypnoMaxDuration = hypnoEffect.getDuration();
        newAmmo(hypnoEffect.getDuration(), hypnoEffect.getDuration());
    }
    public void notifyKill(GridEntity g){
        resurrectQueue.add(g);
        if(resurrectQueue.size()>3){
            resurrectQueue.removeFirst();
        }
    }
    public void onGadgetActivate(){
        for(GridEntity g: resurrectQueue){
            if(g.isDead()){
                g.reviveWithHealth();
                getHolder().getWorld().addObject(g, g.getX(), g.getY()); // Respawn them where they died
                g.applyEffect(new TeamSwitchEffect(getHolder().getTeam(), 400, getHolder()));
                Sounds.play("resurrect");
            }
        }
        resurrectQueue.clear();
    }
    public int defaultGadgets(){
        return 1;
    }
    public void equip(){
        super.equip();
        if(hypnoEffect!=null){
            newAmmo(hypnoMaxDuration, hypnoEffect.getDuration());
        }
    }
    public Necromancer(ItemHolder actor){
        super(actor);
    }
    public String getName(){
        return "Necromancer";
    }
    public int getRarity(){
        return 6;
    }
}




