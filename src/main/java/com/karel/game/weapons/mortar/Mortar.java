package com.karel.game.weapons.mortar;

import java.util.ArrayList;

import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.ItemHolder;
import com.karel.game.Location;
import com.karel.game.Sounds;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.effects.StunEffect;
import com.karel.game.weapons.Weapon;
import com.raylib.Texture;

/**
 * Write a description of class Crossbow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Mortar extends Weapon
{
    private Texture aura = Greenfoot.loadTexture("Weapons/hearth/proj.png");
    private static final int gunReloadTime = 100, ultRadius = 200;
    private int ultDelay = 0;
    private int shotTime = 0;
    private int boostTime = 0;
    private double focus = 0.5;
    private double reloadDelayCount;
    private Location target;
    private ArrayList<ShotLeader> shots = new ArrayList<ShotLeader>();
    private static final int ult = 3000;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime) 
        {
            getHolder().explodeOnEnemies(50, (g)->{
                g.applyEffect(new StunEffect(30, g));
            });
            getHolder().knockBackOnEnemies(50, 50);
            fireShell(getHand().getTargetX(), getHand().getTargetY(), focus==1?800:400, false);
            Sounds.play("crossbowshoot");
            reloadDelayCount = 0;
        }
    }
    public void fireUlt(){
        if(continueUlt()){
            ultDelay--;
            if(ultDelay==30){
                //create shot leaders
                for(GridEntity g: getHolder().getWorld().getGEsInRange(target.x, target.y, ultRadius)){
                    if(getHolder().isAggroTowards(g)){
                        ShotLeader sl = new ShotLeader(g, getHolder());
                        g.addObjectHere(sl);
                        shots.add(sl);
                        if(shots.size()>=7){
                            break;
                        }
                    }
                }
                setPlayerLockMovement(true);
                target = null;
            }
            else if(ultDelay==0){
                for(ShotLeader sl: shots){
                    if(!sl.isInWorld()){
                        return;
                    }
                    Location l = sl.getShotPrediction(shotTime);
                    fireShell(l.x, l.y, 10000, true);
                    sl.getWorld().removeObject(sl);
                }
                onInterrupt();
                if(getUltUpgrade()==1){
                    boostTime = 60;
                    focus = 1;
                    getHolder().applyEffect(new SpeedPercentageEffect(1.3, 60, getHolder()));
                }
            }
        }else{
            target = new Location(getHand().getTargetX(), getHand().getTargetY());
            ultDelay = 90;
            setContinueUlt(true);
        }
    }
    public void onInterrupt(){
        setContinueUlt(false);
        setPlayerLockMovement(false);
        target = null;
        shots.clear();
    }
    //constant - if height is same, they will land at same time.
    public void fireShell(double x, double y, double dist, boolean isUlt){
        boolean gadget = !isUlt&&useGadget();
        MortarShell bullet = new MortarShell(getHolder().getAngle(x, y)+90, Math.min(getHolder().distanceTo(x, y), dist), gadget?50:1700, getHolder(), isUlt, gadget);
        getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
        if(!isUlt&&getAttackUpgrade()==1&&focus==1){
            MortarScoutingShell bullet2 = new MortarScoutingShell(getHolder().getAngle(x, y)+90, Math.min(getHolder().distanceTo(x, y), dist), 1400, getHolder());
            getHolder().getWorld().addObject (bullet2, getHolder().getX(), getHolder().getY());
        }
        shotTime = (int)bullet.getPath().getDuration();
    }
    public void onGadgetActivate(){
        setGadgetCount(1);
    }
    public int defaultGadgets(){
        return 5;
    }
    public int getUlt(){
        return ult;
    }
    public void reload(double speed){
        reloadDelayCount+=speed*(focus==1?1.5:1);
        if(!getHand().isMoving()){
            if(focus<1)focus+=0.1;
            else focus=1;
        }else{
            if(boostTime<=0)focus = 0;
        }
        updateAmmo((int)(focus*10));
    }
    public void update(){
        super.update();
        if(boostTime>0)boostTime--;
    }
    public void render(){
        if(target!=null) getHolder().renderTexture(aura, target.x, target.y, ultRadius*2, ultRadius*2, ultDelay*3, 127);
        super.render();
    }
    public Mortar(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        newAmmo(10, 1);
    }
    public String getName(){
        return "Mortar";
    }
    public int getRarity(){
        return 2;
    }
}







