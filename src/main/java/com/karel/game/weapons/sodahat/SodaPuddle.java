package com.karel.game.weapons.sodahat;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Puddle;
import com.karel.game.effects.SpeedPercentageEffect;

public class SodaPuddle extends Puddle {
    public SodaPuddle(GridObject source){
        super(source);
        setImage("Weapons/fastfood/projPuddle.png");
        setOpacity(170);
        scaleTexture(getRange()*2);
        setHitAllies(true);
        setSelfHit(true);
    }
    public SodaPuddle(int range, int interv, GridObject source){
        this(100, range, interv, source);
    }
    public SodaPuddle(int damage, int range, int interv, GridObject source){
        super(range, interv, 1, source);
        setDamage(damage);
        setImage("Weapons/fastfood/projPuddle.png");
        setOpacity(170);
        scaleTexture(getRange()*2);
        setHitAllies(true);
        setSelfHit(true);
    }
    public void tick(){
        super.tick();
        if(getInterval()>1){
            setInterval(1);
            setTimes(90);
        }else{
            super.tick();
        }
    }
    public void checkHit(){
        super.checkHit();
        if(getHitStory().size()>0)setTimes(0);
    }
    public void doHit(GridEntity targ){
        if(isAggroTowards(targ)){
            targ.applyEffect(new SpeedPercentageEffect(0.5, 60, this));
            super.doHit(targ);
        } else if(isAlliedWith(targ)) {
            targ.applyEffect(new SpeedPercentageEffect(1.2, 60, this));
        }
    }
}
