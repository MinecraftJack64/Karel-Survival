package com.karel.game.effects;

import java.util.ArrayList;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;

/**
 * Deal damage(poison) over time at a preset interval for a preset number of times. Hits through shields.
 * ID "poison"
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PoisonEffect extends TickingEffect
{
    ArrayList<String> log = new ArrayList<String>();
    private int damage;
    public PoisonEffect(int damage, int interval, int times, GridObject source){
        this(damage, interval, times, source, new EffectID(source));
    }
    public PoisonEffect(int damage, int interval, int times, GridObject source, EffectID id){
        super(interval, times, source, id);
        this.damage = (int)(damage*getSource().getPower());
    }
    public String getStaticTextureURL(){
        return "Symbols/Effects/poison.png";
    }
    public boolean isMalicious(){
        return true;
    }
    public void setTarget(GridEntity ge){
        super.setTarget(ge);
        log.add("targ "+ge);
    }
    public void tick(){
        //try{
            damage(getTarget());
        /*}catch(Exception e){
            e.printStackTrace();
            for(String s: log){
                System.out.println(s);
            }
        }*/
        log.add("DAMAGE "+getDuration());
    }
    public void clear(){
        super.clear();
        log.add("cleared");
    }
    public void damage(GridEntity e){
        e.hitIgnoreShield(damage, getSource());
    }
    public String getStringID(){return "poison";}
}
