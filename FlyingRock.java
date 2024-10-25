import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class FlyingRock extends Projectile
{
    /** The damage this bullet will deal */
    private static final int damage = 200;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int life = 150;
    private int speed;
    private Arc path;
    private double rot;
    protected int frame;
    private int range;
    private boolean dieonhit = true;
    public FlyingRock()
    {
        super(null);
    }
    public double getGravity(){
        return 3;
    }
    public FlyingRock(double rotation, double targetdistance, double height, GridObject source)
    {
        super(source);
        rot = rotation;
        //addForce(new Vector(rotation, 15));
        //speed = ;
        path = new Arc(targetdistance, height, getGravity());
        setTeam(source.getTeam());
        setExplosionRange(100);
    }
    
    public void setExplosionRange(int amt){
        range = amt;
    }
    public int getExplosionRange(){
        return range;
    }
    
    public double percentDone(){
        return path.percentDone(frame);
    }
    public Arc getPath(){
        return path;
    }
    
    public double getDirection(){
        return rot;
    }
    
    public void setDieOnHit(boolean val){
        dieonhit = val;
    }
    
    /**
     * The bullet will damage asteroids if it hits them.
     */
    public void applyPhysics()
    {
        if(getRealHeight()<0) {
            checkAsteroidHit();
            if(dieonhit)die();
        } 
        else {
            move(rot, path.getRate());// = -(x^2+bx)/b
            setRealHeight(path.getHeight(frame));
            continueFrame();
        }
    }
    public void die(){
        getWorld().removeObject(this);
        super.die();
    }
    public void continueFrame(){
        frame++;
    }
    
    /**
     * Check whether we have hit an asteroid.
     */
    public void checkAsteroidHit()
    {
        List<GridEntity> l = getObjectsInRange(getExplosionRange(), GridEntity.class);
        if(getNumTargets()<0){
            for(GridEntity g:l){
                if(g!=null&&isAggroTowards(g)){
                    doHit(g);
                }
            }
        }else{
            int n = getNumTargets();
            while(n>0){
                int i = getNearest((List<? extends GridObject>)l);
                if(i==-1){
                    break;
                }
                doHit(l.remove(i));
                n--;
            }
            setNumTargets(0);
        }
        Sounds.play("rocksmash");
    }
    
    public void doHit(GridEntity g){
        damage(g, damage);
    }
}