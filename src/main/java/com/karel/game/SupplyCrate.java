package com.karel.game;
import java.util.ArrayList;

/**
 * Write a description of class SupplyCrate here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SupplyCrate extends GridEntity
{
    ArrayList<GridObject> loot;
    public SupplyCrate(ArrayList<GridObject> todrop){
        startHealth(350);
        setTeam("lootbox");
        setImage("barrel.png");
        loot = todrop;
    }
    public SupplyCrate(){
        this(new ArrayList<GridObject>());
    }
    public SupplyCrate(GridObject thing){
        this();
        loot.add(thing);
    }
    public void die(GridObject killer){
        for(GridObject g:loot){
            getWorld().addObject(g, getX(), getY());
        }
        super.die(killer);
    }
    public boolean willNotify(GridObject source){
        return false;
    }
}
