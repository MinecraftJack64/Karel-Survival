package com.karel.game.gridobjects.gridentities.zombies.cloudserver;
import java.util.ArrayList;

import com.karel.game.ArmorShield;
import com.karel.game.BombTurret;
import com.karel.game.Dasher;
import com.karel.game.DasherDoer;
import com.karel.game.ExternalImmunityShield;
import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.LaserSquad;
import com.karel.game.Sounds;
import com.karel.game.Turret;
import com.karel.game.World;
import com.karel.game.effects.EffectID;
import com.karel.game.effects.FatalPoisonEffect;
import com.karel.game.effects.LifestealEffect;
import com.karel.game.effects.PowerPercentageEffect;
import com.karel.game.effects.ReloadPercentageEffect;
import com.karel.game.effects.SpeedPercentageEffect;
import com.karel.game.gridobjects.gridentities.zombies.Boss;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.gridobjects.gridentities.zombies.ZombieDropper;
import com.karel.game.gridobjects.gridentities.zombies.doctor.DoctorZombie;
import com.karel.game.gridobjects.gridentities.zombies.fungal.FungalZombie;
import com.karel.game.gridobjects.gridentities.zombies.hivemind.HivemindZombie;
import com.karel.game.gridobjects.gridentities.zombies.hornetneck.HornetNeckZombie;
import com.karel.game.gridobjects.gridentities.zombies.rocket.RocketZombie;
import com.karel.game.gridobjects.gridentities.zombies.russiandoll.RussianDollZombie;
import com.karel.game.gridobjects.gridentities.zombies.warrior.WarriorZombie;
import com.karel.game.weapons.ShieldID;
import com.karel.game.particles.Explosion;

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
public class CloudServer extends Boss
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.boss};
    private static final int warriorreload = 700;         // The minimum delay between firing the gun.
    private static final int hoverHeight = 200;
    private int hoverCooldown; // 1000 in sky, 500 on ground
    private boolean isHovering;
    private Dasher dash;
    private ArrayList<WarriorZombie> warriors;
    private ArrayList<GridEntity> turrets;
    private ArrayList<GridEntity> lasers;
    private EffectID wizardStun;// The stuns placed on all other enemies on death
    private int warriorammo;               // How long ago we fired the gun the last time.
    private int hivereload = 850;
    private int hiveammo;
    private boolean hashived;
    private int turretreload = 600;
    private int turretammo;
    private int rocketreload = 800;
    private int rocketammo;
    private int meleereload = 15;
    private int meleeammo;
    private int lightningammo;
    private int lightningreload = 300;
    private int groundCooldown = 60; // 60
    private int primeLightning; // 30
    private int primeDashes; // 30
    private int startDashCooldown; // 30
    private int remainingDashes; // 3
    private int remainingHail; // 16
    private int throwBallCooldown; // 30
    private int effectammo = 0; // for random effects
    private static final int laserReload = 700;
    private int laserAmmo;
    private int deathwaitcooldown = 100;
    private ShieldID hoverShield = new ShieldID(this, "hover");
    public String getStaticTextureURL(){return "angryheraldzareln.png";}
    private int phase;//5 phases
    private static final int hailRange = 200;
    /**
     * Initilise this rocket.
     */
    public CloudServer()
    {
        warriorammo = 400;
        meleeammo = 15;
        hiveammo = 400;
        turretammo = -300;
        rocketammo = 200;
        scaleTexture(90, 90);
        setRotation(0);
        setSpeed(0);
        startHealthAsBoss(9000, 3);
        phase = 1;
        warriors = new ArrayList<WarriorZombie>();
        turrets = new ArrayList<GridEntity>();
        lasers = new ArrayList<GridEntity>();
        wizardStun = new EffectID(this);
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 10000;
    }
    public void behave(){
        if(phase==4){
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
        }else if(phase==5){
            behaveInLastPhase();
            return;
        }
        boolean canSwitchHover = true;
        int op = phase;
        phase = Math.max(3-(3*(getHealth()-1)/getMaxHealth()), op);
        if(op!=phase){
            //
        }
        lightningammo++;
        if(lightningammo>=lightningreload){
            lightningAttack();
        }
        if(checkWarriors()){
            warriorammo++;
        }
        if(warriorammo>=warriorreload){
            warriorAttack();
            warriorammo = 0;
        }

        if(dash!=null)if(!dash.dash()){
            dash = null;
            //if(shamanToSpawn)
        }else{
            canSwitchHover = false;
        }else if(!isHovering&&isOnGround()){
            face(getTarget(), true);
            canSwitchHover = false;
            if(primeLightning>0){
                primeLightning--;
                if(primeLightning<=0){
                    //boltAttack();
                }
            }else if(primeDashes>0){
                primeDashes--;
                if(primeDashes<=0){
                    startDashCooldown = 30;
                    remainingDashes = 3;
                }
            }else if(startDashCooldown>0){
                startDashCooldown--;
                if(startDashCooldown<=0){
                    remainingDashes--;
                    dashAttack();
                    if(remainingDashes>0){
                        startDashCooldown = 30;
                    }
                }
            }else if(remainingHail>0){
                remainingHail--;
                //hailAttack();
            }
            else if(throwBallCooldown>0){
                throwBallCooldown--;
                if(throwBallCooldown<=0){
                    //ballAttack();
                }
            }else if(groundCooldown>0){
                canSwitchHover = true;
                groundCooldown--;
                if(groundCooldown<=0){
                    groundCooldown = 60;
                    //Choose attack
                    if(distanceTo(getTarget())<hailRange&&Greenfoot.getRandomNumber(4)<3){
                        remainingHail = 16;
                    }else{
                        switch(Greenfoot.getRandomNumber(phase)){
                            case 0:
                                primeDashes = 30;
                                break;
                            case 1:
                                primeLightning = 30;
                                break;
                            case 2:
                                //shamanAttack();
                                break;
                        }
                    }
                }
            }
        }

        if(hoverCooldown>0){
            hoverCooldown--;
        }else if(canSwitchHover){
            if(isHovering = !isHovering){
                hoverCooldown = 1000;
                setRotation(0);
            }else{
                hoverCooldown = 500;
            }
        }
        if(isHovering&&getHeight()<hoverHeight){
            setHeight(getHeight()+10);
        }else if(!isHovering&&!isOnGround()){
            setHeight(getHeight()-10);
            if(isOnGround()){
                knockBackOnEnemies(200, 100);
            }
        }
        if(isHovering&&!hasShield(hoverShield)){
            applyShield(new ExternalImmunityShield(hoverShield, -1));
        }else if(!isHovering&&hasShield(hoverShield)){
            removeShield(hoverShield);
        }
        if(true)return;
        
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

        effectammo++;
        if(effectammo>=600){
            applyRandomEffect();
            effectammo = 0;
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
    public boolean checkWarriors(){
        for(WarriorZombie z: warriors){
            if(!z.isDead()){
                return false;
            }
        }
        if(warriors.size()>0){
            warriors.clear();
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
        if(lasers.size()>=3){
            for(int i = 0; i < lasers.size()-3; i++){
                lasers.get(i).applyEffect(new FatalPoisonEffect(10, 1, this));
            }
        }
    }
    public void fungalDefense(){
        for(GridEntity g: getGEsInRange(110)){
            if(isAggroTowards(g)){
                spawnZombieAt(new FungalZombie(), g.getX(), g.getY());
            }
        }
    }
    public void spawnZombieAt(GridObject z, double x, double y){
        double d = distanceTo(x, y);
        ZombieDropper pack = new ZombieDropper(getAngle(x, y)+90, d, d, z, this);
        getWorld().addObject(pack, getX(), getY());
        Sounds.play("zombiedropshoot");
    }
    public void spawnAt(GridObject z, double x, double y){
        getWorld().addObject(z, x, y);
    }
    public void spawnZombie(GridObject z){
        getWorld().addObject(z, getX(), getY());
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
    public void warriorAttack(){
        int gx = getWorld().gridwidth*World.gridSize/2, gy = getWorld().gridheight*World.gridSize/2;
        double lx = getX()-gx, rx = getX()+gx, by = getY()-gy, uy = getY()+gy;
        if(Greenfoot.getRandomNumber(2)==1){
            spawnWarrior(lx, getY());
            spawnWarrior(rx, getY());
            spawnWarrior(getX(), uy);
            spawnWarrior(getX(), by);
        }else{
            spawnWarrior(lx, by);
            spawnWarrior(rx, by);
            spawnWarrior(lx, uy);
            spawnWarrior(rx, uy);
        }
    }
    public void spawnWarrior(double x, double y){
        WarriorZombie z = new WarriorZombie();
        warriors.add(z);
        spawnZombieAt(z, x, y);
    }
    public void lightningAttack(){
        for(int i = 0; i < phase+2; i++){
            double x = getRandomCellX();
            double y = getRandomCellY();
            SuperLightningStrike z = new SuperLightningStrike(this);
            spawnAt(z, x, y);
        }
        lightningammo = 0;
    }
    public void dashAttack(){
        dash = new DasherDoer(getRotation(), 18, 25, 100, (g)->{
            g.knockBack(face(g, false), 30, 200, this);
        }, this);
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
        getWorld().addObject(toSpawn, getX(), getY());
    }
    public void turretAttack(){
        int num = phase/2+1;
        if(phase>=5)num = 1;
        for(int i = 0; i < num; i++){
            double x = getRandomCellX();
            double y = getRandomCellY();
            if(Greenfoot.getRandomNumber(3)<2)spawnZombieAt(new Turret(phase, turrets, this), x, y);
            else spawnZombieAt(new BombTurret(phase, turrets, this), x, y);
        }
    }
    public void laserAttack(){
        double sx = 0, sy = 0;
        int n = 0;
        for(GridEntity g: lasers){
            sx+=g.getX()-getX();
            sy+=g.getY()-getY();
            n++;
        }
        for(GridEntity g: warriors){
            sx+=g.getX()-getX();
            sy+=g.getY()-getY();
            n++;
        }
        for(GridEntity g: turrets){
            sx+=g.getX()-getX();
            sy+=g.getY()-getY();
            n++;
        }
        if(n==0)return;
        sx/=n;
        sy/=n;
        spawnZombieAt(new LaserSquad(phase, lasers, this), getX()*2-sx, getY()*2-sy);
    }
    public void rocketAttack(){
        for(int i = 75; i <= 195; i+=30){
            spawnZombie(new RocketZombie(50, i));
        }
    }
    public void applyRandomEffect(){
        if(phase>5)return;//no random effects past last phase
        boolean affectsAllies = Greenfoot.getRandomNumber(2)==0;
        int r = Greenfoot.getRandomNumber(3);
        System.out.println("Applying random effect "+r+" with allies? "+affectsAllies);
        explodeOn(1000, (ge) -> {
            if(ge!=this&&isAggroTowards(ge)&&!affectsAllies){
                double amt = 1-phase/20.0;
                if(r==0)ge.applyEffect(new SpeedPercentageEffect(amt, 600, this));
                else if(r==1)ge.applyEffect(new PowerPercentageEffect(amt, 600, this));
                else if(r==2)ge.applyEffect(new ReloadPercentageEffect(amt, 600, this));
            }else if(isAlliedWith(ge)){
                double amt = 1+phase/5.0;
                if(r==0)ge.applyEffect(new SpeedPercentageEffect(amt, 600, this));
                else if(r==1)ge.applyEffect(new PowerPercentageEffect(amt, 600, this));
                else if(r==2)ge.applyEffect(new ReloadPercentageEffect(amt, 600, this));
            }
        }, new Explosion(10));
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
        return "Cloud Server";
    }
    @Override
    public String getZombieID(){
        return "cloudserver";
    }
}
