package com.karel.game;

import com.karel.game.effects.StunEffect;
import com.karel.game.particles.Explosion;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class WindBurst extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    private boolean isSuper;
    private AirPump toNotify;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public WindBurst(double rotation, double d, boolean isSuper, GridObject source, AirPump notifyJump)
    {
        super(rotation, source);
        setSpeed(17);
        setLife((int)(d/getSpeed()));
        setDamage(isSuper?200:75);
        this.isSuper = isSuper;
        this.toNotify = notifyJump;
    }
    public void doHit(GridEntity targ){
        if(targ!=null){
            super.doHit(targ);
            targ.knockBack(face(targ, false), 55, 30, this);
        }
        int range = isSuper?125:75;
        explodeOn(range, "enemy", (g)->{
            if(targ!=g){
                damage(g, getDamage()/5);
                if(!isSuper){
                    g.applyEffect(new StunEffect(5, this));
                }else{
                    g.knockBack(face(g, false), 80, 50, this);
                }
            }
        }, null);
        if(getSource() instanceof GridEntity&&!((GridEntity)getSource()).isDead()&&distanceTo(getSource())<=range){
            if(toNotify!=null){
                toNotify.startJump(face(getSource(), false), distanceTo(getSource()), isSuper);
            }
        }
        Explosion exp = new Explosion(1);
        getWorld().addObject(exp, getX(), getY());
        Sounds.play("explode");
    }
    public void expire(){
        doHit(null);
        super.expire();
    }
}
