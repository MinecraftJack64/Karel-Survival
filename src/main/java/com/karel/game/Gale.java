package com.karel.game;

import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Gale here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Gale extends Weapon
{
    private static final int ult = 2000;
    public void fire(){
        Draft bullet = new Draft(getHand().getTargetRotation(), getHolder());
        getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
        Draft bullet2 = new Draft(getHand().getTargetRotation()+5, getHolder());
        getHolder().getWorld().addObject (bullet2, getHolder().getX(), getHolder().getY());
        Draft bullet3 = new Draft(getHand().getTargetRotation()-5, getHolder());
        getHolder().getWorld().addObject (bullet3, getHolder().getX(), getHolder().getY());
        bullet2.setHitStory(bullet.getHitStory());
        bullet3.setHitStory(bullet.getHitStory());//projectiles share same reference to not damage twice
        //bullet.move ();
        Sounds.play("gunshoot");
    }
    public void fireUlt(){
        Tornado bullet = new Tornado(getHand().getTargetRotation(), getHolder());
        getHolder().getWorld().addObject(bullet, getHolder().getX(), getHolder().getY());
        Sounds.play("protonwave");
    }
    public int getUlt(){
        return ult;
    }
    public Gale(ItemHolder actor){
        super(actor);
    }
    public String getName(){
        return "Gale";
    }
    public int getRarity(){
        return 2;
    }
}




