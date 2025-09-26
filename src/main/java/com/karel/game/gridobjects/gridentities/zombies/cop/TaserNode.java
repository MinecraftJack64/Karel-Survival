package com.karel.game.gridobjects.gridentities.zombies.cop;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.EffectID;
import com.karel.game.gridobjects.hitters.StickyBullet;

public class TaserNode extends StickyBullet {
    private TaserNode other;
    private boolean isStunner;
    private boolean onReturn;
    public TaserNode(double rotation, GridObject source){
        super(rotation, 120, source);
        setSpeed(18);
        setDamage(10);
    }
    public void setOther(TaserNode it){
        other = it;
    }
    public void onStick(GridEntity t){
        damage(t, 30);
        if(other!=null&&other.isStuck()){
            t.stun(new EffectID(this));
            other.confirmStun();
            confirmStun();
            isStunner = true;
        }
    }
    public void applyPhysics(){
        if(!onReturn){
            super.applyPhysics();
        }else{
            face(getSource(), true);
            move(getRotation(), 18);
            if(distanceTo(getSource())<9){
                super.die();
            }
        }
    }
    public void confirmStun(){
        setStickCooldown(90);
    }
    public void onUnstick(GridEntity t){
        if(isStunner)t.unstun(new EffectID(this));
    }
    public void die(){
        onReturn = true;
    }
}
