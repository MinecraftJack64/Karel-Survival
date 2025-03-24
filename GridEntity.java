import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet;

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
    private double exposureMultiplier = 1;
    private HashSet<EffectID> immobilizers;//these store the affectors behind each effect
    private boolean canmove = true;//if you can move
    private HashSet<EffectID> silencers;
    private boolean isactive = true;//if you can attack or do stuff
    private HashSet<EffectID> hiders;
    private boolean detectable = true;//if enemies can see you
    boolean isdead = false;
    private GridObject killer;
    private Shield healthShield;
    private ArrayList<Shield> shields = new ArrayList<Shield>();
    private int maxshields = 10;
    private ArrayList<ShieldBar> shieldBars = new ArrayList<ShieldBar>();
    private HashSet<Class> effectImmunities = new HashSet<Class>();
    public boolean isDead(){
        return isdead;
    }
    public void notifyWorldRemove(){
        super.notifyWorldRemove();
        getWorld().allEntities().remove(this);
    }
    public void notifyWorldAdd(){
        super.notifyWorldRemove();
        getWorld().allEntities().add(this);
    }
    public void die(GridObject source){
        isdead = true;
        killer = source.getParentAffecter();
        setHealth(0);
        super.die();
        if(removeOnDeath())try{getWorld().removeObject(this);}catch(Exception e){}//remove from world if set to true
    }
    
    public GridObject getKiller(){
        return killer;
    }
    
    public void revive(){
        isdead = false;
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

    public void setSpeedMultiplier(double perc, EffectID ctrl){
        speedmultiplier = perc;
    }
    
    public double getExposure(){
        return exposureMultiplier;
    }
    public void setExposure(double perc, EffectID ctrl){
        exposureMultiplier = perc;
    }
    public void setExposure(double perc){
        exposureMultiplier = perc;
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

    public boolean applyEffect(Effect e){
        if(isImmuneTo(e)){
            return false;
        }
        //TODO: check immunities: for i in immunities, if i.blocks(e)return false;
        switch(e.getCollisionProtocol()){
            case 1://stacking
                Effect other = getExistingEffect(e);
                if(other!=null){
                    other.stack(e);
                    return true;
                }
            break;
            case 2://dominant
                Effect other2 = getExistingEffect(e);
                if(other2!=null){
                    clearEffect(other2);
                    return true;
                }
            break;
            case 3://recessive
                Effect other3 = getExistingEffect(e);
                if(other3!=null){
                    return false;
                }
            break;
        }
        effects.add(e);
        e.appliedTo(this);
        return true;
    }
    public boolean hasEffect(Class cls){
        for(Effect e: effects){
            if(cls.isInstance(e)){
                return true;
            }
        }
        return false;
    }
    public boolean hasEffect(EffectID id){
        for(Effect e: effects){
            if(e.getID().equals(id)){
                return true;
            }
        }
        return false;
    }
    public Effect getExistingEffect(Effect f){
        for(Effect e: effects){
            if(e.equals(f)){
                return e;
            }
        }
        return null;
    }
    public void clearEffect(EffectID id){
        for(int i = effects.size()-1; i >= 0; i--){
            if(effects.get(i).getID().equals(id)){
                effects.remove(i);
            }
        }
    }
    public void clearEffect(Effect eff){
        effects.remove(eff);
    }
    public HashSet<Class> getEffectImmunities(){
        return effectImmunities;
    }
    public void addEffectImmunities(Class... clss){
        for(Class cls: clss){
            getEffectImmunities().add(cls);
        }
    }
    public boolean isImmuneTo(Effect eff){
        for(Class cls: getEffectImmunities()){
            if(cls.isInstance(eff)){
                return true;
            }
        }
        return false;
    }
    public void stun(EffectID ctrl){
        immobilize(ctrl);
        mute(ctrl);
    }

    public void unstun(EffectID ctrl){
        mobilize(ctrl);
        unmute(ctrl);
    }
    
    public void mute(EffectID ctrl){
        isactive = false;
    }
    
    public void unmute(EffectID ctrl){
        isactive = true;
    }
    
    public void immobilize(EffectID ctrl){
        canmove = false;
    }
    
    public void mobilize(EffectID ctrl){
        canmove = true;
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
    public boolean removeOnDeath(){
        return true;
    }

    public void applyEffects(){ 
        for (int i = effects.size()-1; i>= 0; i--) {
            effects.get(i).affect();
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
            healthBar = new LifeBar(amt,40,5,this);
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
        if(healthBar!=null&&!hasHealthShield())healthBar.setValue(h);
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
        if(acceptShield(thing))applyShield(shields.size(), thing);
    }
    public void applyShield(int pos, Shield thing){
        if(shields.size()<maxshields&&acceptShield(thing)){
            shields.add(pos, thing);
            thing.applyTo(this);
            shieldBars.add(pos, new ShieldBar(thing.getHealth(), 40, 5, pos, this));
            KWorld.me.addObject(shieldBars.get(pos), getRealX()*1.0, getRealY()-50-10*pos);
        }
    }
    public void startHealthShield(Shield amt){
        startHealthShield(amt, true);
    }
    public void startHealthShield(Shield thing, boolean showbar){
        if(healthBar!=null){
            KWorld.me.removeObject(healthBar);
        }
        healthShield = thing;
        thing.applyTo(this);
        health = maxHealth = 1;
        if(showbar){
            healthBar = new ShieldBar(thing.getHealth(),40,5,-1,this);
            KWorld.me.addObject(healthBar, getRealX()*1.0, getRealY()-40);
        }
    }
    public Shield getHealthShield(){
        return healthShield;
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
        if(hasShield())removeShield(shields.size()-1);
    }
    public void removeShield(ShieldID id){
        if(hasHealthShield()&&getHealthShield().getID().equals(id)){
            removeShield(-1);
        }
        int i = indexOfShield(id);
        while(i>=0){
            removeShield(i);
            i = indexOfShield(id);
        }
    }
    public void removeShield(int id){
        if(id>=0){
            shields.remove(id);
            getWorld().removeObject(shieldBars.get(id));
            shieldBars.remove(id);
        }else if(id==-1&&hasHealthShield()){
            kill(this);
        }
    }
    public boolean hasShield(){
        return shields.size()>0;
    }
    public boolean hasShield(ShieldID s){
        return indexOfShield(s)>=0;
    }
    public boolean hasHealthShield(){
        return healthShield!=null;
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
    public Shield getShield(int id){
        return id>shields.size()?null:shields.get(id);
    }
    public Shield getShield(ShieldID s){
        for(int i = shields.size()-1; i >= 0; i--){
            if(shields.get(i).getID().equals(s)){
                return shields.get(i);
            }
        }
        return null;
    }
    public boolean knockBack(double r, double d, double h, GridObject source){
        if(!canBePulled()){
            return false;
        }
        applyEffect(new KnockbackEffect(r, d, h, source));
        return true;
    }
    public void hit(int dmg, GridObject source){
        //dec health
        for(int i = shields.size()-1; i >=0; i--){//process damage through each shield
            dmg = shields.get(i).processDamage(dmg, source);
        }
        hitIgnoreShield(dmg, source);
    }
    public void hitIgnoreShield(int dmg, GridObject source){
        //TODO: some shields can ignore them
        /*for(int i = shields.size()-1; i >=0; i--){//process damage through each shield
            shields.get(i);
        }*/
        dmg*=getExposure();
        if(healthShield!=null){
            dmg = healthShield.processDamage(dmg, source);
            if(healthShield==null){
                setHealth(0);
            }
        }
        if(dmg>0){
            Sounds.play("hit");
        }
        damage(dmg);
        if(!source.covertDamage()&&willNotify(source))source.notifyDamage(this, dmg);
        if(getHealth()<=0)die(source);
    }
    public void kill(GridObject source){
        removeAllShields();
        die(source);
    }
    public void removeAllShields(){
        while(shields.size()>0){
            removeShield();
        }
    }
    public void shieldSetHealth(int id, int amt){
        if(id>=shieldBars.size())return;
        shieldBars.get(id).setValue(amt);
        shieldBars.get(id).updateID(id);
    }
    public void tickShields(){
        for(int i = shields.size()-1; i>=0; i--){
            Shield s = shields.get(i);
            s.tick();
            shieldSetHealth(i, s.getHealth());
        }
        if(hasHealthShield()){
            healthBar.setValue(healthShield.getHealth());
        }
    }
    public void heal(int amt, GridObject source){
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
        applyEffects();
        applyPhysics();
        if(!isDead())behave();
    }
    public void behave(){}
    public String getName(){return "Grid Entity";}
}


