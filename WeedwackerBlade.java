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
    public WeedwackerBlade(double d, double a, GridEntity source){
        distance = d;
        angle = a;
        this.source = source;
        startHealth(1, false);
        applyShield(new MetalHealthShield(this, 5));
    }
    public WeedwackerBlade(GridEntity source){
        this(75, -90, source);
    }
    public GridObject getSource(){
        return source;
    }
    public void kAct(){
        if(source!=null){
            setRealLocation(source.getRealX()+distance*Math.cos((source.getRealRotation()+angle)*Math.PI/180), source.getRealY()+distance*Math.sin((source.getRealRotation()+angle)*Math.PI/180));
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
        if(!hasShield()){
            applyShield(new MetalHealthShield(this, 5));
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
        removeShield();
        applyShield(new ProjectileReflectShield(this, 400));
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
