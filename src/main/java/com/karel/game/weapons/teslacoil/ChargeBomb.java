package com.karel.game.weapons.teslacoil;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.HealCharge;
import com.karel.game.LightningStrike;
import com.karel.game.Sounds;
import com.karel.game.effects.StunEffect;
import com.karel.game.gridobjects.hitters.Bullet;
import com.karel.game.particles.Explosion;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ChargeBomb extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    private boolean lightning, heal;
    private int totaldmg;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    public ChargeBomb(double rotation, boolean ult, GridObject source){
        this(rotation, ult, false, source);
    }
    
    public ChargeBomb(double rotation, boolean ult, boolean gad, GridObject source)
    {
        super(rotation, source);
        setSpeed(17);
        setLife(30);
        setDamage(500);
        lightning = ult;
        heal = gad;
    }
    public void doHit(GridEntity targ){
        explodeOnEnemies(100, (g)->{
            super.doHit(g);
            g.applyEffect(new StunEffect(30, getSource()));
        });
        Explosion exp = new Explosion(1);
        getWorld().addObject(exp, getX(), getY());//INTENDED FOR NOW
        if(lightning){
            addObjectHere(new LightningStrike(this));
        }
        if(heal){
            addObjectHere(new HealCharge(face(getSource(), false), getSource(), (GridEntity)getSource(), totaldmg));
        }
        Sounds.play("explode");
    }
    public void expire(){
        doHit(null);
        super.expire();
    }
    @Override
    public void notifyDamage(GridEntity source, int amt){
        totaldmg+=amt;
    }
    public boolean covertDamage(){
        return true;
    }
}
