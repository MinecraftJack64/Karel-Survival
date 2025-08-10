package com.karel.game.weapons.gale;

import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.weapons.Weapon;

/**
 * The Gale weapon, which fires a series of Draft projectiles that pierce enemies and slow them down. Its ult is a tornado that pulls enemies in
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
        if(getUltUpgrade()==1){
            for(int i = 0; i < 4; i++){
                TornadoDebris debris = new TornadoDebris(bullet.getDirection()+i*90, bullet);
                getHolder().addObjectHere(debris);
            }
        }
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




