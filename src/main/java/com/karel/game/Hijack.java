package com.karel.game;

/**
 * The representation of the Highjacker weapon while jumping on an enemy
 * 
 * @author MinecraftJack64
 */
public class Hijack extends Rocket
{
    private Highjacker toNotify;
    public Hijack(double rotation, double targetdistance, double height, Highjacker toNotify, GridEntity source)
    {
        super(rotation, targetdistance, height, source, source);
        this.toNotify = toNotify;
        setRange(200);
        setNumTargets(1);
        setHitAllies(false);
        setSelfHit(false);
    }
    public void doHit(GridEntity g){
        toNotify.notifyHit(g);
    }
}
