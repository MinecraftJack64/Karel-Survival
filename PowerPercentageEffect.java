/**
 * Write a description of class DamagePercentageEffect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PowerPercentageEffect extends PercentageEffect
{
    public PowerPercentageEffect(double percentage, int duration){
        super(percentage, duration);
    }
    public boolean affect(GridEntity e){
        //System.out.println("Poison info: "+nextinterval+" "+remainingtimes);
        duration--;
        e.setPower(percentage);
        if(duration<=0){
            e.setPower(1);
            return false;
        }
        return true;
    }
}
