package com.karel.game.physics;
import java.util.function.Consumer;

import com.karel.game.GridEntity;

import java.util.HashSet;

/**
 * Write a description of class DasherDoer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DasherDoer extends Dasher
{
    private double radius;//how wide to affect others
    public Consumer<GridEntity> onExplode;//what to do each frame during dashing
    public int damage = 0;
    public int numTargets = -1;
    public HashSet<GridEntity> hitstory = new HashSet<GridEntity>();
    public DasherDoer(double direction, double speed, int time, double radius, Consumer<GridEntity> onExplode, GridEntity subject){
        super(direction, speed, time, subject);
        this.radius = radius;
        this.onExplode = onExplode;
    }
    public DasherDoer(double direction, double speed, int time, double radius, int damage, GridEntity subject){
        super(direction, speed, time, subject);
        this.radius = radius;
        this.damage = damage;
    }
    public boolean dash(){
        if(getSubject().isDead()){
            return false;
        }
        if(damage>0){
            getSubject().explodeOnEnemies((int)radius, (g)->{
                if(!hitstory.contains(g)){
                    if(numTargets!=0){
                        getSubject().damage(g, damage);
                        numTargets--;
                        hitstory.add(g);
                    }
                }
            });
        }else if(onExplode!=null){
            getSubject().explodeOn((int)radius, (g)->{
                if(!hitstory.contains(g)&&numTargets!=0){
                    onExplode.accept(g);
                    numTargets--;
                    hitstory.add(g);
                }
            });
        }
        return numTargets!=0&&super.dash();
    }
}
