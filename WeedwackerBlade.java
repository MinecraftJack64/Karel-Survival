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
    private int strength;
    private final ShieldID ultshieldid = new ShieldID(this, "ult"), healthshieldid = new ShieldID(this, "health");
    public WeedwackerBlade(double d, double a, GridEntity source){
        distance = d;
        angle = a;
        this.source = source;
        strength = 0;
        startHealthShield(new MetalShield(healthshieldid, 6));
        addEffectImmunities(PoisonEffect.class);
    }
    public boolean acceptExternalShields(){
        return false;
    }
    public WeedwackerBlade(GridEntity source){
        this(125, -90, source);
    }
    public GridObject getSource(){
        return source;
    }
    public void setStrength(int s){
        strength = s;
    }
    public void kAct(){
        if(source!=null){
            branchOut(source, angle+source.getRealRotation(), distance);
        }
        super.kAct();
    }
    public void animate(){
        //if(canAttack())setRealRotation(getRealRotation()+30);
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
    }
    public void spin(){
        if(canAttack()){
            setRealRotation(getRealRotation()+30);
            ammo++;
            if(ammo>=3){
                attack();
                ammo = 0;
            }
        }
    }
    public void die(GridObject source){
        super.die(source);
    }
    public void ult(){
        applyShield(new ProjectileReflectShield(ultshieldid, 400));
        setOpacityPercent(0.5);
    }
    public boolean hasUlt(){
        return hasShield(ultshieldid);
    }
    public void hit(int dmg, double d, GridObject s){
        notifyDamage(s.getParentAffecter(), dmg);
        super.hit(dmg, d, s);
    }
    public void notifyDamage(GridEntity s, int dmg){
        source.notifyDamage(s, 225);
    }
    public void immunize(){
        replaceShield(healthshieldid, new SuperWeedwackerShield(new ShieldID(this, "immune"), -1));
    }
    public void attack(){
        List<GridEntity> g = (List<GridEntity>)getIntersectingObjects(GridEntity.class);
        for(GridEntity e: g){
            if(isAggroTowards(e)){
                damage(e, 50+strength*10);
            }
        }
    }
    /*public void hit(int dmg, GridObject s){
        source.notifyDamage(this, dmg);
        super.hit(dmg, s);
    }*/
    public boolean canDetect(){
        return false;
    }
    public boolean canBePulled(){
        return false;
    }
    public boolean canAttack(){
        return source.canAttack();
    }
}
