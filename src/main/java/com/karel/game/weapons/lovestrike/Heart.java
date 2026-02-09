package com.karel.game.weapons.lovestrike;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.SubAffecter;
import com.karel.game.effects.PowerPercentageEffect;

/**
 * Write a description of class Heart here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Heart extends GridObject implements SubAffecter
{
    private GridEntity target, source;
    private boolean dead, isUpgrade;
    private int affectCooldown = 30; // 30
    public Heart(GridEntity targ, boolean isUpgrade, GridEntity source){
        target = targ;
        this.source = source;
        this.isUpgrade = isUpgrade;
        dead = false;
        inherit(source);
        if(isUpgrade)setImage("Weapons/lovestrike/markUp.png");
        else setImage("Weapons/lovestrike/mark.png");
    }
    public GridObject getSource(){
        return source;
    }
    public void update(){
        if(affectCooldown>0&&isUpgrade){
            affectCooldown--;
            if(affectCooldown == 0){
                damage(target, 30);
                target.applyEffect(new PowerPercentageEffect(0.7, 30, this));
                affectCooldown = 30;
            }
        }
        if(target.isDead()){
            die();
        }
        setRotation(getRotation()+18);
        setLocation(target.getX(), target.getY(), target.getHeight());
    }
    public void die(){
        for(int i = 0; i < 10; i++){
            Heartbreak bullet = new Heartbreak(getRotation()+i*36, source);
            if(isUpgrade)bullet.setImage("Weapons/lovestrike/projMarkUp.png");
            addObjectHere(bullet);
        }
        if(!source.isDead()){
            heal(source, 100);
        }
        super.die();
        dead = true;
        getWorld().removeObject(this);
    }
    public boolean isDead(){
        return dead;
    }
}
