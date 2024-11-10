import greenfoot.*;
/**
 * Write a description of class Beeper here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Beeper extends Collectible
{
    /** The damage this bullet will deal */
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int healing = 50;
    private int xp;
    public Beeper(int xp)
    {
        this.xp = xp;
    }
    public void collect(GridObject targ){
        heal(((GridEntity)targ), healing);
        super.collect(targ);
        Sounds.play("Beeper.collect");
    }
}
