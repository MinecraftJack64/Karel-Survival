package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;
import java.util.ArrayList;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class MoleSinkhole extends Bullet
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    ArrayList<GridEntity> suckedenemies = new ArrayList<GridEntity>();
    EffectID stun;
    public MoleSinkhole(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(0);
        setLife(105);
        setDamage(0);
        setNumTargets(-1);
        setMultiHit(false);
        stun = new EffectID(this);
    }
    public void applyPhysics(){
        setLife(2);
        setRealRotation(getRealRotation()+30);
        List<GridEntity> d = getGEsInRange(55);
        for(GridEntity g:d){
            if(isAggroTowards(g)&&!suckedenemies.contains(g)&&g.canBePulled()){
                suckedenemies.add(g);
            }
        }
        for(int i = suckedenemies.size()-1; i>=0; i--){
            GridEntity g = suckedenemies.get(i);
            if(g.isDead()||!g.canBePulled()){
                g.unstun(stun);
                suckedenemies.remove(i);
            }else{
                g.stun(stun);
                g.pullTowards(this, 20);
            }
        }
        super.applyPhysics();
    }
    public void die(){
        for(GridEntity g:suckedenemies){
            g.unstun(stun);
        }
        super.die();
    }
}
