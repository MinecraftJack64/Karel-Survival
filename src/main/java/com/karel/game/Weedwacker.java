package com.karel.game;
import java.util.List;

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
    private int resurrect = 120;
    public void fire(){
        if(drone!=null){
            //drone.spin();
        }
        //show the lightning
        //Sounds.play("electicity");
    }
    public void fireUlt(){
        if(drone==null){
            drone = new WeedwackerBlade(getHolder());
            getHolder().addObjectHere(drone);
            getHolder().mount(drone, -90, 125);
            resurrect = 120;
        }
        if(!drone.hasUlt()){
            drone.ult();
        }else{
            cancelUltReset();
        }
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        if(drone!=null){
            if(drone.isDead())drone = null;
            else{
                if(getAttackUpgrade()==1)drone.setStrength(5-drone.getHealthShield().getHealth());
                drone.spin();
            }
        }else{
            if(resurrect<=0){
                drone = new WeedwackerBlade(getHolder());
                getHolder().addObjectHere(drone);
                getHolder().mount(drone, -90, 125);
                resurrect = 120;
            }
            resurrect--;
        }
    }
    public void equip(){
        super.equip();
        getHolder().addObjectHere(drone);
        getHolder().mount(drone, -90, 125);
        System.out.println(drone.getWorld());
    }
    public void unequip(){
        getHolder().getWorld().removeObject(drone);
        System.out.println(drone.getWorld());
        super.unequip();
    }
    public Weedwacker(ItemHolder actor){
        super(actor);
        drone = new WeedwackerBlade(getHolder());
    }
    public String getName(){
        return "Weedwacker";
    }
    public int getRarity(){
        return 4;
    }
}



