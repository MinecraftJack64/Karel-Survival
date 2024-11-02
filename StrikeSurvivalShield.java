/**
 * Write a description of class StrikeSurvivalShield here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StrikeSurvivalShield extends Shield//allow the holder to survive a certain number of lethal strikes with 1 health
{
    private int health;
    public StrikeSurvivalShield(ShieldID myG, int health){
        super(myG);
        this.health = health;
    }
    public int processDamage(int dmg, GridObject source){
        if(dmg>=getHolder().getHealth()){
            health--;
            source.notifyDamage(getHolder(), dmg);
            if(health<=0){
                remove();
                Sounds.play("armorshieldbreak");
            }else{
                Sounds.play("survivelethalstrike");
            }
            return getHolder().getHealth()-1;
        }else{
            return dmg;
        }
    }
    public boolean damage(int amt, GridObject source){
        remove();
        return false;
    }
    public boolean canBreak(){
        return true;
    }
    public int getHealth(){
        return health;
    }
}