package com.karel.game.weapons.necromancer;

import com.karel.game.Boomerang;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Sounds;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Lifesteal extends Boomerang
{
    private int damage = 35;
    private int dmgdone = 0;
    private GridEntity targ;
    GridEntity hypnoed;
    Necromancer necromancer;
    
    public Lifesteal(double rotation, GridEntity hypno, boolean returnfast, GridObject source, Necromancer necro)
    {
        super(rotation, source);
        setSpeed(14);
        necromancer = necro;
        setNumTargets(1);
        hypnoed = hypno;
        if(returnfast){
            setReturnSpeed(30);
        }else{
            setReturnSpeed(20);
        }
    }
    /**
     * The bullet will damage asteroids if it hits them.
     */
    public void applyPhysics()
    {
        if(damage<165){
            damage+=10;
            setDamage(damage);
        }
        super.applyPhysics();
    }
    public void dieForReal(){
        if(getSource()!=null&&getSource() instanceof GridEntity){
            heal(((GridEntity)getSource()), dmgdone);
        }
        if(targ!=null)super.notifyDamage(targ, dmgdone);
        super.dieForReal();
    }
    
    public void notifyDamage(GridEntity target, int amt){
        this.targ = target;
        dmgdone+=amt;
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        if(targ.isDead()){
            if(necromancer!=null){
                necromancer.notifyKill(targ);
            }
        }
    }
    public void startReturn(){
        super.startReturn();
        Sounds.play("lifesteal");
        if(hypnoed!=null){
            setSource(hypnoed);
        }
    }
}
