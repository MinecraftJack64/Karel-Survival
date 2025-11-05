package com.karel.game.weapons.flashdrive;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class FlashBolt extends Bullet
{
    private GridEntity nextIntendedTarget;
    private FlashBolt other;
    public FlashBolt(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(25);
        setLife(12);
        setDamage(40);
        setNumTargets(5);
        setMultiHit(false);
    }
    public void setOther(FlashBolt b){
        other = b;
    }
    public GridEntity nextTarget(){
        return nextIntendedTarget;
    }
    public void animate(){
        setRotation(getRotation()+30);
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        //point to next target
        GridEntity next = null;
        for(GridEntity g: getWorld().allEntities()){
            if(targ!=g&&!getHitStory().contains(g)&&isAggroTowards(g)&&(other==null||!other.getHitStory().contains(g)&&g!=other.nextTarget())&&(next==null||distanceTo(g)<distanceTo(next))){
                next = g;
            }
        }
        if(next==null){
            setNumTargets(0);
        }else{
            double monangle = face(next, false);
            setDirection(monangle);
            setDamage(getDamage()-7);
            if(getLife()<=4){
                setLife(4);
            }
        }
        nextIntendedTarget = next;
    }
}
