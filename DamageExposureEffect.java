/**
 * Temporarily makes the target take more or less damage using PercentageShield
 */
public class DamageExposureEffect extends PercentageEffect
{
    // percentage is the amount of damage to be taken by target, shield strength = 1-percentage
    private ShieldID shield;
    public DamageExposureEffect(double percentage, int duration, GridObject source){
        super(percentage, duration, source);
        shield = new ShieldID(this);
    }
    public DamageExposureEffect(double percentage, int duration, GridObject source, EffectID id){
        super(percentage, duration, source, id);
        shield = new ShieldID(this);
    }
    public void onApply(){
        getTarget().applyShield(new PercentageShield(shield, 1-getPercentage(), -1));
    }
    public void onClear(){
        getTarget().removeShield(shield);
    }
}
