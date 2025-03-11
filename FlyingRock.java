import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.HashSet;
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
    private HashSet<GridEntity> hitstory;
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
        hitstory = new HashSet<GridEntity>();
        path = new Arc(targetdistance, height, getGravity());
        setDamage(damage);
        setCollisionMode("radius");
        setRange(100);
        setNumTargets(-1);
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
        if(getRealHeight()<=0&&path.percentDone(frame)>0.5) {
            setRealHeight(0);
            checkHit();
            if(dieonhit)die();
        } 
        else {
            move(rot, path.getRate());
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
    @Override
    public HashSet<GridEntity> getHitStory(){
        return hitstory;
    }
    
    /**
     * Check whether we have hit an asteroid.
     */
    public void oldCheckAsteroidHit()
    {
        List<GridEntity> l = getGEsInRange(getRange());
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
                GridEntity g = l.remove(i);
                if(isAggroTowards(g)){
                    doHit(g);
                    n--;
                }
            }
            setNumTargets(0);
        }
        hitstory.addAll(l);
        Sounds.play("rocksmash");
    }
}
