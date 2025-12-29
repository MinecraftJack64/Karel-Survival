package com.karel.game.weapons.mortar;

import com.karel.game.GridObject;
import com.karel.game.Location;

public class ShotLeader extends GridObject {
    private GridObject target, source;
    private double targetX, targetY;
    private int life = 0;
    public ShotLeader(GridObject target, GridObject source){
        inherit(source);
        setImage("skull.png");
        scaleTexture(60);
        setOpacity(127);
        this.target = target;
        this.source = source;
        targetX = target.getX();
        targetY = target.getY();
    }
    public void update(){
        if(!target.isInWorld()||!source.isInWorld()){
            die();
            getWorld().removeObject(this);
            return;
        }
        setLocation(target.getX(), target.getY());
        super.update();
        life++;
    }
    //tth - time to hit
    public Location getShotPrediction(int tth){
        double spf = target.distanceTo(targetX, targetY)/life;
        int frames = tth*2;
        double d = target.face(targetX, targetY, false)+90;
        double fx = targetX+getBranchX(d, spf*frames), fy = targetY+getBranchY(d, spf*frames);
        return new Location(fx, fy);
    }
}
