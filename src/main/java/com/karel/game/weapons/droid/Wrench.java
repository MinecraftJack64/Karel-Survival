package com.karel.game.weapons.droid;

import com.karel.game.GridEntity;
import com.karel.game.Location;

public class Wrench extends com.karel.game.gridobjects.hitters.FlyingProjectile{
    private DroidController notify;
    private boolean notified = false;
    public Wrench(double rotation, double targetdistance, double height, com.karel.game.GridObject source, DroidController notify){
        super(rotation, targetdistance, height, source);
        this.notify = notify;
        setDamage(150);
        setRange(35);
    }
    public void doHit(GridEntity t){
        super.doHit(t);
        if(!notified&&notify!=null){
            notify.notifyHit(t);
            notified = true;
        }
    }
    public void die(){
        if(notify!=null){
            if(!notified){
                notify.notifyHit(new Location(getX(), getY()));
            }
        }
        super.die();
    }
}
