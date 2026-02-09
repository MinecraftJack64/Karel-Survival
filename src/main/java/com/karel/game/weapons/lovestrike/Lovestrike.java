package com.karel.game.weapons.lovestrike;
import java.util.List;

import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.effects.ReloadPercentageEffect;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.trackers.SimpleAmmoManager;
import com.karel.game.weapons.Weapon;
import com.raylib.Texture;

/**
 * Write a description of class Lovestrike here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lovestrike extends Weapon
{
    private static final int gunReloadTime = 40;
    private static final int ult = 300;
    private int ultchargedelay = 0;
    private Heart heart;
    private Texture auraTexture = Greenfoot.loadTexture("Weapons/lovestrike/aura.png");
    public void fire(){
        if (getAmmo().hasAmmo()) 
        {
            CupidArrow bullet = new CupidArrow (getHand().getTargetRotation(), getAttackUpgrade()==1, getHolder(), this);
            getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
            //bullet.move ();
            Sounds.play("gunshoot");
            getAmmo().useAmmo();
        }
    }
    public void fireUlt(){
        Kiss bullet = new Kiss(getHand().getTargetRotation(), getAttackUpgrade()==1, getUltUpgrade()==1, getHolder(), this);
        getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
        Sounds.play("protonwave");
    }
    public void notifyHit(Heart n){
        if(heart!=null&&!heart.isDead()){
            heart.die();
        }
        heart = n;
    }
    public boolean hasHeartActive(){
        return heart!=null&&!heart.isDead();
    }
    public int getUlt(){
        return ult;
    }
    public void onGadgetActivate(){
        setGadgetTimer(150);
        trackGadget();
    }
    public int defaultGadgets(){
        return 1;
    }
    public void update(){
        if(ultchargedelay<=0){
            List<GridEntity> l = getHolder().getGEsInRange(600);
            int total = 0;
            for(GridEntity g:l){
                if(g!=null&&getHolder().isAggroTowards(g)&&g.willNotify((GridObject)getHolder())){
                    chargeUlt(7);
                    if(isUsingGadget()){
                        total++;
                    }
                }
            }
            if(total>0){
                if(total>3)total = 3;
                getHolder().heal(getHolder(), 30);
                getHolder().applyEffect(new ReloadPercentageEffect(1+0.25*total, 7, getHolder()));
                getHolder().applyEffect(new SpeedPercentageEffect(1+0.15*total, 7, getHolder()));
            }
            ultchargedelay = 5;
        }else{
            ultchargedelay--;
        }
    }
    public void render(){
        getHolder().renderTexture(auraTexture, getHolder().getX(), getHolder().getY(), 1200, 1200, 0, 50);
    }
    public Lovestrike(ItemHolder actor){
        super(actor);
        setAmmo(new SimpleAmmoManager(gunReloadTime, 1));
    }
    public String getName(){
        return "Lovestrike";
    }
    public int getRarity(){
        return 7;
    }
}




