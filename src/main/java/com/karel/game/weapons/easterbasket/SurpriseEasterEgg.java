package com.karel.game.weapons.easterbasket;

import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Pet;

public class SurpriseEasterEgg extends Pet
{
    GridEntity targ;
    public SurpriseEasterEgg(GridEntity source){
        super(source);
        setImage("proj"+Greenfoot.getRandomNumber(4)+".png");
        scaleTexture(55);
        startHealth(1000);
        setTeam(source.getTeam());
        targ = new Chick(1.5, source);
    }
    public void behave(){
        hit(2, this);
        if(targ==null){
            die(this);
        }
    }
    public void die(GridObject killer){
        if(targ!=null){getWorld().addObject(targ, getX(), getY());}
        explodeOn(100, 200);
        super.die(killer);
    }
    public String spriteOrigin(){
        return "Weapons/easterbasket/";
    }
    public boolean acceptExternalShields(){
        return false;
    }
    public String getPetID(){
        return "easter-egg";
    }
}
