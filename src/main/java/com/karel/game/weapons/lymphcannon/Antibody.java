package com.karel.game.weapons.lymphcannon;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Shield;
import com.karel.game.gridobjects.hitters.StickyBullet;
import com.karel.game.shields.ShieldID;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Antibody extends StickyBullet
{
    private String target;
    private LymphCannon notifier;
    private int animFrame;
    private boolean upgrade;
    public Antibody(double rotation, String targ, boolean isUpgrade, GridObject source)
    {
        this(rotation, targ, null, isUpgrade, source);
    }
    public Antibody(double rotation, String targ, LymphCannon notify, boolean isUpgrade, GridObject source)
    {
        super(rotation, source);
        upgrade = isUpgrade;
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
        damage(targ, damage);
        if(damage==150&&upgrade){
            targ.applyShield(new MarkedShield(new ShieldID(this, "lymphcannon mark"), 120, 50));
        }
        if(notifier!=null)notifier.notifyHit(targ.getEntityID());
    }
    public void animate(){
        if(hasMounter())animFrame++;
        if((animFrame/5)%2==1){
            if(target==null)setImage("Weapons/lymphcannon/proj1.png");
            else setImage("Weapons/lymphcannon/projTarg1.png");
        }else{
            if(target==null)setImage("Weapons/lymphcannon/proj.png");
            else setImage("Weapons/lymphcannon/projTarg.png");
        }
        super.animate();
    }
    public static class MarkedShield extends Shield{
        private int markDamage = 50; // Damage dealt when the shield is removed
        private int duration = 120; // Duration in ticks
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
        }
        public int processDamage(int amt, GridObject source){
            if(!(source instanceof Antibody)&&!(source instanceof SmallAntibody)||source==getID().getKey())return amt;
            remove();
            return super.processDamage(amt+markDamage, source);
        }
    }
}
