package com.karel.game.gridobjects.gridentities.zombies.cloudserver;
import java.util.ArrayList;
import com.karel.game.Dasher;
import com.karel.game.DasherDoer;
import com.karel.game.ExternalImmunityShield;
import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Location;
import com.karel.game.Sounds;
import com.karel.game.World;
import com.karel.game.effects.EffectID;
import com.karel.game.effects.FatalPoisonEffect;
import com.karel.game.effects.LifestealEffect;
import com.karel.game.gridobjects.gridentities.zombies.Boss;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.gridobjects.gridentities.zombies.ZombieDropper;
import com.karel.game.gridobjects.gridentities.zombies.exorcist.ExorcistZombie;
import com.karel.game.gridobjects.gridentities.zombies.firebreather.FirebreatherZombie;
import com.karel.game.gridobjects.gridentities.zombies.gatherer.GathererZombie;
import com.karel.game.gridobjects.gridentities.zombies.portal.PortalZombie;
import com.karel.game.gridobjects.gridentities.zombies.shaman.ShamanZombie;
import com.karel.game.gridobjects.gridentities.zombies.splitter.SplitterZombie;
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
    private CloudServerSpawner spawner;
    private static final int warriorreload = 700;         // The minimum delay between firing the gun.
    private static final int hoverHeight = 200;
    private int hoverCooldown; // 1000 in sky, 500 on ground
    private boolean isHovering;
    private Dasher dash;
    private boolean isFindingAllies;
    private ArrayList<WarriorZombie> warriors;
    private ArrayList<GridEntity> turrets;
    private ArrayList<AssaultPoint> aps;
    private ArrayList<ExorcistZombie> exors;
    private EffectID wizardStun;// The stuns placed on all other enemies on death
    private int warriorammo;               // How long ago we fired the gun the last time.
    private int meleereload = 15;
    private int meleeammo;
    private int portalReload = 400;
    private int portalAmmo;
    private int assaultammo = 0;
    private int assaultreload = 500;
    private int lightningammo;
    private int lightningreload = 300;
    private int cloudAmmo;
    private int cloudReload = 500;
    private ZRainCloud cloud;
    private int groundCooldown = 60; // 60
    private int primeLightning; // 30
    private int primeDashes; // 30
    private int startDashCooldown; // 60
    private int remainingDashes; // 3
    private int remainingHail; // 16
    private int nthHail;
    private int throwBallCooldown; // 30
    private int effectammo = 0; // for random effects
    private int deathwaitcooldown = 100;
    private ShieldID hoverShield = new ShieldID(this, "hover");
    public String getStaticTextureURL(){return "angryheraldzareln.png";}
    private int phase;//5 phases
    private boolean toSpawnShaman;
    private static final int hailRange = 250;
    /**
     * Initilise this rocket.
     */
    public CloudServer()
    {
        warriorammo = 400;
        meleeammo = 15;
        assaultammo = 200;
        cloudAmmo = 0;
        portalAmmo = 0;
        lightningammo = 200;
        scaleTexture(90, 90);
        setRotation(0);
        setSpeed(0);
        startHealthAsBoss(9000, 3);
        phase = 1;
        warriors = new ArrayList<WarriorZombie>();
        turrets = new ArrayList<GridEntity>();
        aps = new ArrayList<AssaultPoint>();
        exors = new ArrayList<ExorcistZombie>();
        spawner = new CloudServerSpawner();
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
            splitterAttack(5);
            flameDefense();
            if(phase==3){
                gathererAttack();
            }
            isHovering = false;
            hoverCooldown = 0;
            //switch instantly
        }
        lightningammo++;
        if(lightningammo>=lightningreload){
            lightningAttack();
        }
        if(isHovering&&(cloud==null||!cloud.isInWorld()))
            cloudAmmo++;
        if(cloudAmmo>=cloudReload){
            cloudAttack();
            cloudAmmo = 0;
        }
        if(checkWarriors()){
            warriorammo++;
        }
        if(warriorammo>=warriorreload){
            warriorAttack();
            warriorammo = 0;
        }
        if(phase>=2){
            portalAmmo++;
        }
        if(portalAmmo>=portalReload){
            portalAmmo = 0;
            portalAttack();
        }

        if(dash!=null){if(!dash.dash()){
            dash = null;
            if(toSpawnShaman){
                addObjectHere(new ShamanZombie());
                toSpawnShaman = false;
            }
        }else{
            canSwitchHover = false;
        }}
        else if(!isHovering&&isOnGround()){
            if(remainingHail==0&&primeLightning==0)face(getTarget(), true);
            canSwitchHover = false;
            if(primeLightning>0){
                primeLightning--;
                if(primeLightning<=0){
                    boltAttack();
                }
            }else if(primeDashes>0){
                primeDashes--;
                if(primeDashes<=0){
                    startDashCooldown = 60;
                    remainingDashes = 3;
                }
            }else if(startDashCooldown>0){
                startDashCooldown--;
                if(startDashCooldown<=0){
                    remainingDashes--;
                    dashAttack();
                    if(remainingDashes>0){
                        startDashCooldown = 60;
                    }
                }
            }else if(remainingHail>0){
                remainingHail--;
                hailAttack();
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
                                shamanAttack();
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
                knockBackOn(200, 100);
                if(phase>1){
                    splitterAttack(Greenfoot.getRandomNumber(2)+2);
                }
            }
        }
        if(isHovering&&!hasShield(hoverShield)){
            applyShield(new ExternalImmunityShield(hoverShield, -1));
        }else if(!isHovering&&hasShield(hoverShield)){
            removeShield(hoverShield);
        }

        effectammo++;
        if(effectammo>=600){
            applyHeal();
            effectammo = 0;
        }
        if(checkTurrets()){
            assaultammo++;
        }
        if(assaultammo>=assaultreload){
            turretAttack();
            assaultammo = 0;
        }
        
        clearExorcists();

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
        for(AssaultPoint z:aps){
            if(z.isInWorld()){
                return false;
            }
        }
        if(turrets.size()>0){
            turrets.clear();
        }
        if(aps.size()>0){
            aps.clear();
        }
        return true;
    }
    public void flameDefense(){
        for(GridEntity g: getGEsInRange(110)){
            if(isAggroTowards(g)){
                spawnZombieAt(new FirebreatherZombie(), g.getX(), g.getY());
            }
        }
    }
    public void clearExorcists(){
        for(int i = exors.size()-1; i>=0; i--){
            if(exors.get(i).isDead()){
                exors.remove(i);
            }
        }
    }
    public void spawnZombieAt(GridObject z, double x, double y){
        double d = distanceTo(x, y);
        ZombieDropper pack = new ZombieDropper(getAngle(x, y)+90, d, d, z, this);
        getWorld().addObject(pack, getX(), getY());
        Sounds.play("zombiedropshoot");
        if(phase>=2&&z instanceof GridEntity g){
            if(Greenfoot.getRandomNumber(5)==0&&exors.size()<phase){
                spawnZombieAt(Greenfoot.getRandomNumber(2)==0?new ExorcistZombie():new ExorcistZombie(g), x+Greenfoot.getRandomNumber(50)-25, y+Greenfoot.getRandomNumber(50)-25);
            }
        }
    }
    public void spawnAt(GridObject z, double x, double y){
        getWorld().addObject(z, x, y);
    }
    public void spawnZombie(GridObject z){
        getWorld().addObject(z, getX(), getY());
    }
    //Attacks
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
    public void portalAttack(){
        Location l = findCluster(1000, 300);
        if(l==null)return;
        spawnZombieAt(new PortalZombie(1, portalReload*2), l.x, l.y);
    }
    public void spawnWarrior(double x, double y){
        WarriorZombie z = new WarriorZombie();
        warriors.add(z);
        spawnZombieAt(z, x, y);
    }
    public void gathererAttack(){
        for(int i = 0; i < 2; i++){
            spawnZombieAt(new GathererZombie(), getRandomCellX(), getRandomCellY());
        }
    }
    public void splitterAttack(int num){
        for(int i = 0; i < 360; i+=360/num){
            SplitterZombie o = new SplitterZombie();
            o.h.applyEffect(new FatalPoisonEffect(3, 1, this));
            spawnZombieAt(o, getX()+getBranchX(i, 100), getY()+getBranchY(i, 100));
        }
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
    public void cloudAttack(){
        if(phase==1)addObjectHere(cloud = new ZRainCloud(this));
    }
    public void clusterAttackTest(){
        isFindingAllies = true;
        Location t = findCluster(10000, 300);
        isFindingAllies = false;
        if(t!=null){
            double ang = face(t.x, t.y, false);
            double dist = distanceTo(t.x, t.y);
            addObjectHere(new ZHailBall(ang, dist, 100, this));
        }
    }
    public boolean isPotentialTarget(GridEntity t){
        return isFindingAllies?isAlliedWith(t):super.isPotentialTarget(t);
    }
    public void dashAttack(){
        dash = new DasherDoer(getRotation(), 18, 25, 100, (g)->{
            if(isAggroTowards(g)){
                g.knockBack(face(g, false), 30, 200, this);
                damage(g, 100);
            }
        }, this);
    }
    public void hailAttack(){
        addObjectHere(new ZHail(getRotation()+15*Math.sin(nthHail*Math.PI/4), this));
        nthHail++;
    }
    public void boltAttack(){
        addObjectHere(new ZBolt(getRotation(), this, phase>2));
    }
    public void shamanAttack(){
        Location l = findCluster(1000, 200);
        if(l==null)return;
        double ang = face(l.x, l.y, false);
        double dist = distanceTo(l.x, l.y);
        int speed = 20;
        toSpawnShaman = true;
        dash = new Dasher(ang, speed, (int)(dist/speed), this);
    }
    public void turretAttack(){
        for(int i = 0; i < phase-1; i++){
            double x = getRandomCellX();
            double y = getRandomCellY();
            AssaultPoint s = new AssaultPoint(turrets, this);
            aps.add(s);
            spawnZombieAt(s, x, y);
        }
    }
    public void applyHeal(){
        int amt = 200+phase*50;
        explodeOn(1000, (ge) -> {
            if(isAlliedWith(ge)){
                heal(ge, amt);
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
