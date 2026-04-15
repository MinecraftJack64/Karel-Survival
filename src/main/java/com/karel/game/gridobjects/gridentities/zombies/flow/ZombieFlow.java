package com.karel.game.gridobjects.gridentities.zombies.flow;

import com.karel.game.ExternalImmunityShield;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Vector;
import com.karel.game.effects.SilenceEffect;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.shields.ShieldID;

/**
 * Write a description of class TorpedoZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ZombieFlow extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.meatshield, ZombieClass.spawner};
    double vx, vy;
    double tvx, tvy;
    double cdirspeed = 0.18; // how fast it can change speed
    int size = 4;
    public String getStaticTextureURL(){return "gunzareln.png";}
    public ZombieFlow(){
        this(4);
    }
    public ZombieFlow(int size)
    {
        scaleTexture(size*15);
        setSpeed(6);
        startHealth(size*100);
        this.size = size;
    }
    public ZombieFlow(double x, double y, int size){
        this(size);
        vx = x;
        vy = y;
        applyEffect(new SilenceEffect(10, this));
        applyShield(new ExternalImmunityShield(new ShieldID(this), 5));
    }
    public void behave()
    {
        if(getHeight()==0)setHeight(1);
        super.behave();
        GridEntity g = getTarget();
        double dir = face(g==null?getWorld().getPlayer():g, true)-90;
        tvx = Math.cos(dir*Math.PI/180)*getSpeed();
        tvy = Math.sin(dir*Math.PI/180)*getSpeed();
        if(vx>tvx+cdirspeed/2){
            vx-=cdirspeed;
        }else if(vx<tvx-cdirspeed/2){
            vx+=cdirspeed;
        }
        if(vy>tvy+cdirspeed/2){
            vy-=cdirspeed;
        }else if(vy<tvy-cdirspeed/2){
            vy+=cdirspeed;
        }
        translate(vx, vy);
    }
    public void attack(){
        super.attack();
        die(this);
    }
    public int defaultDamage(){
        return (int)(12.5*Math.pow(2, size));
    }
    public void die(GridObject source) 
    {
        if(size>1){
            Vector v = new Vector(vx, vy, 0);
            v.setLength(getSpeed()*1.2);
            v.setDirection(v.getDirection()-45);
            addObjectHere(new ZombieFlow(v.getX(), v.getY(), size-1));
            v.setDirection(v.getDirection()+90);
            addObjectHere(new ZombieFlow(v.getX(), v.getY(), size-1));
        }
        super.die(source);
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 100;
    }
    
    public String getName(){
        return "Zombie Flow";
    }
    @Override
    public String getZombieID(){
        return "flow";
    }
}
