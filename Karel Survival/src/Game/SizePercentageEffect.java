package Game;
/**
 * Write a description of class DamagePercentageEffect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SizePercentageEffect extends PercentageEffect
{
    public SizePercentageEffect(double percentage, int duration, GridObject source){
        super(percentage, duration, source);
    }
    public SizePercentageEffect(double percentage, int duration, GridObject source, EffectID id){
        super(percentage, duration, source, id);
    }
    public void onApply(){
        getTarget().scaleSize(getPercentage(), getID());
    }
    public void onClear(){
        getTarget().scaleSize(1.0/getPercentage(), getID());
    }
}
