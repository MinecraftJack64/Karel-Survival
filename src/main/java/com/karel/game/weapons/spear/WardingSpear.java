package com.karel.game.weapons.spear;
import java.util.HashMap;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Melee;
/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class WardingSpear extends Melee
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int life = 15;
    private double degtotarg, radius;
    private boolean clockwise;
    private SpearWeapon spear;
    private HashMap<GridEntity, Integer> scores;
    
    public WardingSpear(double rotation, boolean dir, SpearWeapon p, GridObject source)
    {
        super(rotation, source);
        setImage("Weapons/spear/spear.png");
        scaleTexture(60);
        setSpeed(15);
        setLife(life);
        setDamage(100);
        setNumTargets(-1);
        degtotarg = rotation+(dir?180:0);
        clockwise = dir;
        spear = p;
        this.radius = 75;
        scores = new HashMap<GridEntity, Integer>();
    }
    
    public void applyPhysics(){
        if(life <= 0) {
            expire();
        } 
        else {
            life--;
            double centerx = getSource().getX(), centery = getSource().getY();
            setLocation(centerx+radius*Math.cos(degtotarg*Math.PI/180), centery+radius*Math.sin(degtotarg*Math.PI/180));
            setRotation(degtotarg);
            if(clockwise)degtotarg+=12;
            else degtotarg-=12;
            checkHit();
        }
    }
    public void doHit(GridEntity targ){
        scores.put(targ, scores.getOrDefault(targ, 0) + 1);
        super.doHit(targ);
    }
    public void die(){
        if(!clockwise){
            WardingSpear ws = new WardingSpear(degtotarg-180, true, spear, getSource());
            addObjectHere(ws);
        }else{
            spear.returnSpear(scores);
        }
        super.die();
    }
}
