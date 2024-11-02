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
    private ArrayList<Shield> shields = new ArrayList<Shield>();
    private int maxshields = 10;
    private ArrayList<ShieldBar> shieldBars = new ArrayList<ShieldBar>();
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
    public void setMaxShields(int amt){
        maxshields = amt;
    }
    public boolean acceptShield(Shield thing){
        return (acceptExternalShields()||thing.getID().getKey()==this);
    }
    public void applyShield(Shield thing){
        applyShield(shields.size(), thing);
    }
    public void applyShield(int pos, Shield thing){
        if(shields.size()<maxshields&&acceptShield(thing)){
            shields.add(pos, thing);
            thing.setHolder(this);
        }
        shieldBars.add(pos, new ShieldBar(thing.getHealth(), 40, 5, pos, this));
        KWorld.me.addObject(shieldBars.get(pos), getRealX()*1.0, getRealY()-50-10*pos);
    }
    public void replaceShield(Shield thing){
        replaceShield(shields.size()-1, thing);
    }
    public void replaceShield(ShieldID old, Shield thing){
        int i = indexOfShield(old);
        if(i>=0){
            replaceShield(i, thing);
        }
    }
    public void replaceShield(int i, Shield thing){
        if(i>=0&&i<shields.size()&&acceptShield(thing)){
            shields.set(i, thing);
            thing.setHolder(this);
            if(shieldBars.get(i)!=null){
                getWorld().removeObject(shieldBars.get(i));
            }
            shieldBars.set(i, new ShieldBar(thing.getHealth(), 40, 5, i, this));
            getWorld().addObject(shieldBars.get(i), getRealX()*1.0, getRealY()-50-10*i);
        }
    }
    public void removeShield(){
        shields.remove(shields.size()-1);
    }
    public void removeShield(ShieldID id){
        int i = indexOfShield(id);
        if(i>=0){
            shields.remove(i);
        }
    }
    public void removeShield(int id){
        shields.remove(id);
        if(shieldBars.get(id)!=null){
            getWorld().removeObject(shieldBars.get(id));
            shieldBars.remove(id);
        }
    }
    public boolean hasShield(){
        return shields.size()>0;
    }
    public boolean hasShield(ShieldID s){
        return indexOfShield(s)>=0;
    }
    public int indexOfShield(ShieldID s){
        for(int i = shields.size()-1; i >= 0; i--){
            if(shields.get(i).getID().equals(s)){
                return i;
            }
        }
        return -1;
    }
    public boolean acceptExternalShields(){//do not let other grid entities apply shields to me
        return true;
    }
    public Shield getShield(){//get the last shield
        return shields.get(shields.size()-1);
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
        for(int i = shields.size()-1; i >=0; i--){//process damage through each shield
            dmg = shields.get(i).processDamage(dmg, source);
        }
        if(dmg>0){
            Sounds.play("hit");
        }
        damage(dmg);
        if(!source.covertDamage()&&willNotify(source))source.notifyDamage(this, dmg);
        if(getHealth()<=0)die(source);
    }
    public void shieldSetHealth(int id, int amt){
        shieldBars.get(id).setValue(amt);
        shieldBars.get(id).updateID(id);
    }
    public void tickShields(){
        for(int i = shields.size()-1; i>=0; i--){
            shields.get(i).tick();
            shieldSetHealth(i, shields.get(i).getHealth());
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
        if(shieldBars!=null)shieldBars.forEach((s)->{s.setVisible(false);});
        return true;
    }
    public void untrap(){
        if(healthBar!=null)healthBar.setVisible(true);
        if(shieldBars!=null)shieldBars.forEach((s)->{s.setVisible(true);});
    }
    public boolean canDetect(){//can be detected
        return detectable;
    }
    public void setDetectable(boolean b){
        detectable = b;
    }
    public void kAct()
    {
        tickShields();
        applyeffects();
        applyphysics();
        if(!isDead())behave();
    }
    public void behave(){}
    public String getName(){return "Grid Entity";}
}
