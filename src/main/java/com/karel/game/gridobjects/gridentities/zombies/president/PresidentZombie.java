package com.karel.game.gridobjects.gridentities.zombies.president;

import com.karel.game.ArmorShield;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Nuke;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.shields.ShieldID;

import java.util.ArrayList;

/**
 * Write a description of class PresidentZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PresidentZombie extends Zombie
{
    private ZombieClass[] classes = new ZombieClass[]{ZombieClass.spawner, ZombieClass.leader};
    public String getStaticTextureURL(){return "presidentzareln.png";}
    private int nbg = 6;//number of bodyguards
    private int extraGuards = 12;
    BodyguardZombie guards[] = new BodyguardZombie[nbg];
    GridEntity gtargets[] = new GridEntity[nbg];
    ArrayList<GridEntity> targets = new ArrayList<GridEntity>();
    /**
     * Initilise this rocket.
     */
    public PresidentZombie()
    {
        setSpeed(2);
        startHealth(300);
        applyShield(new ArmorShield(new ShieldID(this), 300));
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        double monangle = face(getTarget(), canMove());
        if(!canAttack()){
            return;
        }
        for(int i = 0; i < guards.length; i++){
            if(guards[i]==null||guards[i].isDead()&&extraGuards>0){
                guards[i] = new BodyguardZombie(this, i);//create body guards
                getWorld().addObject(guards[i], getX(), getY());
                extraGuards--;
            }
        }
        if(distanceTo(getTarget())>140){
            walk(monangle, 1);
            updateZones();
        }
        else{
            explodeOn(150, "enemy", (g)->{
                if(!targets.contains(g)){
                    targets.add(g);
                }
            }, null);
        }
        for(int i = targets.size()-1; i >=0; i--){
            if(targets.get(i).isDead()){
                targets.remove(i);
            }
        }
        int ng;
        if(getHealth()*1.0/getMaxHealth()<0.6){
            ng = nbg-1;
            for(int i = 0; i < gtargets.length; i++){
                if(!guards[i].isDead()){
                    gtargets[i] = this; // designate healer
                    break;
                }
            }
        }else{
            ng = nbg;
        }
        int d = (int)Math.ceil(ng*1.0/targets.size());
        int nd = d;
        int c = 0;
        if(targets.size()>0){
            for(int i = nbg-ng; i < gtargets.length; i++){
                gtargets[i] = targets.get(c);
                nd--;
                if(nd==0){
                    nd = d;
                    c++;
                }
            }
        }else{
            for(int i = nbg-ng; i < gtargets.length; i++){
                gtargets[i] = null;
            }
        }
    }
    public GridEntity getTarget(int id){
        return gtargets[id];
    }
    
    public void hitIgnoreShield(int amt, double exp, GridObject source){
        super.hitIgnoreShield(amt, exp, source);
        GridEntity sourc = source.getParentAffecter();
        if(sourc==null)return;
        if(isAggroTowards(sourc)&&!targets.contains(sourc)){
            targets.add(sourc);
        }
    }
    public void updateZones(){
        if(guards!=null)
        {
            int id = 0;
            for(BodyguardZombie z: guards){
                if(!z.isDead())z.setPatrolZone(calculateZoneX(id), calculateZoneY(id));
                id++;
            }
        }
    }
    public double calculateZoneX(int id){
        return getBranchX(getRotation()+id*60, 60)+getX();
    }
    public double calculateZoneY(int id){
        return getBranchY(getRotation()+id*60, 60)+getY();
    }
    
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 1000;
    }

    public void die(GridObject source){
        Nuke bomb = new Nuke(this);
        getWorld().addObject(bomb, getTarget().getX(), getTarget().getY());
        super.die(source);
    }
    public String getName(){
        return "President Zombie";
    }
    @Override
    public String getZombieID(){
        return "president";
    }
}
