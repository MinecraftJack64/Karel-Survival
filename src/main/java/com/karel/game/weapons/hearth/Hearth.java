package com.karel.game.weapons.hearth;
import java.util.ArrayList;
import java.util.List;

import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.weapons.Weapon;
import com.raylib.Texture;

/**
 * Write a description of class Hearth here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hearth extends Weapon
{
    private double focus = 0;//40 frames to reach 1
    private double waveCooldown = 1; // 100 frames between waves
    private static final int ult = 1700;
    private ArrayList<Campfire> campfires = new ArrayList<Campfire>();
    private Texture auraTexture = Greenfoot.loadTexture("Weapons/hearth/proj.png");
    public void fire(){
        List<GridEntity> l = getHolder().getGEsInRange(150);
        for(GridEntity g:l){
            if(getHolder().isAggroTowards(g))getHolder().damage(g, (int)(focus*(getHand().isMoving()?4:8)));
        }
        if(waveCooldown<=0&&focus==1){
            waveCooldown = 100;
            getHolder().addObjectHere(new HeatWave(getHolder()));
            Sounds.play("firewave");
        }
    }
    public void fireUlt(){
        clearCampfires();
        Sounds.play("crossbowshoot");
        double x = getHand().getTargetX();
        double y = getHand().getTargetY();
        double d = Math.min(400+focus*50, getHolder().distanceTo(x, y));
        Campfire campfire = getAttackUpgrade()==1?new UpgradedCampfire(getHolder()):new Campfire(getHolder());
        CampfireDropper bullet = new CampfireDropper(getHand().getTargetRotation(), d, d/2, campfire, getHolder());
        getHolder().addObjectHere(bullet);
        campfires.add(campfire);
        if(campfires.size()>5){
            for(int i = 0; i < campfires.size()-5; i++)campfires.get(i).startDie();
        }
        focus = 0;
    }
    public void clearCampfires(){
        for(int i = campfires.size()-1; i >= 0; i--){
            if(campfires.get(i).isDead()){
                campfires.remove(i);
            }
        }
    }
    public void onGadgetActivate(){
        clearCampfires();
        for(Campfire c: campfires){
            c.gadget();
        }
        setGadgetTimer(200);
        trackGadget();
    }
    public int defaultGadgets(){
        return 1;
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        if(getAttackUpgrade()==1)waveCooldown--;
        if(getHand().isAttacking()&&focus<1){
            focus+=0.025;
            if(focus>1)focus=1;
        }else if(!getHand().isAttacking()&&focus>0){
            focus-=0.025;
            if(focus<0)focus=0;
        }
        if(!getHand().isMoving()&&!getHand().isAttacking()){
            chargeUlt((int)((1-focus)*10));
        }
        updateAmmo((int)(focus*40));
    }
    public void render(){
        if(focus>0)getHolder().renderTexture(auraTexture, getHolder().getX(), getHolder().getY(), focus*300, focus*300, 0, 127);
    }
    public Hearth(ItemHolder actor){
        super(actor);
        campfires = new ArrayList<Campfire>();
    }
    public void equip(){
        super.equip();
        newAmmo(40, (int)(focus*40));
    }
    public String getName(){
        return "Hearth";
    }
    public int getRarity(){
        return 1;
    }
}






