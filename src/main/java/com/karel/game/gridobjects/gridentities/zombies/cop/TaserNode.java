package com.karel.game.gridobjects.gridentities.zombies.cop;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.effects.EffectID;
import com.karel.game.gridobjects.hitters.StickyBullet;
import com.raylib.Raylib;
import com.raylib.Vector2;

public class TaserNode extends StickyBullet {
    private TaserNode other;
    private boolean isStunner;
    private boolean onReturn;
    private EffectID effect = new EffectID(this);
    public TaserNode(double rotation, GridObject source){
        super(rotation, 120, source);
        setImage("Projectiles/Bullets/taserprong.png");
        setSpeed(18);
        setDamage(10);
    }
    public void setOther(TaserNode it){
        other = it;
    }
    public void onStick(GridEntity t){
        damage(t, 30);
        if(other!=null&&other.isStuck()){
            t.stun(effect);
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
        if(isStunner)t.unstun(effect);
    }
    public void render(){
        super.render();
        //draw line between source and me
        Raylib.drawLineEx(
            new Vector2(renderTransformX((int)getX()), renderTransformY((int)(getY()-getHeight()))),
            new Vector2(renderTransformX((int)getSource().getX()), renderTransformY((int)(getSource().getY()-getSource().getHeight()))),
            3,
            Raylib.LIGHTGRAY
        );
    }
    public void die(){
        onReturn = true;
    }
}
