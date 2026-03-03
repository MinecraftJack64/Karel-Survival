package com.karel.game.weapons.droid;

import com.karel.game.BombDropper;
import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.Location;
import com.karel.game.Pet;
import com.karel.game.TickingTimeBomb;
import com.karel.game.effects.PullEffect;
import com.karel.game.physics.Dasher;
import com.karel.game.physics.DasherDoer;
import com.raylib.Texture;

/**
 * Write a description of class Critter here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Droid extends Pet {

    public static Texture rocket = Greenfoot.loadTexture("rocket.png");
    public static Texture rocket2 = Greenfoot.loadTexture("rocketWithThrust.png");
    private Texture crosshair = Greenfoot.loadTexture("Controls/target.png");
    private GridEntity target;
    private Location patrol;
    private DroidController remote;
    private int ammo = 0;
    private Dasher dash;
    private int mode = 0;
    private int ult = 0; // 300
    private static final int laserrange = 400;

    /**
     * Initilise this rocket.
     */
    public Droid(DroidController spawner, GridEntity hive) {
        super(hive);
        setImage(rocket);
        scaleTexture(50, 50);
        startHealth(1500);
        setSpeed(4);
        if (hive != null)
            inherit(hive);
        remote = spawner;
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave() {
        double monangle = face(getTarget(), dash==null&&canMove());
        ammo++;
        if (dash != null) {
            if (!dash.dash()) {
                dash = null;
            }
        } else if (distanceTo(getTarget()) > laserrange) {
            walk(monangle, 1);
        } else if(mode==0||mode==3){
            if (ammo > reloadtime && canAttack() && (mode==0&&isAggroTowards(getTarget())||mode==3&&isAlliedWith(getTarget()))) {
                attack();
                ammo = 0;
            }
            if (distanceTo(getTarget()) < laserrange/2) {
                walk(monangle+180, 0.8);
            }
            if (remote.getAttackUpgrade() == 1) {
                if(patrol!=null){
                    //
                }
            }
        } else if(mode==1){
            attack();
            if (distanceTo(getTarget()) > 20) {
                walk(monangle, 1.2);
            }
        } else if(mode==2){
            if (ammo > reloadtime && canAttack() && isAggroTowards(getTarget())) {
                attack();
                ammo = 0;
            }
            if (distanceTo(getTarget()) > 30) {
                walk(monangle, 1.2);
                ult();
            }
        }
    }
    public GridEntity getTarget(){
        if(target!=null){
            if(target.isDead()){
                target = null;
            }else{
                return target;
            }
        }
        return super.getTarget();
    }

    public void animate() {
        //
    }

    public boolean isPotentialTarget(GridEntity g){
        return mode==3?g.getHealth()<g.getMaxHealth()&&isAlliedWith(g):super.isPotentialTarget(g);
    }

    private static final int reloadtime = 30;

    public void attack() {
        switch (mode) {
            case 0:
                addObjectHere(new DroidLaser(getRotation(), this));
                break;
            case 1:
                addObjectHere(new DrillBit(getRotation(), this));
                break;
            case 2:
                for(int i = -45; i < 45; i++){
                    addObjectHere(new DroidShot(getRotation()+i, this));
                }
                break;
            case 3:
                addObjectHere(new DroidHealLaser(getRotation(), this));
        }
    }

    public void fallBack() {
        face(getSpawner(), true);
        int s = 15, l = (int)(distanceTo(getSpawner())/s);
        dash = new Dasher(getRotation(), s, l, this);
        dash.dash();
        getSpawner().applyEffect(new PullEffect(getSpawner().face(this, true), s, l, getSpawner()));
        heal(getSpawner(), 500);
        heal(this, 500);
    }

    public void jailbreak(double direction, double distance){
        addObjectHere(new BombDropper(direction, Math.min(400, distance), 300, new TickingTimeBomb(5, this), this));
    }

    public void dash(double direction){
        setRotation(direction);
        dash = new DasherDoer(getRotation(), 15, 25, 50, 150, this);
        dash.dash();
    }

    public void setMode(int m){
        mode = m;
    }

    public void setTarget(Location l){
        patrol = l;
        target = null;
    }

    public void setTarget(GridEntity g){
        target = g;
        patrol = null;
    }

    public void ult(){
        if(ult>300){
            dash = new Dasher(getRotation(), 15, 8, this);
            dash.dash();
            ult = 0;
        }
    }

    public void render(){
        super.render();
        int scale = 50;
        if(target!=null){
            target.renderTexture(crosshair, target.getX(), target.getY(), scale, scale, 0, 127);
        }
        if(patrol!=null){
            renderTexture(crosshair, patrol.x, patrol.y, scale, scale, 0, 127);
        }
    }

    public void notifyDamage(GridEntity target, int amt){
        ult+=amt;
        super.notifyDamage(target, amt);
    }

    public void gadget() {
        //
    }

    public int getMode() {
        return mode;
    }

    public String getPetID() {
        return "droid-droid";
    }
}
