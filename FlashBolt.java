import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class FlashBolt extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    
    public FlashBolt(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(25);
        setLife(12);
        setDamage(40);
        setNumTargets(10);
        setMultiHit(false);
    }
    public void animate(){
        setRealRotation(getRealRotation()+30);
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        //point to next target
        GridEntity next = null;
        for(GridEntity g: getWorld().allEntities()){
            if(targ!=g&&!getHitStory().contains(g)&&isAggroTowards(g)&&(next==null||distanceTo(g)<distanceTo(next))){
                next = g;
            }
        }
        if(next==null){
            setNumTargets(0);
        }else{
            double monangle = face(next, false);
            setDirection(monangle);
            if(getLife()<=4){
                setLife(4);
            }
        }
    }
}
