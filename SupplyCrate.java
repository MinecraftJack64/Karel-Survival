import java.util.ArrayList;

/**
 * Write a description of class SupplyCrate here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SupplyCrate extends GridEntity
{
    ArrayList<GridObject> loot;
    public SupplyCrate(ArrayList<GridObject> todrop){
        startHealth(300);
        setTeam("lootbox");
        loot = todrop;
    }
    public SupplyCrate(){
        this(new ArrayList<GridObject>());
    }
    public SupplyCrate(GridObject thing){
        this();
        loot.add(thing);
    }
    public void die(GridObject killer){
        for(GridObject g:loot){
            getWorld().addObject(g, getRealX(), getRealY());
        }
        super.die(killer);
        getWorld().removeObject(this);
    }
    public boolean willNotify(GridObject source){
        return false;
    }
}