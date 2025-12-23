package com.karel.game.weapons.frostspirit;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

public class MiniWreath extends Bullet{
    private int life = 30;
    private boolean isBig;
    public MiniWreath(double rot, GridObject source, boolean isBig){
        super(rot, source);
        if(isBig){
            setLife(24);
        }else{
            setLife(12);
            life = 20;
        }
        setSpeed(13);
        setNumTargets(-1);
        setDamage(20);
        this.isBig = isBig;
    }
    public void applyPhysics(){
        if(getLife()>0||life<=0){
            super.applyPhysics();
        }else{
            if(life%10==0){
                explodeOn(30, 50);
            }
            life--;
        }
    }
    public void die(){
        if(isBig){
            for(int i = -90; i <= 90; i+=180)addObjectHere(new MiniWreath(getDirection()+i, this, false));
        }else{
            for(int i = 0; i <= 360; i+=60)addObjectHere(new Snowflake(i+getRotation(), this));
        }
        super.die();
    }
}
