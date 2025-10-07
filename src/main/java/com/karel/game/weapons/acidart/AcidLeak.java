package com.karel.game.weapons.acidart;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Possessor;
import com.karel.game.SubAffecter;

public class AcidLeak extends GridObject implements Possessor, SubAffecter {
    private GridObject source;
    private GridEntity possessor;
    private int ticks = 120;
    public AcidLeak(GridObject source, GridEntity possess){
        this.source = source;
        inherit(source);
        setImage("explosion-big.png");
        scaleTexture(330);
        setOpacity(127);
        if(possess!=null&&possess.possess(this)){
            possessor = possess;
        }
    }
    public void update(){
        super.update();
        if(possessor==null){
            ticks--;
            if(ticks%30==0){
                explodeOnEnemies(165, (g)->{
                    if(possessor!=g){
                        damage(g, 100);
                    }else if(possessor==g){
                        damage(g, 10);
                    }
                });
                if(ticks==0){
                    getWorld().removeObject(this);
                }
            }
        }
    }
    public void onDeath(){
        possessor.unpossess(this);
        possessor = null;
    }
    public void tick(){
        setLocation(possessor.getX(), possessor.getY());
        ticks--;
        if(ticks%30==0){
            explodeOnEnemies(165, (g)->{
                if(possessor!=g){
                    damage(g, 100);
                }else if(possessor==g){
                    damage(g, 10);
                }
            });
            if(ticks==0){
                possessor.unpossess(this);
                possessor = null;
                getWorld().removeObject(this);
            }
        }
    }
    public GridObject getSource(){
        return source;
    }
    public void notifyDamage(GridEntity g, int amt){
        if(getSource()!=null)getSource().notifyDamage(g, amt);
    }
    public double damageSecrecy(){
        return super.damageSecrecy()*0.35;
    }
}
