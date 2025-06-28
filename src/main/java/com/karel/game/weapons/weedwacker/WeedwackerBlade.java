package com.karel.game.weapons.weedwacker;
import java.util.List;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.MetalShield;
import com.karel.game.PoisonEffect;
import com.karel.game.ProjectileReflectShield;
import com.karel.game.ProjectileParryShield;
import com.karel.game.SubAffecter;
import com.karel.game.weapons.ShieldID;
import com.raylib.Raylib;
import com.raylib.Vector2;

/**
 * Write a description of class WeedwackerBlade here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WeedwackerBlade extends GridEntity implements SubAffecter
{
    private GridEntity source;
    private double ammo = 0;//5
    //private double distance, angle;
    private int strength;
    private final ShieldID ultshieldid = new ShieldID(this, "ult"), healthshieldid = new ShieldID(this, "health");
    public WeedwackerBlade(double d, double a, GridEntity source){
        //distance = d;
        //angle = a;
        this.source = source;
        strength = 0;
        startHealthShield(new MetalShield(healthshieldid, 6));
        setDetectable(false);
        setImage("Weapons/weedwacker/proj.png");
        scaleTexture(40);
        addEffectImmunities(PoisonEffect.class);
    }
    public boolean acceptExternalShields(){
        return false;
    }
    public WeedwackerBlade(GridEntity source){
        this(125, -90, source);
    }
    public GridObject getSource(){
        return source;
    }
    public void setStrength(int s){
        strength = s;
    }
    public void animate(){
        //if(canAttack())setRotation(getRotation()+30);
    }
    public void render(){
        super.render();
        //draw line between source and me
        Raylib.drawLineEx(
            new Vector2(renderTransformX((int)getX()), renderTransformY((int)getY())),
            new Vector2(renderTransformX((int)getSource().getX()), renderTransformY((int)getSource().getY())),
            7,
            Raylib.BLACK
        );
    }
    public void behave(){
        matchTeam(source);
        if(source.isDead()){
            die(this);
            return;
        }
        if(!hasShield(ultshieldid)){
            setOpacityPercent(1);
        }
    }
    public void spin(double speed){
        if(canAttack()){
            setRotation(getRotation()+30);
            ammo+=speed;
            if(ammo>=3){
                attack();
                ammo = 0;
            }
        }
    }
    public void die(GridObject source){
        super.die(source);
    }
    public void ult(boolean upgraded){
        applyShield(upgraded?new ProjectileParryShield(ultshieldid, 400):new ProjectileReflectShield(ultshieldid, 400));
        startHealthShield(new MetalShield(healthshieldid, 6));
        setOpacityPercent(0.5);
    }
    public boolean hasUlt(){
        return hasShield(ultshieldid);
    }
    public void hit(int dmg, double d, GridObject s){
        notifyDamage(s.getParentAffecter(), 225);
        super.hit(dmg, d, s);
    }
    public void notifyDamage(GridEntity s, int dmg){
        source.notifyDamage(s, dmg);
    }
    public void immunize(){
        startHealthShield(new MetalShield(healthshieldid, 12));
    }
    public void attack(){
        List<GridEntity> g = getCollidingObjects();
        for(GridEntity e: g){
            if(isAggroTowards(e)){
                damage(e, 50+strength*10);
            }
        }
    }
    public boolean canBePulled(){
        return false;
    }
    public boolean canAttack(){
        return source.canAttack();
    }
}
