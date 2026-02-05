package com.karel.game.weapons.trolltest;

import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.effects.EffectID;
import com.karel.game.physics.Dasher;
import com.karel.game.trackers.SimpleAmmoManager;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Gun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TrollTest extends Weapon
{
    private static final int gunReloadTime = 40;
    private int currRiddle;
    private Dasher dash;
    private static final int ult = 2000;
    public void fire(){
        if (getAmmo().hasAmmo()) 
        {
            TrollRiddle bullet = new TrollRiddle (getHand().getTargetRotation(), currRiddle, getHolder());
            getHolder().getWorld().addObject (bullet, getHolder().getX(), getHolder().getY());
            //bullet.move ();
            Sounds.play("gunshoot");
            getAmmo().useAmmo();
            currRiddle++;
            if(currRiddle>2)currRiddle = 0;
        }
    }
    public void fireUlt(){
        TrollBat bullet = new TrollBat(getHand().getTargetRotation(), getUltUpgrade()==1, getHolder());
        getHolder().getWorld().addObject(bullet, getHolder().getX(), getHolder().getY());
        Sounds.play("protonwave");
    }
    public void update(){
        if(dash!=null&&!dash.dash())dash = null;
    }
    public int getUlt(){
        return ult;
    }
    public void onGadgetActivate(){
        getHolder().explodeOnEnemies(200, (g)->{
            if(g.hasEffect(new EffectID("trolltest-riddle")))getHolder().addObjectHere(new TrollRiddle(getHolder().face(g, false), 0, getHolder()));
        });
        setGadgetTimer(20);
        dash = new Dasher(getHand().getTargetRotation(), 15, 15, getHolder());
    }
    public TrollTest(ItemHolder actor){
        super(actor);
        setAmmo(new SimpleAmmoManager(gunReloadTime, 1));
    }
    public int defaultGadgets(){
        return 2;
    }
    public String getName(){
        return "Troll Test";
    }
    public int getRarity(){
        return 0;
    }
    public BotGuide getBotGuide(){
        return new BotGuide();
    }
    public class BotGuide extends Weapon.BotGuide{
        public int getEffectiveRange(){
            return 600;
        }
        public int getUltEffectiveRange(){
            return 125;
        }
        public int getUltIdealRange(){
            return 0;
        }
        //AVERAGE SPEED OF PROJECTILE
        public double getLead(double d){
            return 15;
        }
        //TODO: requires number of nearby enemies, more when farther, just one when close
        public boolean shouldUseUlt(){
            return true;
        }
    }
}




