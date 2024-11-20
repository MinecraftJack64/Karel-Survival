import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class JadeBlade extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public JadeBlade(double rotation, GridObject source)
    {
        //size from 45-120
        //damage from 25-200 over 60 frames
        //takes 15 frames to start
        super(rotation, source);
        setSpeed(22);
        setLife(200);
        setDamage(10);
        setNumTargets(-1);
        getImage().scale(25, 25);
        setMultiHit(false);
    }
    public void animate(){
        setRealRotation(getRealRotation()+30);
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        //
    }
}
