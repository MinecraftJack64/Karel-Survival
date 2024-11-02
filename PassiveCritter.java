/**
 * Write a description of class PassiveCritter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PassiveCritter extends GridObject implements SubAffecter, ICritter
{
    private Player source;
    private static final int reloadtime = 30;
    private int ammo = 0;//60
    private int id;
    private double deg, dist;
    private LilCreatures spawner;
    public PassiveCritter(int id, double deg, double dist, LilCreatures spawner, Player source){
        this.source = source;//do not set power
        this.id = id;
        this.deg = deg;
        this.dist = dist;
        this.spawner = spawner;
    }
    public Player getSource(){
        return source;
    }
    public void kAct(){
        branchOut(getSource(), deg+getSource().getTargetRotation(), dist);
        ammo++;
        face(getSource().getTargetX(), getSource().getTargetY(), true);
        if(ammo>=reloadtime&&getSource().canAttack()&&!getSource().isDead()){
            attack();
            ammo = 0;
        }
    }
    public int getAmmo(){
        return ammo;
    }
    public void syncAmmo(int am){
        ammo = am;
    }
    public void attack(){
        CritterSlime bullet = new CritterSlime(getRealRotation(), getSource());
        addObjectHere(bullet);
    }
    public void deploy(){
        face(getSource().getTargetX(), getSource().getTargetY(), true);
        FlyingCritter bullet = new FlyingCritter(getRealRotation(), getSource(), spawner, id);
        addObjectHere(bullet);
        spawner.notifyCritterPhaseChange(id, bullet);
        die();
    }
    public void die(){
        getWorld().removeObject(this);
        super.die();
    }
}
