import greenfoot.*;
import java.util.ArrayList;

/*
 * classes
 * fila-mint
 * reinforce-mint: shieldzombie
 * bombard-mint: explodingzombie
 * arma-mint: zombie that controls bombs that drop down
 * contain-mint
 * spear-mint
 * pepper-mint
 * enchant-mint
 * winter-mint
 * appease-mint: shooterzombie stays at a distance and shoots small low damage bullets at you, slower than normal
 * ail-mint: poisonzombie that occasionally lets out a cloud that poisons you and boosts nearby zombies and leaves behind a poison area on death. Will slow you if you are too close to it. Very slow and tanky
 * conceal-mint: ninjazombies remain invisible and wait for the perfect opportunity to strike. They run towards you and when they're next to you, they throw 9 ninja stars while quickly circling you 3 times. They then run away and wait again. They occasionally reveal their location for short intervals of time
 * enlighten-mint
 * enforce-mint: zombie
 */
/**
 * Write a description of class Wizard here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Wizard extends Zombie
{
    private static final int sporereload = 700;         // The minimum delay between firing the gun.
    private ArrayList<FungalZombie> spores;
    private ArrayList<GridEntity> turrets;
    private int sporeammo;               // How long ago we fired the gun the last time.
    private int hivereload = 850;
    private int hiveammo;
    private boolean hashived;
    private int turretreload = 600;
    private int turretammo;
    private int rocketreload = 800;
    private int rocketammo;
    private int meleereload = 15;
    private int meleeammo;
    private int deathwaitcooldown = 100;
    private GreenfootImage rocket = new GreenfootImage("heraldzareln.png");
    private int phase;//5 phases
    /**
     * Initilise this rocket.
     */
    public Wizard()
    {
        sporeammo = 400;
        meleeammo = 15;
        hiveammo = 400;
        turretammo = -300;
        rocketammo = 200;
        rocket.scale(90, 90);
        setImage(rocket);
        setRealRotation(0);
        setSpeed(0);
        startHealthAsBoss(10000, 5);
        phase = 1;
        spores = new ArrayList<FungalZombie>();
        turrets = new ArrayList<GridEntity>();
    }
    //ovveride this
    public int getXP(){
        return 10000;
    }
    public void behave(){
        if(phase==6){
            deathwaitcooldown--;
            setSpeed(0.5);
            Sounds.play("bossshivering");
            if(!checkForSurvivors()&&deathwaitcooldown<=0){
                phase++;
                setSpeed(3);
                Sounds.play("bossupgrade");
            }
            walk(Greenfoot.getRandomNumber(361), 1);
            return;
        }else if(phase==7){
            behaveInLastPhase();
            return;
        }
        int op = phase;
        phase = Math.max(5-(5*(getHealth()-1)/getMaxHealth()), op);
        if(op!=phase){
            healSelfAttack();
            hashived = false;
            if(phase==5){
                tankAttack();
            }
            getWorld().getGame().getSpawner().setBossPhase(phase);
        }
        
        if(checkSpores()){
            sporeammo++;
        }
        if(sporeammo>=sporereload){
            sporeAttack();
            sporeammo = 0;
        }
        
        if(phase>1&&!hashived){
            hiveammo++;
        }
        if(hiveammo>=hivereload){
            hiveDropAttack();
            hashived = true;
            hiveammo = 0;
        }
        
        if(checkTurrets())turretammo++;
        if(turretammo>=turretreload){
            turretAttack();
            turretammo = 0;
        }
        
        rocketammo++;
        if(rocketammo>=rocketreload){
            rocketAttack();
            rocketammo = 0;
        }
        
        meleeammo++;
        if(distanceTo(getTarget())<40&&meleeammo>=meleereload){
            damage(getTarget(), 100);
            meleeammo = 0;
        }
    }
    public boolean trap(){
        return false;
    }
    public void behaveInLastPhase(){
        //setSpeed(3);
        hit(10000, this);//intended
        //super.behave();
    }
    public boolean checkSpores(){
        for(FungalZombie z:spores){
            if(!z.isDead()){
                return false;
            }
        }
        if(spores.size()>0){
            spores.clear();
        }
        return true;
    }
    public boolean checkTurrets(){
        for(GridEntity z:turrets){
            if(!z.isDead()){
                return false;
            }
        }
        if(turrets.size()>0){
            turrets.clear();
        }
        return true;
    }
    public void spawnZombieAt(GridObject z, double x, double y){
        double d = distanceTo(x, y);
        ZombieDropper pack = new ZombieDropper(getAngle(x, y)+90, d, d, z, this);
        getWorld().addObject(pack, getRealX(), getRealY());
        Sounds.play("zombiedropshoot");
    }
    public void spawnZombie(GridObject z){
        getWorld().addObject(z, getRealX(), getRealY());
    }
    //Attacks
    public void healSelfAttack(){
        spawnZombieAt(new DoctorZombie(this), getXAtOffset(2), getYAtOffset(1));
        spawnZombieAt(new DoctorZombie(this), getXAtOffset(-1), getYAtOffset(2));
        spawnZombieAt(new DoctorZombie(this), getXAtOffset(-2), getYAtOffset(-1));
        spawnZombieAt(new DoctorZombie(this), getXAtOffset(1), getYAtOffset(-2));
    }
    public void tankAttack(){
        spawnZombieAt(newRDZ(), getXAtOffset(1), getYAtOffset(1));
        spawnZombieAt(newRDZ(), getXAtOffset(1), getYAtOffset(-1));
        spawnZombieAt(newRDZ(), getXAtOffset(-1), getYAtOffset(1));
        spawnZombieAt(newRDZ(), getXAtOffset(-1), getYAtOffset(-1));
    }
    public RussianDollZombie newRDZ(){
        RussianDollZombie r = new RussianDollZombie();
        r.applyeffect(new SpeedPercentageEffect(0, 800));
        return r;
    }
    public void sporeAttack(){
        for(int i = 0; i < Math.min(phase, 4); i++){
            double x = getRandomCellX();
            double y = getRandomCellY();
            FungalZombie z = new FungalZombie();
            spores.add(z);
            spawnZombieAt(z, x, y);
        }
    }
    public void hiveDropAttack(){
        Zombie toSpawn;
        if(phase>3){
            toSpawn = new HornetNeckZombie();
            if(phase==5){
                toSpawn.applyShield(new ArmorShield(toSpawn, 200));
            }
        }else{
            toSpawn = new HivemindZombie();
        }
        getWorld().addObject(toSpawn, getRealX(), getRealY());
    }
    public void turretAttack(){
        for(int i = 0; i < phase/2+1; i++){
            double x = getRandomCellX();
            double y = getRandomCellY();
            spawnZombieAt(new Turret(phase, turrets, this), x, y);
        }
    }
    public void rocketAttack(){
        for(int i = 75; i <= 195; i+=30){
            spawnZombie(new RocketZombie(50, i));
        }
    }
    public void startLastPhase(){
        getWorld().getGame().getSpawner().setBossPhase(6);
        phase = 6;
        for(GridEntity g: getWorld().allEntities){
            if(g!=this&&(isAlliedWith(g)||g instanceof Zombie)){
                g.stun();
                g.applyeffect(new LifestealEffect(20, 1, -1, this));
            }
        }
    }
    public boolean checkForSurvivors(){
        getWorld().getGame().getSpawner().setBossPhase(7);
        boolean found = false;
        for(GridEntity g: getWorld().allEntities){
            if(g!=this&&(isAlliedWith(g)||g instanceof Zombie)&&!g.isDead()){
                found = true;
                if(g.canMove()||g.canAttack())g.stun();
                if(!g.hasEffect(LifestealEffect.class))g.applyeffect(new LifestealEffect(20, 1, -1, this));
            }
        }
        return found;
    }
    public boolean canBePulled(){
        return false;
    }
    public boolean isWall(){
        return false;
    }
    public boolean applyeffect(Effect e){
        if(!(e instanceof TeamSwitchEffect)){
            super.applyeffect(e);
            return true;
        }
        return false;
        //System.out.println("Effect applied");
    }
    public void die(GridObject killer){
        if(phase==5){
            setHealth(1);
            applyShield(new ExternalImmunityShield(this, -1));
            startLastPhase();
        }else{
            explodeFleshConfetti(20);
            Sounds.play("bossdie");
            super.die(killer);
        }
    }
}