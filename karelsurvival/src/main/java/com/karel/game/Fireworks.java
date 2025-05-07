package com.karel.game;
/**
 * Write a description of class Fireworks here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Fireworks extends Weapon implements AmmoHolder
{
    private static final int gunReloadTime = 30;
    private AmmoManager ammo;
    private int reloadDelayCount;
    private static final int ult = 2000;
    private boolean nextammosupercharged = false;
    public void fire(){//one full ammo deals 350 damage
        if (reloadDelayCount >= gunReloadTime&&ammo.hasAmmo()) 
        {
            if(nextammosupercharged){
                //
            }
            double d = Math.min(2*getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 800);
            FireworkRocket bullet = new FireworkRocket (getHand().getTargetRotation(), d, ammo.getAmmo()*100, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getRealX(), getHolder().getRealY());
            //bullet.move ();
            Sounds.play("fireworkshoot");
            reloadDelayCount = 0;
            ammo.useAmmo();
            nextammosupercharged = false;
        }
    }
    public void fireUlt(){
        Nuke bullet = new Nuke(getHolder());
        getHolder().getWorld().addObject(bullet, getHolder().getRealX(), getHolder().getRealY());
        //bullet.move ();
        Sounds.play("lassoshoot");
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        reloadDelayCount++;
        if(reloadDelayCount>=gunReloadTime){
            ammo.reload();
        }
        if(nextammosupercharged){
            updateAmmo(ammo.getAmmoBar()+1);
        }else updateAmmo(ammo.getAmmoBar());
    }
    public Fireworks(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        ammo = new AmmoManager(45, 2, 4);
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(ammo.getMaxAmmoBar(), ammo.getAmmoBar(), ammo.getMaxAmmo());
    }
    public String getName(){
        return "Fireworks";
    }
    public int getRarity(){
        return 3;
    }
}






