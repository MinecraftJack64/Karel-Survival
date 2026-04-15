package com.karel.game.weapons.morphingstone;

import com.karel.game.Boomerang;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Sounds;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class EssenceStone extends Boomerang
{
    GridEntity hypnoed;
    MorphingStone necromancer;
    
    public EssenceStone(double rotation, GridObject source, MorphingStone necro)
    {
        super(rotation, source);
        setSpeed(14);
        setDamage(200);
        necromancer = necro;
        setNumTargets(1);
        setReturnSpeed(20);
    }
    public void dieForReal(){
        if(necromancer!=null)necromancer.notifyHit();
        super.dieForReal();
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
    }
}
