package com.karel.game.weapons.snakecharmer;

import java.util.ArrayList;

import com.karel.game.GridEntity;
import com.karel.game.ItemHolder;
import com.karel.game.effects.EffectID;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.trackers.AmmoManager;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class GrenadeLauncher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SnakeCharmer extends Weapon
{
    private static final int gunReloadTime = 20;
    private int reloadDelayCount;
    private static final int ult = 2000;
    private boolean zigZag = true;
    private int ultDuration = 0; // 150
    private ArrayList<GridEntity> enemies = new ArrayList<GridEntity>();
    private EffectID stun = new EffectID(this);
    private static final int ultRange = 150;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime&&getAmmo().hasAmmo()) 
        {
            //calculate aim
            double ang = 0;
            double zagrange = CharmNote.zagRange(), range = getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY());
            if(range>zagrange&&range<=zagrange*Math.sqrt(2)){
                ang = (zigZag?-1:1)*Math.toDegrees(Math.acos(zagrange/range));
            }else if(range>zagrange){
                ang = (zigZag?-1:1)*Math.toDegrees(Math.asin(zagrange/range));
            }
            getHolder().addObjectHere(new CharmNote(ang+getHand().getTargetRotation(), getHolder(), zigZag, false));
            getHolder().addObjectHere(new CharmNote(ang+getHand().getTargetRotation()-5, getHolder(), zigZag, true));
            getHolder().addObjectHere(new CharmNote(ang+getHand().getTargetRotation()+5, getHolder(), zigZag, true));
            zigZag = !zigZag;
            reloadDelayCount = 0;
            getAmmo().useAmmo();
        }
    }
    public void fireUlt(){
        if(ultDuration>0){
            cancelUltReset();
            return;
        }
        setLocked(true);
        ultDuration = 150;
        newSpecial(150, 150);
        getHolder().applyEffect(new SpeedPercentageEffect(0.75, ultDuration, getHolder()));
        getHolder().explodeOn(ultRange, 150);
    }
    public void update(){
        super.update();
        if(ultDuration>0){
            ultDuration--;
            updateSpecial(ultDuration);
            if(ultDuration==0){
                setLocked(false);
                disableSpecial();
                for(GridEntity g: enemies){
                    g.unstun(stun);
                    g.clearFakeTeam();
                }
                enemies.clear();
            }else{
                for(int i = enemies.size()-1; i >= 0; i--){
                    if(getHolder().distanceTo(enemies.get(i))>ultRange){
                        enemies.get(i).unstun(stun);
                        enemies.get(i).clearFakeTeam();
                        enemies.remove(i);
                    }
                }
                getHolder().explodeOnEnemies(ultRange, (e)->{
                    if(!enemies.contains(e)){
                        enemies.add(e);
                        e.stun(stun);
                        e.setFakeTeam(getHolder().getTeam());
                    }
                });
            }
        }
    }
    public void reload(double speed){
        reloadDelayCount++;
        if(reloadDelayCount>=gunReloadTime){
            super.reload(speed);
        }
    }
    public void onGadgetActivate(){
        setGadgetCount(4);
        trackGadget();
    }
    public int defaultGadgets(){
        return 2;
    }
    public int getUlt(){
        return ult;
    }
    public SnakeCharmer(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        setAmmo(new AmmoManager(30, 3, 3));
    }
    public String getName(){
        return "Snake Charmer";
    }
    public int getRarity(){
        return 5;
    }
}






