package com.karel.game.weapons.droid;

import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.Pet;
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
    private DroidController remote;
    private int ammo = 0;
    private Dasher dash;
    private int mode = 0;
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
        double monangle = face(getTarget(), canMove());
        ammo++;
        if (dash != null) {
            if (!dash.dash()) {
                dash = null;
            }
        } else if (distanceTo(getTarget()) > laserrange) {
            walk(monangle, 1);
        } else {
            if (ammo > reloadtime && canAttack() && isAggroTowards(getTarget())) {
                attack();
                ammo = 0;
            }
            if (distanceTo(getTarget()) > laserrange/2) {
                walk(monangle+180, 0.8);
            }
            if (remote.getAttackUpgrade() == 1) {
                //
            }
        }

    }

    public void animate() {
        //
    }

    private static final int reloadtime = 30;

    public void attack() {
        switch (mode) {
            case 0:
                addObjectHere(new DroidLaser(getRotation(), this));
                break;
            case 1:
                dash = new DasherDoer(getRotation(), 15, 7, 30, 100, this);
                dash.dash();
                break;
        }
    }

    public void dashhome() {
        dash = new Dasher(getRotation(), 15, 7, this);
        dash.dash();
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
