package Game;
/**
 * Write a description of class HitObserver here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface HitObserver  
{
    public void notifyHit(Hitter me, GridEntity ge);
}
