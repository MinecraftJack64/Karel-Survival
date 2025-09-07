package com.karel.game.weapons.flail;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Reelin;
import com.karel.game.effects.EffectID;
import com.karel.game.effects.SpeedPercentageEffect;
import com.raylib.Raylib;
import com.raylib.Vector2;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class FlailCore extends Reelin
{
    private static final int naildistance = 25;//if farther, knock back, if closer, 25% extra damage
    private boolean isUpgrade;
    private boolean applySpeed;
    public FlailCore(double rotation, double targetdistance, double height, GridObject source, boolean upgrade)
    {
        super(rotation, targetdistance, height, source);
        isUpgrade = upgrade;
        setImage("Weapons/flail/proj.png");
        scaleTexture(50);
        setRange(100);
        setDamage(300);
        setSpeed(13);
        setDamageOnReturn(true);
    }
    public int getDamage(GridEntity targ){
        double multiplier = distanceTo(targ)<=naildistance?1.25:1;
        return (int)(super.getDamage(targ)*multiplier);
    }
    public void doHit(GridEntity targ){
        if(distanceTo(targ)>naildistance){
            targ.knockBack(face(targ, false), 5, 15, this);
        }
        if(isUpgrade){
            applySpeed = true;
        }
        super.doHit(targ);
    }
    public void startReturn(){
        super.startReturn();
        setDamage(100);
    }
    public void render(){
        //draw line between source and me
        Raylib.drawLineEx(
            new Vector2(renderTransformX((int)getX()), renderTransformY((int)(getY()-getHeight()))),
            new Vector2(renderTransformX((int)getSource().getX()), renderTransformY((int)(getSource().getY()-getSource().getHeight()))),
            11,
            Raylib.DARKGRAY
        );
        super.render();
    }
    public void die(){
        if(applySpeed&&getSource() instanceof GridEntity s){
            s.applyEffect(new SpeedPercentageEffect(2, 15, this, new EffectID(this)));
        }
        super.die();
    }
    public double getGravity(){
        return 3;
    }
}
