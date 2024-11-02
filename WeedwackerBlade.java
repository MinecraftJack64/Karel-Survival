import java.util.List;

/**
 * Write a description of class WeedwackerBlade here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WeedwackerBlade extends GridEntity implements SubAffecter
{
    private GridEntity source;
    private int ammo = 0;//5
    private double distance, angle;
    private final ShieldID ultshieldid = new ShieldID(this, "ult"), healthshieldid = new ShieldID(this, "health");
    public WeedwackerBlade(double d, double a, GridEntity source){
        distance = d;
        angle = a;
        this.source = source;
        startHealth(1, false);
        setMaxShields(1);
        applyShield(new MetalHealthShield(healthshieldid, 5));
    }
    public boolean acceptExternalShields(){
        return false;
    }
    public WeedwackerBlade(GridEntity source){
        this(75, -90, source);
    }
    public GridObject getSource(){
        return source;
    }
    public void kAct(){
        if(source!=null){
            branchOut(source, angle+source.getRealRotation(), distance);
        }
        super.kAct();
    }
    public void animate(){
        if(canAttack())setRealRotation(getRealRotation()+30);
    }
    public void behave(){
        matchTeam(source);
        if(source.isDead()){
            die(this);
            return;
        }
        if(!hasShield(ultshieldid)){
            setOpacityPercent(1);
        }
        if(canAttack()){
            ammo++;
            if(ammo>=3){
                attack();
                ammo = 0;
            }
        }
    }
    public void die(GridObject source){
        super.die(source);
        getWorld().removeObject(this);
    }
    public void ult(){
        applyShield(new ProjectileReflectShield(ultshieldid, 400));
        setOpacityPercent(0.5);
    }
    public void notifyDamage(GridEntity s, int dmg){
        source.notifyDamage(s, dmg);
    }
    public void attack(){
        List<GridEntity> g = (List<GridEntity>)getIntersectingObjects(GridEntity.class);
        for(GridEntity e: g){
            if(isAggroTowards(e)){
                damage(e, 50);
            }
        }
    }
    public void hit(int dmg, GridObject s){
        source.notifyDamage(null, dmg);
        super.hit(dmg, s);
    }
    public boolean canDetect(){
        return false;
    }
    public boolean canAttack(){
        return source.canAttack();
    }
}
