package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZTorpedo extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    double vx, vy;
    double tvx, tvy;
    double cdirspeed = 0.2;
    double pspeed = 15;
    public ZTorpedo(double rotation, GridObject source)
    {
        super(rotation, source);
        setLife(30);
        setDamage(50);
        setSpeed(0);
        tvx = Math.cos((rotation-90)*Math.PI/180)*pspeed;
        tvy = Math.sin((rotation-90)*Math.PI/180)*pspeed;
        vx = tvx;
        vy = tvy;
    }
    public void setRealRotation(double rot){
        super.setRealRotation(rot+90);
    }
    public void applyPhysics(){
        super.applyPhysics();
        setLife(30);
        GridEntity g = getTarget();
        double dir = face(g==null?getWorld().getPlayer():g, true)-90;
        tvx = Math.cos(dir*Math.PI/180)*pspeed;
        tvy = Math.sin(dir*Math.PI/180)*pspeed;
        if(vx>tvx+cdirspeed/2){
            vx-=cdirspeed;
        }else if(vx<tvx-cdirspeed/2){
            vx+=cdirspeed;
        }
        if(vy>tvy+cdirspeed/2){
            vy-=cdirspeed;
        }else if(vy<tvy-cdirspeed/2){
            vy+=cdirspeed;
        }
        translate(vx, vy);
    }
    public GridEntity getTarget(){
        GridEntity next = null;
        for(GridEntity g: getWorld().allEntities()){
            if(!getHitStory().contains(g)&&isAggroTowards(g)&&(next==null||distanceTo(g)<distanceTo(next))){
                next = g;
            }
        }
        return next;
    }
}
