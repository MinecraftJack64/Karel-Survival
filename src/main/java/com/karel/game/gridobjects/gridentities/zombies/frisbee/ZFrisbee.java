package com.karel.game.gridobjects.gridentities.zombies.frisbee;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.ZBullet;
public class ZFrisbee extends ZBullet
{
    
    private GridEntity grabber;

    public ZFrisbee(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Projectiles/Bullets/jokerball.png");
        setSpeed(15);
        setLife(50);
        setNumTargets(-1);
        setCollisionMode("radius");
        setRange(100);
        setDamage(250);
    }
    public void applyPhysics(){
        if(grabber!=null&&grabber.isInWorld()&&grabber.canBePulled()){
            grabber.pullTowards(this, getSpeed());
        }
        super.applyPhysics();
    }
    public void doHit(GridEntity g){
        if(g.canBePulled())g.knockBack(face(g, false)+90, distanceTo(g)*1.5, 25, this);
        super.doHit(g);
    }
    public void grab(GridEntity t){
        grabber = t;
    }
    public void letGo(){
        grabber = null;
    }
    public boolean isGrabbed(){
        return grabber!=null;
    }
    
    public void animate(){
        setRotation(getRotation()+80);
        super.animate();
    }
}
