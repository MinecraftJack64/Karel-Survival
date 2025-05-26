package com.karel.game;
import java.util.List;

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
    private int colorCooldown = 0;
    private int color = 2;//0,1,2,3,4,5 - red-purple
    private int nextColor = 4;
    private int reloadDelayCount;
    private boolean greenboostson = false;
    private static final int ult = 2000;
    private ChameleonTongue tongue;
    private EffectID greenspeed;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime&&!checkTongue()) 
        {
            attack();
            reloadDelayCount = 0;
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
                    createOrb(g.getRealX(), g.getRealY());
                }
            }
        }, null);
        if(!hit[0]){
            ChameleonTongue bullet = new ChameleonTongue (getHand().getTargetRotation(), isColor(5), getAttackUpgrade()==1, getHolder());
            getHolder().addObjectHere(bullet);
            tongue = bullet;
            if(isColor(2)){
                double bestResidual[] = new double[]{180};
                getHolder().explodeOn(480, "enemy", (g)->{
                    if(Math.abs(getHolder().face(g, false)-getHand().getTargetRotation()-180)%360<90){
                        double residual = Math.abs(getHolder().face(g, false)-getHand().getTargetRotation())%360;
                        System.out.println(residual+" "+getHolder().face(g, false)+" "+getHand().getTargetRotation());
                        if(residual<bestResidual[0]){
                            bestResidual[0] = residual;
                        }
                    }
                }, null);
                ChameleonTongue bullet2 = new ChameleonTongue (getHand().getTargetRotation()+bestResidual[0], false, false, getHolder());
                getHolder().addObjectHere(bullet2);
            }
        }//bullet.move ();
        Sounds.play("gunshoot");
    }
    public boolean checkTongue(){
        if(tongue!=null&&tongue.hasReturned()){
            if(tongue.target()!=null){
                getHolder().damage(tongue.target(), tongue.target().getHealth());
                if(tongue.target().isDead())createOrb(tongue.target().getRealX(), tongue.target().getRealY());
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
        //if(catcher==getHolder())color = c;
        getHolder().heal(catcher, heal);
    }
    public boolean isColor(int chk){
        if(chk==2)return true;
        return colorCooldown>0&&color==chk;
    }
    public void fireUlt(){
        colorCooldown = 300;
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
    public void reload(){
        reloadDelayCount++;
        checkTongue();
        if(colorCooldown>0)colorCooldown--;
        updateAmmo(Math.min(reloadDelayCount, gunReloadTime));
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
            getHolder().heal(getHolder(), 10);
        }
    }
    public Chameleon(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        greenspeed = new EffectID(actor, "chameleon_ult_speed");
    }
    public void equip(){
        super.equip();
        newAmmo(gunReloadTime, reloadDelayCount);
    }
    public void unequip(){
        super.unequip();
        if(greenboostson){
            getHolder().setSpeedMultiplier(1, greenspeed);
            getHolder().unground();
            greenboostson = false;
        }
    }
    public String getName(){
        return "Chameleon";
    }
    public int getRarity(){
        return 0;
    }
}




