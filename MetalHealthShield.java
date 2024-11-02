/**
 * Write a description of class MetalHealthShield here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MetalHealthShield extends Shield //kills the holder when it breaks
{
    private int health;
    public MetalHealthShield(ShieldID myG, int health){
        super(myG);
        this.health = health;
    }
    public int processDamage(int dmg, GridObject source){
        health--;
        source.notifyDamage(getHolder(), dmg+(health<0?health:0));
        if(health<=0){
            remove();
            getHolder().hit(getHolder().getHealth(), source);
            Sounds.play("armorshieldbreak");
        }
        return 0;
    }
    public boolean damage(int amt, GridObject source){
        
        processDamage(amt, source);
        return false;
    }
    public boolean canBreak(){
        return true;
    }
    public int getHealth(){
        return health;
    }
}
