package com.karel.game;
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
public class Wizard extends Boss
{
    private static final int sporereload = 700;         // The minimum delay between firing the gun.
    private ArrayList<FungalZombie> spores;
    private ArrayList<GridEntity> turrets;
    private ArrayList<GridEntity> lasers;
    private EffectID wizardStun;// The stuns placed on all other enemies on death
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
    private static final int laserReload = 700;
    private int laserAmmo;
    private int deathwaitcooldown = 100;
    public String getStaticTextureURL(){return "angryheraldzareln.png";}
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
        scaleTexture(90, 90);
        setRealRotation(0);
        setSpeed(0);
        startHealthAsBoss(10000, 5);
        phase = 1;
        spores = new ArrayList<FungalZombie>();
        turrets = new ArrayList<GridEntity>();
        lasers = new ArrayList<GridEntity>();
        wizardStun = new EffectID(this);
        addEffectImmunities(TeamSwitchEffect.class);
    }
    //ovveride this
    public int getXP(){
        return 10000;
    }
    public void behave(){
        if(phase==6||phase==7){
            deathwaitcooldown--;
            setSpeed(0.5);
            Sounds.play("bossshivering");
            if(!checkForSurvivors()&&deathwaitcooldown<=0){
                phase++;
                setSpeed(3);
                Sounds.play("bossupgrade");
            }
            walk(Greenfoot.getRandomNumber(360), 1);
            return;
        }else if(phase==7){
            behaveInLastPhase();
            return;
        }
        int op = phase;
        phase = Math.max(5-(5*(getHealth()-1)/getMaxHealth()), op);
        if(op!=phase){
            healSelfAttack();
            fungalDefense();
            hashived = false;
            if(phase==5){
                tankAttack();
            }
        }
        //if(true)return;// DEBUG
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
        
        checkLasers();
        laserAmmo++;
        if(laserAmmo>=laserReload){
            laserAttack();
            laserAmmo = 0;
        }
        
        meleeammo++;
        if(distanceTo(getTarget())<40&&meleeammo>=meleereload){
            damageIgnoreShield(getTarget(), 150);// melee attack
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
    public void checkLasers(){
        for(int i = lasers.size()-1; i >= 0; i--){
            if(lasers.get(i).isDead()){
                lasers.remove(i);
            }
        }
        if(lasers.size()>=6){
            for(int i = 0; i < lasers.size()-6; i++){
                lasers.get(i).applyEffect(new FatalPoisonEffect(10, 1, this));
            }
        }
    }
    public void fungalDefense(){
        for(GridEntity g: getGEsInRange(110)){
            if(isAggroTowards(g)){
                spawnZombieAt(new FungalZombie(), g.getRealX(), g.getRealY());
            }
        }
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
        r.applyEffect(new SpeedPercentageEffect(0, 800, this));
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
                toSpawn.applyShield(new ArmorShield(new ShieldID(this, "hornetneck protection"), 200));
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
    public void laserAttack(){
        double sx = 0, sy = 0;
        int n = 0;
        for(GridEntity g: lasers){
            sx+=g.getRealX()-getRealX();
            sy+=g.getRealY()-getRealY();
            n++;
        }
        for(GridEntity g: spores){
            sx+=g.getRealX()-getRealX();
            sy+=g.getRealY()-getRealY();
            n++;
        }
        for(GridEntity g: turrets){
            sx+=g.getRealX()-getRealX();
            sy+=g.getRealY()-getRealY();
            n++;
        }
        if(n==0)return;
        sx/=n;
        sy/=n;
        spawnZombieAt(new LaserSquad(phase, lasers, this), getRealX()*2-sx, getRealY()*2-sy);
    }
    public void rocketAttack(){
        for(int i = 75; i <= 195; i+=30){
            spawnZombie(new RocketZombie(50, i));
        }
    }
    public void startLastPhase(){
        phase = 6;
        for(GridEntity g: getWorld().allEntities){
            if(g!=this&&(isAlliedWith(g)||g instanceof Zombie)){
                g.stun(wizardStun);
                g.applyEffect(new LifestealEffect(20, 1, -1, this));
            }
        }
    }
    public boolean checkForSurvivors(){
        phase = 7;
        boolean found = false;
        for(GridEntity g: getWorld().allEntities){
            if(g!=this&&(isAlliedWith(g)||g instanceof Zombie)&&!g.isDead()){
                found = true;
                if(g.canMove()||g.canAttack())g.stun(wizardStun);
                if(!g.hasEffect(LifestealEffect.class))g.applyEffect(new LifestealEffect(20, 1, -1, this));
            }
        }
        return found;
    }
    public int getPhase(){
        return phase;
    }
    public boolean canBePulled(){
        return false;
    }
    public boolean isWall(){
        return false;
    }
    public void die(GridObject killer){
        if(phase==5){
            setHealth(1);
            applyShield(new ExternalImmunityShield(new ShieldID(this), -1));
            startLastPhase();
        }else{
            explodeFleshConfetti(20);
            Sounds.play("bossdie");
            super.die(killer);
        }
    }
    public String getName(){
        return "Setup Wizard";
    }
}
