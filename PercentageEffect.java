/**
 * Write a description of class PercentageEffect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PercentageEffect extends DurationEffect
{
    private double percentage;
    public PercentageEffect(double percentage, int duration, GridObject source)
    {
        super(duration, source);
        this.percentage = percentage;
    }
    public PercentageEffect(double percentage, int duration, GridObject source, EffectID id)
    {
        super(duration, source, id);
        this.percentage = percentage;
    }
    public double getPercentage(){
        return percentage;
    }
}
