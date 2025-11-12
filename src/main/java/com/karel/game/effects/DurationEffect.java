package com.karel.game.effects;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.raylib.Color;
import com.raylib.Raylib;
import com.raylib.Vector2;

/**
 * An effect that lasts a specified number of frames, removing itself after.
 * ID "duration"
 * @author MinecraftJack64
 */
public class DurationEffect extends Effect
{
    int duration;
    private int maxDuration;
    private Color durationColor = new Color((byte)255, (byte)127, (byte)255, (byte)127);
    public DurationEffect(int duration, GridObject source){
        this(duration, source, new EffectID(source));
    }
    public DurationEffect(int duration, GridObject source, EffectID id){
        super(id);
        this.duration = duration;
        setSource(source);
        setCollisionProtocol(1);
    }
    public DurationEffect(int duration, EffectID id){
        super(id);
        this.duration = duration;
        setCollisionProtocol(1);
    }
    public String getStaticTextureURL(){
        return "Symbols/Effects/duration.png";
    }
    public int getDuration(){
        return duration;
    }
    public void setDuration(int t){
        duration = t;
    }
    public void affect(){
        duration--;
        if(duration==0){
            clear();
        }
    }
    public void appliedTo(GridEntity source){
        super.appliedTo(source);
        maxDuration = getDuration();
        onApply();
    }
    public void clear(){
        onClear();
        super.clear();
    }
    public int getMaxDuration(){
        return maxDuration;
    }
    public void onApply(){}
    public void onClear(){}
    public void stack(Effect other){
        duration+=((DurationEffect)other).getDuration();
    }
    public void render(double x){
        int size = 14;
        if(getTarget()!=null)
            Raylib.drawRing(new Vector2(getTarget().renderTransformX((int)(getTarget().getX()+x-size/2)), getTarget().renderTransformY((int)(getTarget().getY()-getTarget().getHeight()+40-size/2))), 0, getTarget().renderTransformY((int)size/2), (float)-90, (int)(360.0*getDuration()/getMaxDuration()-90), 1, durationColor);
        super.render(x);
    }
    public String getStringID(){return "duration";}
}
