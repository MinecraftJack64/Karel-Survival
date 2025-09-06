package com.karel.game.weapons.spear;
import java.util.HashMap;

import com.karel.game.FlyingProjectile;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.StunEffect;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class SlammingSpear extends FlyingProjectile
{
    HashMap<GridEntity, Integer> scores;
    SpearWeapon myspear;
    public SlammingSpear(HashMap<GridEntity, Integer> hs, SpearWeapon sp, GridObject source)
    {
        super(0, 0, 30, source);
        setImage("Weapons/spear/spear-loaded.png");
        scaleTexture(60);
        scores = hs;
        myspear = sp;
        setRange(35);
        //if(load instanceof GridEntity)getWorld().allEntities.add((GridEntity)load);
    }
    public double getGravity(){
        return 1;
    }
    public void doHit(GridEntity g){
        damage(g, 100);
    }
    public void applyPhysics(){
        super.applyPhysics();
        setRotation(getRotation() + 30);
    }
    
    public void checkHit(){
        super.checkHit();
        for(var thing: scores.entrySet()){
            if(!thing.getKey().isDead()){
                thing.getKey().knockBack(face(thing.getKey(), false), 80, 40, this);
                if(thing.getValue()>=2){
                    thing.getKey().applyEffect(new StunEffect(65, this));
                }
            }
        }
        addObjectHere(new SpearSlamWave());
        myspear.returnSpear(null);
    }
}
