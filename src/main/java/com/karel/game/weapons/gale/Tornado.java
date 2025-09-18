package com.karel.game.weapons.gale;
import java.util.List;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.EffectID;
import com.karel.game.gridobjects.hitters.Bullet;

import java.util.ArrayList;

/**
 * A spinning tornado shot from the Gale weapon ult. It pulls enemies towards itself and deals no damage, but stuns them for a while.
 * 
 * @author Poul Henriksen
 */
public class Tornado extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    ArrayList<GridEntity> suckedenemies = new ArrayList<GridEntity>();
    EffectID stun;
    private int startSlowing = 25; // How long it takes before the tornado starts slowing down.
    public Tornado(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(18);
        setLife(200);
        setDamage(0);
        setNumTargets(-1);
        setMultiHit(false);
        stun = new EffectID(this);
    }
    public void applyPhysics(){
        if(startSlowing>0)startSlowing--;
        else if(getSpeed()>0){
            setSpeed(getSpeed()-3);
        }
        setRotation(getRotation()+30);
        List<GridEntity> d = getGEsInRange(55);
        for(GridEntity g:d){
            if(isAggroTowards(g)&&!suckedenemies.contains(g)&&g.canBePulled()){
                suckedenemies.add(g);
                if(getLife()>50)setLife(Math.max(50, getLife()-20));
            }
        }
        for(int i = suckedenemies.size()-1; i>=0; i--){
            GridEntity g = suckedenemies.get(i);
            if(g.isDead()||!g.canBePulled()){
                g.unstun(stun);
                suckedenemies.remove(i);
            }else{
                g.stun(stun);
                g.pullTowards(this, 20);
            }
        }
        super.applyPhysics();
    }
    public void die(){
        for(GridEntity g:suckedenemies){
            g.unstun(stun);
        }
        super.die();
    }
}
