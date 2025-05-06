package Game;
/**
 * Write a description of class ImmunityShield here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SuperWeedwackerShield extends Shield
{
    private int duration;
    public SuperWeedwackerShield(ShieldID myG, int health){
        super(myG);
        this.duration = health;
    }
    public int processDamage(int dmg, GridObject source){
        source.notifyDamage(getHolder(), dmg);
        return 0;
    }
    public void tick(){
        duration--;
        if(duration==0){
            remove();
        }
    }
    public boolean damage(int amt, GridObject source){
        processDamage(amt, source);
        remove();
        getHolder().hit(getHolder().getHealth(), source);
        return false;
    }
    public boolean canBreak(){
        return true;
    }
}


