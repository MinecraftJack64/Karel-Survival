package com.karel.game.weapons.grimoire;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.FlyingProjectile;
import com.karel.game.particles.FlameTrail;

public class MagicFireball extends FlyingProjectile
{
    private Grimoire caster;
    public MagicFireball(double rotation, double targetdistance, double height, GridObject source, Grimoire g)
    {
        super(rotation, targetdistance, height, source);
        setImage("Projectiles/Throws/molotov.png");
        scaleTexture(30);
        caster = g;
    }
    public void animate(){
        super.animate();
        setRotation(getRotation() + 30);
    }
    public void applyPhysics(){
        FlameTrail ft = new FlameTrail(1);
        addObjectHere(ft);
        ft.setHeight(getHeight());
        super.applyPhysics();
    }
    public void die(){
        addObjectHere(new MagicFirePuddle(this));
        super.die();
    }
    public void doHit(GridEntity g){
        caster.notifyHit(this, g, totalHits());
        super.doHit(g);
        if(g.isDead()){
            caster.notifyKill(g);
        }
    }
}
