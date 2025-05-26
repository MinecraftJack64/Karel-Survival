package com.karel.game;
import java.util.ArrayList;

/**
 * Write a description of class Crystal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Crystal extends GridEntity implements SubAffecter
{
    GridEntity targ;
    GridObject sourc;
    public Crystal(GridEntity target, GridObject source){
        startHealth(200);
        setTeam(source.getTeam());
        targ = target;
        sourc = source;
        if(target.trap())source.getWorld().removeObject(target);
        else targ = null;
    }
    public void behave(){
        setExposure(Math.pow(0.8, getGEsInRange(150).stream().filter(e->e instanceof Crystal).count()));
        hit(1, this);
        if(targ==null){
            die(this);
        }
    }
    public void die(GridObject killer){
        if(targ!=null){targ.untrap();getWorld().addObject(targ, getRealX(), getRealY());targ.hit(Math.min((int)(getPower()*1500),targ.getHealth()/2), sourc);}
        super.die(killer);
    }
    public GridObject getSource(){
        return sourc;
    }
    public boolean acceptExternalShields(){
        return false;
    }
}
