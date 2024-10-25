import greenfoot.*;
import java.util.List;

/**
 * Write a description of class CellTurret here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CellTurret extends Pet
{

    private GreenfootImage rocket = new GreenfootImage("chickzareln.png");    
    private GreenfootImage rocket2 = new GreenfootImage("chickzareln.png");   
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private GridEntity myhive;
    private int ammo = 0;
    private Class target;
    private int damage = 10;
    private boolean isattacking;
    public boolean inposition;
    private static double speed = 5;
    private static double attackrange = 300;
    private static final int reloadtime = 5;
    private boolean cantransform;
    private double tx, ty;
    /**
     * Initilise this rocket.
     */
    public CellTurret(double x, double y, Class targ, GridEntity hive)
    {
        int scale = 30;
        rocket.scale(scale, scale);
        rocket2.scale(40, 40);
        setImage(rocket);
        setRotation(180);
        setSpeed(4);
        startHealth(400);
        inherit(hive);
        myhive = hive;
        target = targ;
        tx = x;
        ty = y;
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        double monangle = face(getTarget(), canMove());
        if(!inposition){
            setRealHeight(-5);
            walk(face(tx, ty, canMove()), 1);
            if(distanceTo(tx, ty)<2){
                inposition = true;
                setRealLocation(tx, ty);
            }
        }else{
            ammo++;
            if(isattacking&&distanceTo(getTarget())>attackrange){
                isattacking = false;
                setRealHeight(-7);
            }
            else if(!isattacking&&distanceTo(getTarget())<=attackrange){
                isattacking = true;
                knockEnemiesBack();
                setRealHeight(0);
            }
            if(isattacking&&getTarget()!=myhive&&ammo>reloadtime&&canAttack()){
                attack();
                ammo = 0;
            }
        }
        if(cantransform){
            setImage(rocket2);
        }else{
            setImage(rocket);
        }
    }
    public void setNewTarget(Class targ){
        target = targ;
    }
    public void knockEnemiesBack(){
        List<GridEntity> l = getObjectsInRange(50, GridEntity.class);
        for(GridEntity g:l){
            if(isAggroTowards(g)){
                damage(g, 10);
                g.knockBack(face(g, false), 50, 20, this);
                g.applyeffect(new PowerPercentageEffect(0.2, 90));
            }
        }
    }
    public boolean readyToTransform(Class pot){
        cantransform = myhive!=null&&!myhive.isDead()&&distanceTo(myhive)<35&&target!=pot&&pot!=null;
        return cantransform;
    }
    public void attack(){
        SmallAntibody bullet = new SmallAntibody(getRealRotation(), target, this);
        addObjectHere(bullet);
    }
    public void die(GridObject killer){
        List<GridEntity> l = getObjectsInRange(300, GridEntity.class);
        for(GridEntity g:l){
            if(isAggroTowards(g)){
                SmallAntibody bullet = new SmallAntibody(face(g, false), target, this);
                addObjectHere(bullet);
            }
        }
        super.die(killer);
        try{getWorld().removeObject(this);}catch(Exception e){}
    }
    public GridEntity getTarget(){
        //return getWorld().player;
        GridEntity candidate = getNearestTarget();
        if(candidate == null){
            candidate = getWorld().getPlayer();
        }
        return candidate;//use this for now
    }
    public GridEntity getNearestTarget(){
        GridEntity res = null;
        double max = 0;
        for(GridEntity e: getWorld().allEntities()){
            if(!isAggroTowards(e)||!e.canDetect()||(target!=null&&!target.isInstance(e))){
                continue;
            }
            if(distanceTo(e)<max||res==null){
                res = e;
                max = distanceTo(e);
            }
        }
        return res;
    }
    public void hit(int amt, GridObject source){
        super.hit((target!=null&&target.isInstance(source))?amt/10:amt, source);
    }
    public void notifyDamage(GridEntity target, int amt){
        if(myhive!=null&&!myhive.isDead()){
            myhive.notifyDamage(target, amt);
        }else{
            super.notifyDamage(target, amt);
        }
    }
    public boolean canDetect(){
        return getRealHeight()>=0;
    }
}
