package com.karel.game.weapons.catclaw;

import com.karel.game.ItemHolder;
import com.karel.game.LandingHandler;
import com.karel.game.Mousetrap;
import com.karel.game.PowerPercentageEffect;
import com.karel.game.SimpleAmmoManager;
import com.karel.game.Sounds;
import com.karel.game.SpeedPercentageEffect;
import com.karel.game.weapons.EffectID;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class CatClaw here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CatClaw extends Weapon implements LandingHandler
{
    private int nextclawcooldown = 0;
    private static final int ult = 700;
    private boolean toland;
    private int clawtofire = 0;
    private Claw claws[] = new Claw[4];
    public void fire(){
        if(continueUse()){
            if(nextclawcooldown<=0){
                fireClaw(clawtofire);
                clawtofire++;
                if(clawtofire==4){
                    clawtofire = 0;
                    setContinueUse(false);
                    setPlayerLockRotation(false);
                }else{
                    nextclawcooldown = 1;
                }
            }else{
                nextclawcooldown--;
            }
        }else if (getAmmo().hasAmmo()&&canFire()) 
        {
            fireClaw(clawtofire);
            setContinueUse(true);
            setPlayerLockRotation(true);
            clawtofire++;
            Sounds.play("clawunsheath");
            getAmmo().useAmmo();
            nextclawcooldown = 1;
        }
    }
    public void fireClaw(int c){
        claws[c] = getAttackUpgrade()==1?new PullingClaw(getHand().getTargetRotation()+(-21+14*c), getHolder()):new Claw(getHand().getTargetRotation()+(-21+14*c), getHolder());
        getHolder().addObjectHere(claws[c]);
    }
    public void fireUlt(){
        if(!canFire()){
            cancelUltReset();
            return;
        }
        Mousetrap bullet = new Mousetrap(getHolder()){
            public boolean covertDamage(){
                return true;
            }
        };
        getHolder().getWorld().addObject(bullet, getHolder().getX(), getHolder().getY());
        double d = Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 300);
        toland = true;
        getHolder().initiateJump(getHand().getTargetRotation(), d, 75);
        setLocked(true);
    }
    public boolean canFire(){
        if(toland){
            return false;
        }
        for(Claw c: claws){
            if(c!=null&&!c.hasReturned()){
                return false;
            }
        }
        return true;
    }
    public int getUlt(){
        return ult;
    }
    public CatClaw(ItemHolder actor){
        super(actor);
        setAmmo(new SimpleAmmoManager(30, 1));
        chargeUlt(700);
        toland = false;
    }
    public void doLanding(){
        if(toland){
            toland = false;
            getHolder().applyEffect(new PowerPercentageEffect(2, 60, getHolder(), new EffectID(getHolder(), "strengthen")));
            for(int i = 0; i < 8; i++){
                SwipingClaw bullet = new SwipingClaw(getHolder().getRotation()+i*45, 70, getHolder());
                getHolder().getWorld().addObject(bullet, getHolder().getX(), getHolder().getY());
            }
            setLocked(false);
            getHolder().applyEffect(new SpeedPercentageEffect(1.5, 60, getHolder(), new EffectID(getHolder(), "speedup")));
        }
    }
    public String getName(){
        return "Cat Claw";
    }
    public int getRarity(){
        return 4;
    }
}






