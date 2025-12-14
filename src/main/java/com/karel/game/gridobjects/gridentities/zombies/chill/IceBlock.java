package com.karel.game.gridobjects.gridentities.zombies.chill;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.SubAffecter;

/**
 * Write a description of class Crystal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class IceBlock extends GridEntity implements SubAffecter
{
    GridEntity targ;
    GridObject sourc;
    private int life = 200;
    public IceBlock(GridEntity target, GridObject source){
        startHealth(200);
        setTeam(source.getTeam());
        targ = target;
        sourc = source;
        if(target.trap())source.getWorld().removeObject(target);
        else targ = null;
    }
    public void behave(){
        if(targ==null){
            kill(this);
            return;
        }
        heal(targ, 7);
        life--;
        if(life<=0){
            kill(this);
        }
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
    public String getEntityID(){return "zombie-chill-iceblock";}
}
