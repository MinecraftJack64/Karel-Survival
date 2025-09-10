package com.karel.game.weapons.lymphcannon;
import java.util.List;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Pet;
import com.karel.game.effects.PowerPercentageEffect;

/**
 * Write a description of class CellTurret here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CellTurret extends Pet
{
    public String getStaticTextureURL(){return "chickzareln.png";}
    private int ammo = 0;
    private Class target;
    private int damage = 10;
    private boolean isattacking;
    public boolean inposition;
    private static double speed = 5;
    private static double attackrange = 300;
    private static final int reloadtime = 5;
    private boolean cantransform;
    private double tx, ty;
    /**
     * Initilise this rocket.
     */
    public CellTurret(double x, double y, Class targ, GridEntity hive)
    {
        super(hive);
        int scale = 30;
        setSpeed(4);
        startHealth(400);
        inherit(hive);
        target = targ;
        tx = x;
        ty = y;
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        double monangle = face(getTarget(), canMove());
        if(!inposition){
            setHeight(-5);
            walk(face(tx, ty, canMove()), 1);
            if(distanceTo(tx, ty)<2){
                inposition = true;
                setLocation(tx, ty);
            }
        }else{
            ammo++;
            if(isattacking&&distanceTo(getTarget())>attackrange){
                isattacking = false;
                setHeight(-7);
            }
            else if(!isattacking&&distanceTo(getTarget())<=attackrange&&getTarget()!=getSpawner()){
                knockEnemiesBack();
                isattacking = true;
                setHeight(0);
            }
            if(isattacking&&getTarget()!=getSpawner()&&ammo>reloadtime&&canAttack()){
                attack();
                ammo = 0;
            }
        }
    }
    public void animate(){
        if(cantransform){
            scaleTexture(40, 40);
        }else{
            scaleTexture(30, 30);
        }
    }
    public void setNewTarget(Class targ){
        target = targ;
    }
    public void knockEnemiesBack(){
        explodeOn(50, "enemy", (g)->{
            damage(g, 10);
            g.knockBack(face(g, false), 50, 20, this);
            g.applyEffect(new PowerPercentageEffect(0.2, 90, this));
        }, null);
    }
    public boolean readyToTransform(Class pot){
        cantransform = getSpawner()!=null&&!getSpawner().isDead()&&distanceTo(getSpawner())<35&&target!=pot&&pot!=null;
        return cantransform;
    }
    public void attack(){
        SmallAntibody bullet = new SmallAntibody(getRotation(), target, this);
        addObjectHere(bullet);
    }
    public void die(GridObject killer){
        explodeOn(300, "enemy", (g)->{
            SmallAntibody bullet = new SmallAntibody(face(g, false), target, this);
            addObjectHere(bullet);
        }, null);
        super.die(killer);
        try{getWorld().removeObject(this);}catch(Exception e){}
    }
    public GridEntity getNearestTarget() {
        if(target==null)return super.getNearestTarget();
        GridEntity nearestTarget = null;
        double closestDistance = 0;
    
        for (GridEntity entity : this.getWorld().allEntities) {
            if (isAggroTowards(entity)&&entity.canDetect()&&target.isInstance(entity)&&distanceTo(entity)<attackrange) {
                double currentDistance = this.distanceTo(entity);
                
                if (nearestTarget == null || currentDistance < closestDistance) {
                    nearestTarget = entity;
                    closestDistance = currentDistance;
                }
            }
        }
        if(nearestTarget==null){
            nearestTarget = super.getNearestTarget();
        }
        return nearestTarget;
    }
    public void hit(int amt, GridObject source){
        super.hit((target!=null&&target.isInstance(source))?amt/10:amt, source);
    }
    public boolean canDetect(){
        return getHeight()>=0;
    }
    public void notifyDamage(GridEntity source, int amt){
        if(target!=null&&target.isInstance(source)){
            super.notifyDamage(source, amt);
        }
    }
}
