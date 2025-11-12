package com.karel.game.weapons.jadecutter;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.EffectID;
import com.karel.game.effects.SizePercentageEffect;
import com.karel.game.gridobjects.hitters.Hitter;

/**
 * Write a description of class StaticJadeBlade here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StaticJadeBlade extends Hitter{
    private double size = 40;//160 damage
    private int maxsize = 80;
    private boolean isSuper;
    private int growthRate = 2;
    public StaticJadeBlade(GridObject source, boolean upgraded)
    {
        super(source);
        setTeam(source.getTeam());
        setNumTargets(1);
        growthRate = upgraded?4:2;
    }
    public StaticJadeBlade(GridObject source)
    {
        this(source, false);
    }
    public void fire(double r, boolean isUpgrade){
        JadeBlade jb = isSuper?new UltJadeBlade(r, size, isUpgrade, this):new JadeBlade(r, size, isUpgrade, this);
        addObjectHere(jb);
        die();
    }
    public void upgrade(boolean grow){
        if(grow)size = 80;
        isSuper = true;
        setOpacityPercent(0.5);
    }
    public void gadget(){
        maxsize = 160;
    }
    public boolean canAttack(){
        return true;
    }
    public void update(){
        setDamage((int)(size*5));
        if(getSource()!=null&&(!(getSource() instanceof GridEntity)||!((GridEntity)getSource()).isDead())&&!getSource().getTeam().equals(getTeam())){//make sure team is same as source
            setTeam(getSource().getTeam());
        }
        checkHit();
        size+=growthRate*(getSource() instanceof GridEntity g?g.getReloadMultiplier():1);
        if(size>maxsize){
            size = maxsize;
        }
        if(getNumTargets()<=0){
            die();
        }
    }
    public void animate(){
        scaleTexture((int)size);
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        if(isSuper&&!targ.hasEffect("sizeMal")){
            targ.applyEffect(new SizePercentageEffect(0.70, -1, this, new EffectID(this)));
        }
    }
    public void die(){
        super.die();
        getWorld().removeObject(this);
    }
}
