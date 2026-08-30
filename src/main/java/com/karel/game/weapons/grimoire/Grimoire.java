package com.karel.game.weapons.grimoire;

import com.karel.game.GridEntity;
import com.karel.game.ItemHolder;
import com.karel.game.PercentageShield;
import com.karel.game.Sounds;
import com.karel.game.effects.PoisonEffect;
import com.karel.game.effects.PowerPercentageEffect;
import com.karel.game.effects.ReloadPercentageEffect;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.gridobjects.hitters.Projectile;
import com.karel.game.shields.ShieldID;
import com.karel.game.trackers.SimpleAmmoManager;
import com.karel.game.weapons.Weapon;
import com.raylib.Raylib;

/**
 * Write a description of class Gun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Grimoire extends Weapon
{
    private static final int[] gunReloadTime = {45, 60, 50, 75, 75};
    private int primaryAttack = 0, secondaryAttack = 0;
    private int ultPhase;
    private int ultCooldown = 0;
    private int waterDelay = 0;
    private int xp = 0;
    private int level = 0; // up to 10, each level requires 100*2^level
    private int levelUpCooldown = 0; // 15
    private static final int ult = 2000;
    public void fire(){
        if(continueUse()){
            if(waterDelay>0){
                waterDelay--;
                if(waterDelay%3==0){
                    MagicWaterJet bullet3 = new MagicWaterJet(getHand().getTargetRotation(), getHolder(), this);
                    getHolder().addObjectHere(bullet3);
                }
            }else{
                setContinueUse(false);
                setPlayerLockRotation(false);
            }
        }
        if (getAmmo().hasAmmo()) 
        {
            switch(primaryAttack){
                case 0:
                    MagicBolt bullet = new MagicBolt (getHand().getTargetRotation(), getHolder(), this);
                    getHolder().addObjectHere(bullet);
                    break;
                case 1:
                    //earth TODO
                case 2:
                    //water
                    MagicWaterJet bullet3 = new MagicWaterJet(getHand().getTargetRotation(), getHolder(), this);
                    getHolder().addObjectHere(bullet3);
                    waterDelay = 15;
                    setContinueUse(true);
                    setPlayerLockRotation(true);
                    break;
                case 3:
                    //fire
                    double d = Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 350);
                    MagicFireball bullet4 = new MagicFireball (getHand().getTargetRotation(), d, d/2, getHolder(), this);
                    getHolder().addObjectHere(bullet4);
                    break;
                case 4:
                    //wind
                    MagicTornado bullet5 = new MagicTornado (getHand().getTargetRotation(), getHolder(), this);
                    getHolder().addObjectHere(bullet5);
                    break;
            }
            Sounds.play("gunshoot");
            getAmmo().useAmmo();
        }
    }
    public void fireUlt(){
        if(continueUlt()){
            if(ultCooldown>0){
                ultCooldown--;
            }else{
                setContinueUlt(false);
                switch(primaryAttack){
                    case 1:
                        //earth
                        getHolder().applyShield(new PercentageShield(new ShieldID(this), 0.6, 150));
                        break;
                    case 2:
                        //water
                        getHolder().applyEffect(new ReloadPercentageEffect(1.5, 120, getHolder()));
                        break;
                    case 3:
                        //fire
                        getHolder().applyEffect(new PowerPercentageEffect(1.25, 150, getHolder()));
                        break;
                    case 4:
                        //wind
                        getHolder().applyEffect(new SpeedPercentageEffect(1.25, 125, getHolder()));
                        break;
                }
                setPlayerLockMovement(false);
            }
        }
        if(primaryAttack==0){
            primaryAttack = getSelectedPalette()+1;
            ultCooldown = 30;
            setContinueUlt(true);
            setPlayerLockMovement(true);
            selectPalette(1);
            ultPhase = 1;
        }else if(secondaryAttack==0){
            secondaryAttack = getSelectedPalette();
            ultPhase = 2;
        }else{
            //spawn pets
            //attack
        }
    }
    public int getUlt(){
        return ult;
    }
    public void onGadgetActivate(){
        primaryAttack = 0;
        secondaryAttack = 0;
        selectPalette(0);
        ultPhase = 0;
    }
    public void levelUp(int xp){
        //attack upgrade
        this.xp+=xp;
    }
    // s - source, t - target, i - index of hit
    public void notifyHit(Projectile s, GridEntity t, int i){
        switch(secondaryAttack){
            case 1:
                //electricity TODO
            case 2:
                //shadow
                if(i==0){
                    ShadowTendrils st = new ShadowTendrils(t, getHolder());
                    t.addObjectHere(st);
                }
                break;
            case 3:
                //poison
                t.applyEffect(new PoisonEffect(s.getDamage(t)/6, 30, 4, getHolder()));
                break;
            case 4:
                //light TODO
        }
    }
    public void notifyKill(GridEntity target){
        if(getAttackUpgrade()==1)target.addObjectHere(new XPOrb(this, target.getMaxHealth()));
    }
    public void update(){
        super.update();
        if(levelUpCooldown>0){
            levelUpCooldown--;
        }else{
            if(100*Math.pow(2, level)<=xp){
                level++;
                levelUpCooldown = 15;
            }
        }
        if(ultReady()&&ultPhase<2){
            showPalette();
        }else{
            hidePalette();
        }
    }
    public Grimoire(ItemHolder actor){
        super(actor);
        setAmmo(new SimpleAmmoManager(gunReloadTime[primaryAttack], 1));
        setPaletteOffset(0);
        addToPalette(new PaletteSlice(Raylib.YELLOW, null, "Earth"));
        addToPalette(new PaletteSlice(Raylib.BLUE, null, "Water"));
        addToPalette(new PaletteSlice(Raylib.RED, null, "Fire"));
        addToPalette(new PaletteSlice(Raylib.LIME, null, "Air"));
        addNewPalette();
        addToPalette(new PaletteSlice(Raylib.ORANGE, null, "Electric"));
        addToPalette(new PaletteSlice(Raylib.PURPLE, null, "Shadow"));
        addToPalette(new PaletteSlice(Raylib.GREEN, null, "Poison"));
        addToPalette(new PaletteSlice(Raylib.BEIGE, null, "Light"));
        selectPalette(0);
        ultPhase = 0;
    }
    public int defaultGadgets(){
        return 1;
    }
    public String getName(){
        return "Spellbook";
    }
    public int getRarity(){
        return 5;
    }
}




