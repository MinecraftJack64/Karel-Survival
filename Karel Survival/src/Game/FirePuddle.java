package Game;

/**
 * Write a description of class FirePuddle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FirePuddle extends Puddle
{
    public FirePuddle(GridObject source){
        super(80, 30, 3, source);
        setDamage(50);
    }
}
