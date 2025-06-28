package com.karel.game.weapons.necromancer;

import com.karel.game.Bullet;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.TeamSwitchEffect;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Hypnotizer extends Bullet
{
    Necromancer notifier;
    
    public Hypnotizer(double rotation, GridObject source)
    {
        super(rotation, source);
        setLife(70);
        setDamage(0);
        setSpeed(7);
    }
    
    public Hypnotizer(double rotation, GridObject source, Necromancer thing)
    {
        super(rotation, source);
        setLife(70);
        setDamage(50);
        setSpeed(7);
        notifier = thing;
    }
    public void animate()
    {
        setRotation(getRotation()+15);
    }
    public void doHit(GridEntity targ){
        TeamSwitchEffect effect = new TeamSwitchEffect(getTeam(), 400, this);
        if(!targ.applyEffect(effect)){
            super.doHit(targ);//if hypnosis fails, then instead do damage as usual
            return;
        }
        heal(targ, targ.getMaxHealth()-targ.getHealth());//fully heal hypnotized
        if(notifier!=null){
            notifier.notifyHypno(targ, effect);
        }
    }
}
