package com.karel.game.weapons.lymphcannon;

import java.util.ArrayList;

import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Pet;
import com.karel.game.SubAffecter;
import com.karel.game.effects.EffectID;
import com.karel.game.effects.PowerPercentageEffect;
import com.raylib.Texture;

/**
 * Write a description of class CellTurret here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CellTurret extends Pet
{
    public String getStaticTextureURL(){return "lymphcannon/pet.png";}
    private int ammo = 0;
    private String target;
    private boolean isattacking;
    public boolean inposition;
    private static double attackrange = 400;
    private static final int reloadtime = 5;
    private boolean cantransform;
    private Texture auraTexture = Greenfoot.loadTexture("Weapons/lymphcannon/aura.png");
    private ArrayList<CellTurret> friends;
    private double tx, ty;
    private int stormCooldown;
    private static int dangerRange = 200;
    private boolean isUpgrade = false;
    /**
     * Initilise this rocket.
     */
    public CellTurret(double x, double y, String targ, boolean upgrade, GridEntity hive)
    {
        super(hive);
        setSpeed(4);
        startHealth(400);
        inherit(hive);
        target = targ;
        tx = x;
        ty = y;
        isUpgrade = upgrade;
        setImage("lymphcannon/petMini.png");
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        face(getTarget(), canMove());
        if(!inposition){
            setHeight(-5);
            walk(face(tx, ty, canMove()), 1);
            if(distanceTo(tx, ty)<2){
                inposition = true;
                setLocation(tx, ty);
                applySpawnImmunity();
            }
        }else{
            setImage("lymphcannon/petTarg.png");
            if(stormCooldown>60){
                setReloadMultiplier(3);
            }
            else if(stormCooldown==60){
                mute(new EffectID("storm"));
                setReloadMultiplier(1);
            }else{
                if(stormCooldown==1){
                    unmute(new EffectID("storm"));
                }
            }
            if(stormCooldown>0)stormCooldown--;
            ammo+=getReloadMultiplier();
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
                if(isUpgrade&&(inDangerRange(getTarget())||friendInDanger()))ammo+=2;
            }
        }
    }
    public void setFriends(ArrayList<CellTurret> a){
        friends = a;
    }
    public boolean friendInDanger(){
        for(CellTurret g: friends){
            if(g.inDangerRange(getTarget())){
                return true;
            }
        }
        return false;
    }
    public boolean inDangerRange(GridEntity g){
        return distanceTo(g)<=dangerRange&&(target!=null&&g.getEntityID().equals(target));
    }
    public void animate(){
        if(cantransform){
            scaleTexture(40);
        }else{
            scaleTexture(30);
        }
        super.animate();
    }
    public void render(){
        if(isUpgrade)renderTexture(auraTexture, getX(), getY(), dangerRange*2, dangerRange*2, 0, 50);
        super.render();
    }
    public void setNewTarget(String targ){
        target = targ;
    }
    public void knockEnemiesBack(){
        explodeOn(50, "enemy", (g)->{
            damage(g, 10);
            g.knockBack(face(g, false), 50, 20, this);
            g.applyEffect(new PowerPercentageEffect(0.2, 90, this));
        }, null);
    }
    public boolean readyToTransform(String pot){
        cantransform = getSpawner()!=null&&!getSpawner().isDead()&&distanceTo(getSpawner())<35&&pot!=null&&!pot.equals(target);
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
    public void gadget(String target){
        this.target = target;
        heal(this, getMaxHealth());
        applySpawnImmunity();
        stormCooldown = 180;
    }
    public int spawnImmunityLength(){
        return 120;
    }
    public GridEntity getNearestTarget() {
        if(target==null)return super.getNearestTarget();
        GridEntity nearestTarget = null;
        double closestDistance = 0;
        //prioritize targeted types
        for (GridEntity entity : this.getWorld().allEntities) {
            if (isAggroTowards(entity)&&entity.canDetect()&&target.equals(entity.getEntityID())&&distanceTo(entity)<attackrange) {
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
    public void hitIgnoreShield(int amt, double exp, GridObject source){
        boolean shield = (source instanceof GridEntity ge)&&(target!=null&&target.equals(ge.getEntityID()))||(source instanceof SubAffecter)&&(target!=null&&target.equals(source.getParentAffecter().getEntityID()));
        super.hitIgnoreShield(shield?amt/10:amt, exp, source);
    }
    public boolean canDetect(){
        return getHeight()>=0;
    }
    public void notifyDamage(GridEntity source, int amt){
        if(target!=null&&target.equals(source.getEntityID())){
            super.notifyDamage(source, amt);
        }
    }
}
