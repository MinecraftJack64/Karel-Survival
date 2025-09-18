package com.karel.game.weapons.lymphcannon;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Shield;
import com.karel.game.effects.InfectionEffect;
import com.karel.game.gridobjects.hitters.StickyBullet;
import com.karel.game.weapons.ShieldID;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Antibody extends StickyBullet
{
    private String target;
    private LymphCannon notifier;
    public Antibody(double rotation, String targ, GridObject source)
    {
        this(rotation, targ, null, source);
    }
    public Antibody(double rotation, String targ, LymphCannon notify, GridObject source)
    {
        super(rotation, source);
        setSpeed(10);
        setLife(50);
        setDamage(0);
        notifier = notify;
        target = targ;
        if(targ==null)setImage("Weapons/lymphcannon/proj.png");
        else setImage("Weapons/lymphcannon/projTarg.png");
        setRotation(getRotation()+90);
        scaleTexture(25);
    }
    public void onUnstick(GridEntity targ){
        int damage = target!=null?(target.equals(targ.getEntityID())?150:75):100;
        if(damage==150){
            targ.applyShield(new MarkedShield(new ShieldID(this, "lymphcannon mark"), 120, 50));
        }
        if(notifier!=null)notifier.notifyHit(targ.getEntityID());
    }
    public static class MarkedShield extends Shield{
        private int markDamage = 50; // Damage dealt when the shield is removed
        private int duration = 120; // Duration in ticks
        private int startCooldown = 5; // Cooldown before the shield can do damage, if hit before cooldown, it will remove but not do damage
        public MarkedShield(ShieldID myG, int duration, int markDamage){
            super(myG);
            this.markDamage = markDamage;
            this.duration = duration;
        }
        public void tick(){
            duration--;
            if(duration <= 0){
                remove();
            }
            if(startCooldown>0)startCooldown--;
        }
        public int processDamage(int amt, GridObject source){
            //if(!(source instanceof Antibody)&&!(source instanceof SmallAntibody)||source==getID().getKey())return amt;
            remove();
            return super.processDamage(startCooldown<=0?(amt+markDamage):0, source);
        }
    }
}
