package Game;
/**
 * Write a description of class GOD here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GOD extends GridEntity
{
    public GOD(){
        startHealthShield(new ImmunityShield(new ShieldID(this), -1));
        setTeam("god");
    }
    public boolean willNotify(GridObject source){
        return false;
    }
}
