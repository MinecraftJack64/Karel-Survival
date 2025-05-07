package com.karel.game;
import java.util.ArrayList;
import java.util.List;

/**
 * Write a description of class EntrancePortal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EntrancePortal extends GridObject
{
    List<GridEntity> exiters = new ArrayList<GridEntity>();
    EntrancePortal other;
    GridObject source;
    int health = 14;
    public EntrancePortal(GridObject source){
        this.source = source;
        setTeam(source.getTeam());
    }
    public void link(EntrancePortal other){
        if(this.other==null){
            this.other = other;
            other.link(this);
        }
    }
    public void kAct(){
        explodeOn(health*5, "ally", (g)->{
            if(!exiters.contains(g)){
                if(g.canBePulled()){
                    g.pullTowards(this, 10);
                    if(distanceTo(g)<=10){
                        lowerHealth();
                        other.receive(g);
                    }
                }else{
                    exiters.add(g);
                }
            }
        }, null);
        for(int i = exiters.size()-1; i >= 0; i--){
            if(exiters.get(i).isDead()||distanceTo(exiters.get(i))>health*5){
                exiters.remove(i);
            }
        }
    }
    public void lowerHealth(){
        health--;
        getImage().scale((int)(health*2.5+35), (int)(health*2.5+35));
    }
    public void receive(GridEntity g){
        g.pullTo(getRealX(), getRealY());
        exiters.add(g);
        lowerHealth();
        if(health<=0){
            other.kill();
            this.kill();
        }
    }
    public void kill(){
        //placeholder
        super.die();
        getWorld().removeObject(this);
    }
}


