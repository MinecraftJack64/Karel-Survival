package com.karel.game;
/**
 * represents the drone hovering above a player with the DroneRemote weapon. At the command of the weapon, it shoots 4 consecutive bullets at a targeted location, which also changes at the command of the weapon
 * 
 * @author MinecraftJack64
 * @version 1.0
 */
public class Drone extends GridObject implements SubAffecter
{
    private GridEntity source;
    private double ddx, ddy;
    private double dvx, dvy;// vector for reaiming
    public int hoverheight = 100;
    private int remainingshots = 0;//4 remaining shots per attack
    private int tbs = 0;//5 time between shots
    private int ammo = 0;//40 time between attacks
    private int remainingUlt;//40 attacks at 0, 10, 20, 30, 40
    private ComboTracker combo;
    public Drone(GridEntity source){
        this.source = source;//do not set power
    }
    public GridObject getSource(){
        return source;
    }
    public void update(){
        setLocation(source.getX(), source.getY());
        setRotation(90+source.getAngle(source.getX()+ddx, source.getY()+ddy));
        if(getHeight()<source.getHeight()+hoverheight-2.5){
            this.setHeight(getHeight()+5);
        }else if(getHeight()>source.getHeight()+hoverheight+2.5){
            this.setHeight(getHeight()-5);
        }
        ammo++;
        if(remainingshots>0){
            attack(false);
        }
        if(remainingUlt>0){
            if(remainingUlt%10==0){
                ultAttack();
            }
            ddx+=dvx;
            ddy+=dvy;
            remainingUlt--;
            if(remainingUlt==0){
                ultAttack();
            }
        }
        super.update();
    }
    public void attack(boolean startCombo){
        if(ammo>=40&&remainingUlt==0){
            remainingshots = 4;
            ammo = 0;
            if(startCombo){
                combo = new ComboTracker();
            }
        }
        if(remainingshots>0&&tbs<=0){
            tbs = 5;
            remainingshots--;
            AerialBullet b = new AerialBullet(getRotation(), getHeight(), source.distanceTo(source.getX()+ddx, source.getY()+ddy)/(hoverheight/10), 10, source, combo);
            addObjectHere(b);
        }else if(tbs>0){
            tbs--;
        }
        if(remainingshots==0){
            combo = null;
        }
    }
    public void ultAttack(){
        AerialBullet b = new AerialBullet(getRotation(), getHeight(), source.distanceTo(source.getX()+ddx, source.getY()+ddy)/(hoverheight/10), 10, source){
            public boolean covertDamage(){
                return true;
            }
        };
        addObjectHere(b);
    }
    public void reposition(double dx, double dy){
        remainingshots = 0;// cancel attack
        dvx = (dx-ddx)/40;
        dvy = (dy-ddy)/40;
        remainingUlt = 40;
    }
}
