package com.karel.game.effects;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;

/**
 * Deal damage(fire) over time at a preset interval for a preset number of times.
 * ID "burn"
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BurnEffect extends TickingEffect
{
    private int damage;
    public BurnEffect(int damage, int interval, int times, GridObject source){
        this(damage, interval, times, source, new EffectID(source));
    }
    public BurnEffect(int damage, int interval, int times, GridObject source, EffectID id){
        super(interval, times, source, id);
        this.damage = (int)(damage*getSource().getPower());
    }
    public String getStaticTextureURL(){
        return "Symbols/Effects/burn.png";
    }
    public boolean isMalicious(){
        return true;
    }
    public int getCollisionProtocol(){
        return 3;
    }
    public void tick(){
        damage(getTarget());
    }
    public void damage(GridEntity e){
        e.hit(damage, getSource());
    }
    public String getStringID(){return "burn";}
}
