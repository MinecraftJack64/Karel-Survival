import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;
import java.util.ArrayList;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Lasso extends Reelin
{
    private List<GridEntity> targets;
    private boolean immobiletarget;//if a target is immobile, pull source to center of all immovable targets instead
    private int latchx, latchy;//where the source is pulled too, the center point between all immovable objects
    public Lasso(double rotation, double targetdistance, GridObject source)
    {
        super(rotation, targetdistance, targetdistance/2, source);
        setExplosionRange(100);
        targets = new ArrayList<GridEntity>();
        setDamage(0);
        setSpeed(10);
    }
    public void doReturn(){
        if(!updateCenter()){
            for(GridEntity target: targets){
                target.stun();
                target.pullTowards(this, 15);
            }
            super.doReturn();
        }else{
            pullTowards(latchx, latchy, 20);
            getSource().stun();
            getSource().pullTowards(this, 10);
        }
    }
    public void die(){
        for(GridEntity target:targets){
            if(!target.isDead()){
                target.unstun();
                target.applyeffect(new StunEffect(20, getSource()));
            }
            if(immobiletarget){
                if(getSource()!=null&&!getSource().isDead()){
                    getSource().unstun();
                }
            }
        }
        super.die();
    }
    //clean up list, removing dead entities, set the center in case of immovability, and return if it should pull or not
    public boolean updateCenter(){//returns true if there are immovable targets
        latchx = 0;
        latchy = 0;
        int immobs = 0;
        for(int i = targets.size()-1; i >= 0; i--){
            GridEntity target = targets.get(i);
            if(target.isDead()){
                targets.remove(i);
            }else if(!target.canBePulled()){
                immobs++;
                latchx+=target.getRealX();
                latchy+=target.getRealY();
            }
        }
        if(immobs>0){latchx/=immobs;
        latchy/=immobs;}
        return immobs>0;
    }
    public void doHit(GridEntity targ){
        super.doHit(targ);
        targets.add(targ);
        targ.stun();
    }
    public GridEntity getSource(){
        return (GridEntity)(super.getSource());
    }
    public double getGravity(){
        return 3;
    }
}
