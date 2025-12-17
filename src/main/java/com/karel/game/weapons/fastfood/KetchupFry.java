package com.karel.game.weapons.fastfood;
import com.karel.game.GridObject;
import com.karel.game.Shield;
import com.karel.game.shields.ShieldID;
import com.karel.game.GridEntity;

public class KetchupFry extends Fry
{

    public KetchupFry(double rotation, GridObject source, boolean isSuper)
    {
        super(rotation, source, isSuper);
        setImage("Weapons/fastfood/projUpgrade.png");
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        targ.applyShield(new MarkedShield(new ShieldID(this), 120, 50));
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
            remove();
            return super.processDamage(startCooldown<=0?(amt+markDamage):0, source);
        }
    }
}
