package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class SlammingSpear extends FlyingRock
{
    HashMap<GridEntity, Integer> scores;
    SpearWeapon myspear;
    public SlammingSpear(HashMap<GridEntity, Integer> hs, SpearWeapon sp, GridObject source)
    {
        super(0, 0, 30, source);
        scores = hs;
        myspear = sp;
        setRange(35);
        //if(load instanceof GridEntity)getWorld().allEntities.add((GridEntity)load);
    }
    public double getGravity(){
        return 1;
    }
    public void doHit(GridEntity g){
        damage(g, 100);
    }
    
    public void checkHit(){
        super.checkHit();
        for(var thing: scores.entrySet()){
            if(!thing.getKey().isDead()){
                thing.getKey().knockBack(face(thing.getKey(), false), 80, 40, this);
                if(thing.getValue()>=2){
                    thing.getKey().applyEffect(new StunEffect(65, this));
                }
            }
        }
        myspear.returnSpear(null);
    }
}
