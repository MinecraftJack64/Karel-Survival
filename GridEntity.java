import java.util.ArrayList;
import java.util.Iterator;

/**
 * Write a description of class GridEntity here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class GridEntity extends GridObject
{
    ArrayList<Effect> effects = new ArrayList<Effect>();
    private int maxHealth = 200;//TMP
    private int health = maxHealth;
    HealthBar healthBar;
    public double movementspeed = 5, speedmultiplier = 1;
    private boolean canmove = true;
    private boolean isactive = true;
    private boolean detectable = true;
    boolean isdead = false;
    private GridObject killer;
    public Shield shield;
    ShieldBar shieldBar;
    public boolean isDead(){
        return isdead;
    }

    public void die(GridObject source){
        isdead = true;
        killer = source;
        while(killer instanceof SubAffecter&&((SubAffecter)killer).getSource()!=null){
            killer = ((SubAffecter)killer).getSource();
        }
        getWorld().allEntities().remove(this);
        super.die();
    }
    
    public GridObject getKiller(){
        return killer;
    }
    
    public void revive(){
        isdead = false;
        getWorld().allEntities().add(this);
        super.revive();
    }
    public void reviveWithHealth(){
        revive();
        startHealth(getMaxHealth());
    }

    public double getSpeed(){
        return movementspeed;
    }

    public double getMultipliedSpeed(){
        return movementspeed*speedmultiplier;
    }

    public void setSpeedMultiplier(double perc){
        speedmultiplier = perc;
    }

    public void setSpeed(double speed){
        movementspeed = speed;
    }

    public void walk(double angle, double speed){
        if(canmove&&(getRealHeight()<=0||canFly()))move(angle, getMultipliedSpeed()*speed);
    }
    
    public boolean canFly(){
        return false;
    }

    public boolean applyeffect(Effect e){
        effects.add(e);
        e.appliedTo(this);
        return true;
        //System.out.println("Effect applied");
    }
    public boolean hasEffect(Class cls){
        for(Effect e: effects){
            if(cls.isInstance(e)){
                return true;
            }
        }
        return false;
    }
    public void stun(){
        canmove = false;
        isactive = false;
    }

    public void unstun(){
        canmove = true;
        isactive = true;
    }
    
    public void mute(){
        isactive = false;
    }
    
    public void unmute(){
        isactive = true;
    }

    public boolean canAttack(){
        return isactive;
    }

    public boolean canMove(){
        return canmove;
    }
    
    public boolean collidesWithWalls(){
        return true;
    }

    public void applyeffects(){ 
        for (int i=effects.size()-1;i>=0;i--) {
            Effect e = effects.get(i);
            //System.out.println("Effect taking hold");
            if(!e.affect(this)){
                effects.remove(e);
                //System.out.println("Effect wore off");
            }
            //effects.remove(i);
            if(isDead()){
                break;
            }
        }
    }
    public void startHealth(int amt){
        startHealth(amt, true);
    }
    public void startHealth(int amt, boolean showbar){
        if(healthBar!=null){
            KWorld.me.removeObject(healthBar);
        }
        health = maxHealth = amt;
        if(showbar){
            healthBar = new HealthBar(amt,40,5,this);
            KWorld.me.addObject(healthBar, getRealX()*1.0, getRealY()-40);
        }
    }
    public void startHealthAsBoss(int amt, int numphases){
        health = maxHealth = amt;
        if(healthBar!=null){
            KWorld.me.removeObject(healthBar);
        }
        healthBar = new BossBar(amt,500,10,this);
        healthBar.divideIntoPhases(numphases);
        KWorld.me.gameUI().addBossBar((BossBar)healthBar);
    }
    public void setHealth(int h){
        health = h;
        if(healthBar!=null)healthBar.setValue(h);
    }
    public int getHealth(){
        return health;
    }
    public GridEntity getTarget(){
        return null;
    }
    public int getMaxHealth(){
        return maxHealth;
    }
    public double getPercentHealth(){
        return health*1.0/maxHealth;
    }
    public void damage(int amt){
        setHealth(getHealth()-amt);
    }
    public void applyShield(Shield thing){
        if(shield!=null)return;
        shield = thing;
        if(shieldBar!=null){
            getWorld().removeObject(shieldBar);
        }
        shieldBar = new ShieldBar(thing.getHealth(), 40, 5, this);
        KWorld.me.addObject(shieldBar, getRealX()*1.0, getRealY()-50);
    }
    public void replaceShield(Shield thing){
        shield = thing;
        if(shieldBar!=null){
            getWorld().removeObject(shieldBar);
        }
        shieldBar = new ShieldBar(thing.getHealth(), 40, 5, this);
        KWorld.me.addObject(shieldBar, getRealX()*1.0, getRealY()-50);
    }
    public void removeShield(){
        shield = null;
        if(shieldBar!=null){
            getWorld().removeObject(shieldBar);
            shieldBar = null;
        }
    }
    public boolean hasShield(){
        return shield!=null;
    }
    public Shield getShield(){
        return shield;
    }
    public boolean knockBack(double r, double d, double h, GridObject source){
        if(!canBePulled()){
            return false;
        }
        applyeffect(new KnockbackEffect(r, d, h, source));
        return true;
    }
    public void hit(int dmg, GridObject source){
        //dec health
        if(shield!=null){
            dmg = shield.processDamage(dmg, source);
            if(hasShield())shieldSetHealth(shield.getHealth());
        }
        if(dmg>0){
            Sounds.play("hit");
        }
        damage(dmg);
        if(!source.covertDamage()&&willNotify(source))source.notifyDamage(this, dmg);
        if(getHealth()<=0)die(source);
    }
    public void shieldSetHealth(int amt){
        shieldBar.setValue(amt);
    }
    public void tickshield(){
        if(shield!=null){
            shield.tick();
        }
    }
    public void heal(int amt){
        damage(-amt);
        if(getHealth()>getMaxHealth()){
            setHealth(getMaxHealth());
        }
        Sounds.play("heal");
    }
    public void cleanUp(){
        if(healthBar!=null)getWorld().removeObject(healthBar);
        //remove effect icons
        //remove status bars
        //remove all components
    }
    public boolean trap(){
        if(healthBar!=null)healthBar.setVisible(false);
        if(shieldBar!=null)shieldBar.setVisible(false);
        return true;
    }
    public void untrap(){
        if(healthBar!=null)healthBar.setVisible(true);
        if(shieldBar!=null)shieldBar.setVisible(true);
    }
    public boolean canDetect(){//can be detected
        return detectable;
    }
    public void setDetectable(boolean b){
        detectable = b;
    }
    public void kAct()
    {
        tickshield();
        applyeffects();
        applyphysics();
        if(!isDead())behave();
    }
    public void behave(){}
    public String getName(){return "Grid Entity";}
}
