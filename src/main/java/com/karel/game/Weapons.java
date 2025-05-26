package com.karel.game;
import java.util.ArrayList;

import com.karel.game.weapons.gun.Gun;
/**
 * Write a description of class Weapons here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Weapons  
{
    public static Class weaponRarities[][] = new Class[][]{
        {Gun.class, TrapSetter.class, DroneRemote.class},
        {RockCatapult.class, Shotgun.class},
        {Crossbow.class, Slicer.class},
        {TeslaCoil.class},
        {CatClaw.class, CrystalGun.class},
        {PetMole.class},
        {Necromancer.class},
        {EasterBasket.class, Fireworks.class, Lovestrike.class},
        {Sudo.class}
    };
    public static int chances[][] = {{0,0,0,0,0},{0,0,0,0,0},{100,0,0,0,0},{100,50,0,0,0},{100,75,0,0,0},{100,85,25,0,0},{100,90,50,0,0},{100,90,75,0,0},{100,90,80,25,0},{100,90,85,50, 0},{100,90,75,75,0}, {100, 90, 90, 80, 25}};
    public static Class attemptWeaponCrafting(int stuff, Item[] exclude){
        ArrayList<ArrayList<Class>> nws = new ArrayList<ArrayList<Class>>();
        for(int i = 0; i < weaponRarities.length; i++){
            nws.add(new ArrayList<Class>());
            for(int j = 0; j < weaponRarities[i].length; j++){
                if(!isExcluded(weaponRarities[i][j], exclude)){
                    nws.get(i).add(weaponRarities[i][j]);
                }
            }
        }
        int cc[] = chances[stuff];
        for(int i = cc.length-1; i >= 0; i--){
            if(cc[i]>0){
                if(Greenfoot.getRandomNumber(100)<cc[i]){
                    if(nws.get(i).size()>0){
                        return nws.get(i).get(Greenfoot.getRandomNumber(nws.get(i).size()));
                    }
                }
            }
        }
        return null;
    }
    public static boolean isExcluded(Class cls, Item[] exclude){
        for(Item e: exclude){
            if(cls.isInstance(e)){
                return true;
            }
        }
        return false;
    }
}
/*
 * 
  common: 0, 0, 100, 100, 100, 100, 100, 100, 100, 100, 100
uncommon: 0, 0, 0, 50,    75, 85, 90,    90, 90, 90, 90
    rare: 0, 0, 0, 0,     0, 25, 50,     75, 80, 85, 90
    epic: 0, 0, 0, 0,     0, 0, 0,       0, 25, 50, 75

 */