package com.karel.game.weapons.crystalgun;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.SubAffecter;

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
        hit(1, this);
        if(targ==null){
            die(this);
        }
    }
    public void die(GridObject killer){
        if(targ!=null){
            targ.untrap();
            getWorld().addObject(targ, getX(), getY());
            if(isAggroTowards(targ))targ.hit(Math.min((int)(getPower()*1500),targ.getHealth()/2), sourc);
            else if(isAlliedWith(targ))targ.heal(targ.getHealth()/2, sourc);
        }
        super.die(killer);
    }
    public GridObject getSource(){
        return sourc;
    }
    public boolean acceptExternalShields(){
        return false;
    }
    public String getEntityID(){return "pet-crystalgun-crystal";}
}
