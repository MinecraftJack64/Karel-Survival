import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class DeafeningScreamWave extends ScreamWave
{
    /** A bullet looses one life each act, and will disappear when life = 0 */
    public DeafeningScreamWave(double rotation, int life, int damage, GridObject source)
    {
        super(rotation, life, damage, source);
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        if(getLife()>5)targ.applyEffect(new SilenceEffect(getLife(), new EffectID("scream attack")){
            public int getCollisionProtocol(){
                return 2;
            }
        });
    }
}
