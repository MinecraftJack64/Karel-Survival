package com.karel.game.weapons.chameleon;

import java.util.HashSet;

import com.karel.game.GridEntity;
import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.effects.EffectID;
import com.karel.game.effects.PowerPercentageEffect;
import com.karel.game.effects.StunEffect;
import com.karel.game.trackers.SimpleAmmoManager;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Chameleon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Chameleon extends Weapon
{
    private static final int gunReloadTime = 10;
    private HashSet<Integer> colors = new HashSet<Integer>();
    private int colorCooldown = 0;
    private int color = 3;//0,1,2,3,4,5 - red-purple
    private int nextColor = 4;
    private boolean greenboostson = false;
    private static final int ult = 2000;
    private ChameleonTongue tongue;
    private EffectID greenspeed;
    public void fire(){
        if (getAmmo().hasAmmo()&&!checkTongue()) 
        {
            attack();
            getAmmo().useAmmo();
        }
    }
    public void attack(){
        boolean[] hit = {false};
        getHolder().explodeOn(110, "enemy", (g)->{
            if(Math.abs(getHolder().face(g, false)-getHand().getTargetRotation())<30){
                getHolder().damage(g, 100);
                hit[0] = true;
                if(isColor(0)){
                    g.applyEffect(new StunEffect(12, getHolder()));
                }
                if(g.isDead()){
                    createOrb(g.getX(), g.getY());
                }
            }
        }, null);
        if(!hit[0]){
            ChameleonTongue bullet = new ChameleonTongue (getHand().getTargetRotation(), isColor(5), getAttackUpgrade()==1, getHolder());
            getHolder().addObjectHere(bullet);
            tongue = bullet;
            if(isColor(2)){
                double bestResidual[] = new double[]{0, -1};
                getHolder().explodeOn(480, "enemy", (g)->{
                    if(getHolder().getFacingDistance(g)>90){
                        double residual = getHolder().getFacingDistance(g);
                        if(residual>bestResidual[0]){
                            bestResidual[0] = residual;
                            bestResidual[1] = getHolder().getFacingOffset(g)>0?1:-1;
                        }
                    }
                }, null);
                if(bestResidual[0]<90){
                    bestResidual[0] = 180;
                }
                ChameleonTongue bullet2 = new ChameleonTongue (getHand().getTargetRotation()+bestResidual[0]*bestResidual[1], false, false, getHolder());
                getHolder().addObjectHere(bullet2);
            }
        }//bullet.move ();
        Sounds.play("gunshoot");
    }
    public boolean checkTongue(){
        if(tongue!=null&&tongue.hasReturned()){
            if(tongue.target()!=null){
                getHolder().damage(tongue.target(), tongue.target().getHealth());
                if(tongue.target().isDead())createOrb(tongue.target().getX(), tongue.target().getY());
            }
            tongue = null;
            return false;
        }else{
            return tongue!=null;
        }
    }
    public void createOrb(double x, double y){
        ChameleonOrb orb = new ChameleonOrb(this, nextColor);
        getHolder().getWorld().addObject(orb, x, y);
        nextColor++;
        if(nextColor>5){
            nextColor = 0;
        }
    }
    public void notifyColorChange(int c, int heal, GridEntity catcher){
        if(catcher==getHolder()){
            color = c;
            colors.add(c);
        }
        getHolder().heal(catcher, heal);
        if(equipped())tintPlayer();
    }
    public boolean isColor(int chk){
        return colorCooldown>0&&(color==chk||colors.contains(chk));
    }
    public void fireUlt(){
        if(colorCooldown>0){
            cancelUltReset();
        }else{
            newSpecial(300, 300);
            colorCooldown = 300;
            getHolder().applyEffect(new PowerPercentageEffect(1.2, 300, getHolder()));
            colors.clear();
        }
    }
    public boolean ult(){
        if(super.ult()){
            if(isColor(1))chargeUlt(getUlt()/4);
            return true;
        }else{
            return false;
        }
    }
    public void chargeUlt(int amt){
        if(isColor(1))super.chargeUlt(amt*2);
        else super.chargeUlt(amt);
    }
    public int getUlt(){
        return ult;
    }
    public void update(){
        checkTongue();
        if(colorCooldown>0){
            colorCooldown--;
            updateSpecial(colorCooldown);
            if(colorCooldown==0){
                disableSpecial();
            }
        }
        if(!greenboostson&&isColor(3)){
            getHolder().setSpeedMultiplier(1.5, greenspeed);
            getHolder().ground();
            greenboostson = true;
        }else if(greenboostson&&!isColor(3)){
            getHolder().setSpeedMultiplier(1, greenspeed);
            getHolder().unground();
            greenboostson = false;
        }
        if(isColor(4)){
            getHolder().heal(getHolder(), 15);
        }
    }
    public void tintPlayer(){
        switch(color){
            case 0:
                getHolder().setTint(255, 0 ,0);
                break;
            case 1:
                getHolder().setTint(255, 170, 0);
                break;
            case 2:
                getHolder().setTint(255, 255, 0);
                break;
            case 3:
                getHolder().setTint(0, 255, 0);
                break;
            case 4:
                getHolder().setTint(0, 0, 255);
                break;
            case 5:
                getHolder().setTint(128, 0, 255);
                break;
        }
    }
    public Chameleon(ItemHolder actor){
        super(actor);
        setAmmo(new SimpleAmmoManager(gunReloadTime, 1));
        greenspeed = new EffectID(actor, "chameleon_ult_speed");
    }
    public void equip(){
        super.equip();
        tintPlayer();
    }
    public void unequip(){
        super.unequip();
        if(greenboostson){
            getHolder().setSpeedMultiplier(1, greenspeed);
            getHolder().unground();
            greenboostson = false;
        }
        //clear color
        getHolder().setTint(255, 255, 255);
    }
    public String getName(){
        return "Chameleon";
    }
    public int getRarity(){
        return 0;
    }
}




