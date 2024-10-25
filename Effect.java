/* all compound/mask effects
 * poison - ticking(damage)+healpercentage(<1)
 * slow - speedpercentage(<1)
 * speed - speedpercentage(>1)
 * weaken - damagepercentage(<1)
 * strength - damagepercentage(>1)
 * damage - hit the target
 * heal - heal the target
 * healing - ticking(heal)
 * ticking(effect) - do effect over interval
 * push - forced move in a direction
 * percentage effects:
 * speed(how much target can move)
 * damage(amount of damage that target can deal)
 * heal(the amount of healing that target can receive from sources)
 * reload(how fast target can reload their weapon)
 * shield(how much damage target receives)
 */
/**
 * Write a description of class Effect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Effect  
{
    public boolean affect(GridEntity source){return false;}
    public void appliedTo(GridEntity source){}
    public String getID(){
        return null;
    }
}
