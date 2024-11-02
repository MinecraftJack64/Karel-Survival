import java.util.ArrayList;
import java.util.List;

/**
 * Write a description of class TickingfTimeBomb here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TickingTimeBomb extends GridEntity implements SubAffecter
{
    GridObject sourc;
    public TickingTimeBomb(GridObject source){
        startHealth(75);
        sourc = source;
        inherit(source);
    }
    public void behave(){
        damage(this, 1);
    }
    public void die(GridObject killer){
        explodeOn(50, 350);
        super.die(killer);
        getWorld().removeObject(this);
    }
    public boolean willNotify(){
        return false;
    }
    public void heal(int amt){
        super.heal(0);
    }
    public GridObject getSource(){
        return sourc;
    }
    public boolean acceptExternalShields(){
        return false;
    }
}
