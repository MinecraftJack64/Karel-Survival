package com.karel.game.weapons.grimoire;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class MagicBolt extends Bullet
{
    private Grimoire caster;
    
    public MagicBolt(double rotation, GridObject source, Grimoire g)
    {
        super(rotation, source);
        setImage("Projectiles/Bullets/zbullet.png");
        setLife(40);
        setSpeed(13);
        setDamage(125);
        caster = g;
    }
    public void doHit(GridEntity g){
        caster.notifyHit(this, g, 0);
        super.doHit(g);
        if(g.isDead()){
            caster.notifyKill(g);
        }
    }
}
