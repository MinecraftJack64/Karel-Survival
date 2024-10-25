/**
 * Write a description of class Shield here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Shield  
{
    GridEntity myGE;
    public Shield(GridEntity myG){
        myGE = myG;
    }
    public int processDamage(int dmg, GridObject source){
        return dmg;
    }
    public boolean damage(int amt, GridObject s){return false;}//return true if shield breaks
    public void tick(){}
    public int getHealth(){
        return 100;
    }
    public boolean canBreak(){
        return false;
    }
    public GridEntity getHolder(){
        return myGE;
    }
}
