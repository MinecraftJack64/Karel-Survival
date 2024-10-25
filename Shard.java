import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Shard extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    private int bonus;
    public Shard(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(13);
        setLife(30);
        setDamage(100);
        setNumTargets(-1);
        bonus = 30;
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        if(face(targ, false)>getDirection()){
            setDirection(getDirection()+90);
        }else{
            setDirection(getDirection()-90);
        }
        setRealRotation(getRealRotation()+90);
        addLife(bonus);
        bonus/=2;
    }
}
