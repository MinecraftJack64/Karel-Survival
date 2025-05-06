package Game;
/**
 * Write a description of class ImmunityShield here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PercentageShield extends Shield
{
    private int duration;
    private double strength;
    public PercentageShield(ShieldID myG, double strength, int health){
        super(myG);
        this.duration = health;
        this.strength = strength;
    }
    public int processDamage(int dmg, GridObject source){
        //source.notifyDamage(getHolder(), dmg);
        return (int)(dmg*(1-strength));//does not stop damage if source is self
    }
    public void tick(){
        duration--;
        if(duration==0){
            remove();
        }
    }
    public boolean damage(int amt, GridObject source){
        if(duration>=0){
            duration-=amt/3;
            if(duration<=0){
                remove();
            }
        }
        return false;
    }
    public int getDuration(){
        return duration;
    }
    public int getHealth(){
        return -1;
    }
    public boolean canBreak(){
        return duration>=0;
    }
}


