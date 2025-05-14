package com.karel.game;
import java.util.List;

/**
 * A rocket that flies an entity
 * 
 * @author MinecraftJack64
 */
public class Rocket extends FlyingRock
{
    private GridEntity load;
    private boolean hasLanded;
    private Target targetsymbol;
    private EffectID loadStun;
    public Rocket(double rotation, double targetdistance, double height, GridEntity toSpawn, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        load = toSpawn;
        setRange(150);
        setHitAllies(true);
        setSelfHarm(true);
        hasLanded = false;
        loadStun = new EffectID(this, "carryload");
        load.stun(loadStun);
    }
    public void applyTarget(double x, double y){
        if(targetsymbol==null){
            targetsymbol = new Target();
            getWorld().addObject(targetsymbol, x, y);
        }
    }
    public double getGravity(){
        return 5;
    }
    public void applyPhysics(){
        if(!load.isDead()){
            load.pullTo(getRealX(), getRealY(), getRealHeight());
        }
        super.applyPhysics();
    }
    public boolean hasLanded(){
        return hasLanded;
    }
    
    public void checkHit(){
        load.pullTo(getRealX(), getRealY(), 0);
        load.unstun(loadStun);
        hasLanded = true;
        if(targetsymbol!=null){
            getWorld().removeObject(targetsymbol);
        }
        super.checkHit();
        Sounds.play("rocketcrash");
    }
    public void doHit(GridEntity g){
        if(g==getSource()){
            damage(g, Math.min(400, g.getMaxHealth()*2/3));
            return;
        }
        damage(g, (int)(400-distanceTo(g)));
    }
}
