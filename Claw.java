import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Claw extends Boomerang
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int phase;
    private boolean hasreturned;
    //private int life = 10;
    
    public Claw(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(20);
        setReturnSpeed(20);
        setLife(11);
        setDamage(0);
        setNumTargets(-1);
        phase = 1;
        hasreturned = false;
    }
    public boolean hasReturned(){
        return hasreturned;
    }
    public void expire(){
        getHitStory().clear();
        setLife(1);
        setDamage(50);
        setSpeed(15);
        phase = 2;
    }
    public void applyPhysics(){
        if(phase==2){
            if(getSource()==null||distanceTo(getSource())<20){
                dieForReal();
                return;
            }
            setRealRotation(face(getSource(), false)+90);
            move(getRealRotation()-90, getSpeed());
            checkHit();
        }else{
            super.applyPhysics();
        }
    }
}
