package com.karel.game.weapons.drone;

import com.karel.game.ItemHolder;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class DroneRemote here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DroneRemote extends Weapon
{
    private static final int ult = 100;
    private Drone drone;
    private int ultreload = 0;
    public void fire(){
        if(drone!=null){
            drone.attack(getAttackUpgrade()==1);
        }
    }
    public void fireUlt(){
        if(drone!=null){
            drone.reposition(getHand().getTargetX()-getHolder().getX(), getHand().getTargetY()-getHolder().getY(), getUltUpgrade()==1);
        }
    }
    public int getUlt(){
        return ult;
    }
    public void reload(double x){
        if(ultreload>=5){chargeUlt(5);ultreload = 0;}
        else{
            ultreload++;
        }
        if(drone!=null){
            drone.reload(x);
            if(useGadget()){
                drone.setPosition(getHand().getTargetX()-getHolder().getX(), getHand().getTargetY()-getHolder().getY());
            }
        }
    }
    public void onGadgetActivate(){
        setGadgetTimer(600);
    }
    public int defaultGadgets(){
        return 3;
    }
    public void equip(){
        super.equip();
        getHolder().addObjectHere(drone);
    }
    public void unequip(){
        getHolder().getWorld().removeObject(drone);
        super.unequip();
    }
    public DroneRemote(ItemHolder actor){
        super(actor);
        drone = new Drone(getHolder());
        chargeUlt(100);
    }
    public String getName(){
        return "Drone";
    }
    public int getRarity(){
        return 0;
    }
}





