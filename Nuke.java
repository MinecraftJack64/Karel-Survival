import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Nuke extends Projectile
{
    /** The damage this bullet will deal */
    Target targ;
    /** A bullet looses one life each act, and will disappear when life = 0 */
    public Nuke()
    {
        super(null);
    }
    public Nuke(GridObject source)
    {
        super(source);
        setRealRotation(90);
        setTeam(source.getTeam());
        setRealHeight(2000);
    }
    
    /**
     * The bullet will damage asteroids if it hits them.
     */
    public void applyPhysics()
    {
        if(targ==null){
            targ = new Target();
            getWorld().addObject(targ, getRealX(), getRealY());
        }
        if(getRealHeight()<=0) {
            setRealHeight(0);
            checkAsteroidHit();
            getWorld().removeObject(this);
            if(targ!=null){
                getWorld().removeObject(targ);
                targ = null;
            }
            super.die();
        } 
        else {
            setRealHeight(getRealHeight()-20);
        }
    }
    
    /**
     * Check whether we have hit an asteroid.
     */
    public void checkAsteroidHit()
    {
        List<GridEntity> l = getObjectsInRange(1000, GridEntity.class);
        for(GridEntity g:l){
            if(g!=null&&isAggroTowards(g)){
                doHit(g);
            }
        }
        getWorld().addObject(new Explosion(10), getRealX(), getRealY());
        Sounds.play("rocketcrash");
    }
    public int getDamage(double distance){
        return (int)(400+Math.pow(2, -(distance-975)/100));
    }
    
    public boolean covertDamage(){
        return true;
    }
}