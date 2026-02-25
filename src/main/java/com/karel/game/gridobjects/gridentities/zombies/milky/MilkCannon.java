package com.karel.game.gridobjects.gridentities.zombies.milky;

import com.karel.game.GridObject;
import com.karel.game.Sounds;
import com.karel.game.SubAffecter;

public class MilkCannon extends GridObject implements SubAffecter {
    GridObject target = null, source;
    private int shotCooldown = 0;//5
    private int shotsRemaining = 0;//4
    private boolean defaulting;
    public MilkCannon(GridObject source){
        setImage("rocket.png");
        inherit(source);
        this.source = source;
    }
    public void update(){
        if(shotsRemaining>0){
            continueFire();
        }else{
            if(defaulting){
                setRotation(source.getRotation());
            }else if(target!=null) face(target, true);
        }
        super.update();
    }
    public void setTarget(GridObject targ){
        target = targ;
    }
    public void setDefaulting(boolean d){
        defaulting = d;
    }
    public void fire(){
        matchPower(source);
        shotsRemaining = 7;
        continueFire();
    }
    public void continueFire(){
        if(shotsRemaining>0&&shotCooldown<=0){
            ZMilkDrop bullet = new ZMilkDrop (getRotation()+Math.sin(shotsRemaining*Math.PI/2)*5, this);
            getWorld().addObject (bullet, getX(), getY());
            Sounds.play("gunshoot");
            shotsRemaining--;
            shotCooldown = 5;
        }else{
            shotCooldown--;
        }
    }
    public GridObject getSource(){
        return source;
    }
}
