package Game;
/**
 * Temporarily makes the target take more or less damage using PercentageShield
 */
public class DamageExposureEffect extends PercentageEffect
{
    // percentage is the amount of damage to be taken by target
    public DamageExposureEffect(double percentage, int duration, GridObject source){
        super(percentage, duration, source);
    }
    public DamageExposureEffect(double percentage, int duration, GridObject source, EffectID id){
        super(percentage, duration, source, id);
    }
    public void onApply(){
        getTarget().setExposure(getPercentage(), getID());
    }
    public void onClear(){
        getTarget().setExposure(1, getID());
    }
    public boolean isMalicious(){
        return getPercentage()>1;
    }
}
