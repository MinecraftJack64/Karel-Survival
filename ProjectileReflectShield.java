/**
 * Write a description of class ImmunityShield here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ProjectileReflectShield extends Shield
{
    private int duration;
    public ProjectileReflectShield(GridEntity myG, int health){
        super(myG);
        this.duration = health;
    }
    public int processDamage(int dmg, GridObject source){
        source.notifyDamage(getHolder(), dmg);
        if(source instanceof Bullet){
            Bullet psource = (Bullet)source;
            psource.setDirection(psource.getDirection()-180);
            psource.setTeam(getHolder().getTeam());
            psource.setSelfHarm(true);
            if(psource.getNumTargets()!=-1){
                psource.setNumTargets(psource.getNumTargets()+1);
            }
        }
        return 0;//does not stop damage if source is self
    }
    public void tick(){
        duration--;
        if(duration==0){
            getHolder().removeShield();
        }
    }
    public boolean damage(int amt, GridObject source){
        return false;
    }
    public boolean canBreak(){
        return duration>=0;
    }
}
