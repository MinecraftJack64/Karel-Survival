/**
 * Write a description of class SpeedPercentageEffect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SpeedPercentageEffect extends PercentageEffect
{
    // instance variables - replace the example below with your own
    /**
     * Constructor for objects of class SpeedPercentageEffect
     */
    public SpeedPercentageEffect(double percentage, int duration)
    {
        super(percentage, duration);
        //
    }
    public boolean affect(GridEntity e){
        //System.out.println("Poison info: "+nextinterval+" "+remainingtimes);
        duration--;
        e.setSpeedMultiplier(percentage);
        if(duration<=0){
            e.setSpeedMultiplier(1);
            return false;
        }
        return true;
    }
}
