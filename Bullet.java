import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.ArrayList;
import java.util.HashSet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Bullet extends Projectile
{
    /** The damage this bullet will deal */
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int life = 40;
    private double speed;
    private double direction;
    
    
    public Bullet()
    {
        super(null);
    }
    
    public Bullet(double rotation, HashSet<GridEntity> h, GridObject source)
    {
        super(source);
        hitstory = h;
        setDirection(rotation);
        setRealRotation(rotation+90);
        //addForce(new Vector(rotation, 15));
        setTeam(source.getTeam());
        setSpeed(15);
        setNumTargets(1);
        setAggression(true);
        setSelfHarm(false);
        setMultiHit(true);
    }
    public Bullet(double rotation, GridObject source)
    {
        this(rotation, new HashSet<GridEntity>(), source);
    }
    public void setLife(int life){
        this.life = life;
    }
    public int getLife(){
        return life;
    }
    public void addLife(int add){
        this.life+=add;
    }
    public void setSpeed(double speed){
        this.speed = speed;
    }
    public double getSpeed(){
        return speed;
    }
    public void setDirection(double rot){
        direction = rot;
    }
    public double getDirection(){
        return direction;
    }
    /**
     * The bullet will damage asteroids if it hits them.
     */
    public void applyPhysics()
    {
        if(life <= 0) {
            expire();
        } 
        else {
            if(getNumTargets()==0){
                finish();
                return;
            }
            life--;
            move(getDirection(), getSpeed());
            checkHit();
        }
    }
    
    
    public void die(){
        getWorld().removeObject(this);
        super.die();
    }
    public void expire(){die();}
    public void finish(){die();}
}