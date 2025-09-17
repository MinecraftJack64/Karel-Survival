package com.karel.game.weapons.weedwacker;

import com.karel.game.ItemHolder;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Weedwacker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Weedwacker extends Weapon
{
    private static final int ult = 1500;
    private WeedwackerBlade drone;
    private double resurrect = 120;
    private int droneDistance = 125; // Distance from the player to the drone when mounted
    private int droneExtension = 0;
    private int gadgetExtensionCooldown = 0;
    private int gadgetAction = 0; // 0 = Extension, 1 = Stay, 2 = Retract
    public void fire(){
        if(drone!=null){
            //drone.spin();
        }
    }
    public void fireUlt(){
        if(drone==null){
            drone = new WeedwackerBlade(getHolder());
            getHolder().addObjectHere(drone);
            getHolder().mount(drone, -90, 125);
            resurrect = 120;
        }
        if(!drone.hasUlt()){
            drone.ult(getUltUpgrade()==1);
        }else{
            cancelUltReset();
        }
    }
    public boolean bladeActive(){
        return drone!=null;
    }
    public WeedwackerBlade getBlade(){
        return drone;
    }
    public int getUlt(){
        return ult;
    }
    public int defaultGadgets(){
        return 1;
    }
    public void onGadgetActivate(){
        gadgetExtensionCooldown = 35;
        gadgetAction = 0; // Extension
        setContinueGadget(true);
    }
    // Cannot activate if blade is not in the world
    public boolean canActivateGadget(){
        return super.canActivateGadget() && drone!=null && drone.isInWorld();
    }
    public void onGadgetContinue(){
        if(drone==null||!drone.isInWorld()){
            setContinueGadget(false);
            return;
        }
        if(gadgetExtensionCooldown>0){
            gadgetExtensionCooldown--;
            if(gadgetAction==0){
                droneExtension += 10; // Extend the drone
                getHolder().mount(drone, -90, droneDistance+droneExtension);
            }else if(gadgetAction==2){
                if(droneExtension>0){
                    droneExtension -= 15; // Retract the drone
                    if(droneExtension < 0) droneExtension = 0; // Prevent negative extension
                    getHolder().mount(drone, -90, droneDistance+droneExtension);
                }else{
                    setContinueGadget(false); // Stop if fully retracted
                }
            }
        }else if(gadgetAction==2){
            setContinueGadget(false);
        }else{
            gadgetAction = 2; // Switch to retracting
            gadgetExtensionCooldown = 24; // Reset cooldown for retraction
        }
    }
    public void notifyHit(){
        if(continueGadget()){
            gadgetAction = 1; // Stay in current state
            gadgetExtensionCooldown = 200; // Reset cooldown for staying
        }
    }
    public void reload(double speed){
        if(drone!=null){
            if(drone.isDead())drone = null;
            else{
                if(getAttackUpgrade()==1)drone.setStrength(5-drone.getHealthShield().getHealth());
                drone.spin(speed);
            }
        }else{
            if(resurrect<=0){
                drone = new WeedwackerBlade(getHolder());
                getHolder().addObjectHere(drone);
                getHolder().mount(drone, -90, 125);
                resurrect = 120;
            }
            resurrect-= speed;
        }
    }
    public void equip(){
        super.equip();
        getHolder().addObjectHere(drone);
        getHolder().mount(drone, -90, droneDistance);
    }
    public void unequip(){
        getHolder().getWorld().removeObject(drone);
        super.unequip();
    }
    public Weedwacker(ItemHolder actor){
        super(actor);
        drone = new WeedwackerBlade(getHolder(), this);
    }
    public String getName(){
        return "Weedwacker";
    }
    public int getRarity(){
        return 5;
    }
}



