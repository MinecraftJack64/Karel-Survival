package com.karel.game.weapons.rock;
import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class RockCatapult here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RockCatapult extends Weapon
{
    private static final int gunReloadTime = 70;
    private double reloadDelayCount;
    private static final int ult = 800;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime) 
        {
            double d = Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 400);
            FlyingRock bullet = new FlyingRock (getHand().getTargetRotation(), d, d/2, getHolder(), useGadget());
            getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
            Sounds.play("airtoss");
            //d400=reload70, d0=reload20
            reloadDelayCount = getAttackUpgrade()==1?(50-(int)(d/8)):0;
        }
    }
    public void fireUlt(){
        Asteroid bullet = new Asteroid(getHand().getTargetRotation(), getHolder(), getUltUpgrade()==1);
        getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
    }
    public void onGadgetActivate(){
        setGadgetCount(5);
        trackGadget();
    }
    public void reload(double s){
        reloadDelayCount+=s;
        updateAmmo(Math.min((int)reloadDelayCount, gunReloadTime));
    }
    public int getUlt(){
        return ult;
    }
    public RockCatapult(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        newAmmo(gunReloadTime, (int)reloadDelayCount);
    }
    public int defaultGadgets(){
        return 1;
    }
    public String getName(){
        return "Rock Launcher";
    }
    public int getRarity(){
        return 1;
    }
}






