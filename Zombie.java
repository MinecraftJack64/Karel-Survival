import greenfoot.*;
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
 * 
 * Zombie roles
 * Meatshield - take hits in place of others, usually low stats but spawn in abundance
 *  Zombie, Shield Zombie, Zombee
 * Tank - takes a lot of damage to kill, protects others behind it
 *  Fungal Zombie, Hard Hat Zombie, Ghost Zombie
 * Ranger - attacks from a safe range, usually low health
 *  Shooter Zombie, Marksman Zombie, Laser Zombie
 * Assault - aggressively tries to get up close and deals a lot of damage, usually low health
 *  Exploding Zombie, Ninja Zombie, Rocket Zombie
 * Spawner - creates other zombies to do the work for it
 *  Hivemind Zombie, Hornet Neck Zombie, Easter Zombie
 */
/**
 * Write a description of class Zombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Zombie extends GridEntity
{
    private static final int reloadtime = 5;         // The minimum delay between firing the gun.

    private int reloadDelayCount;               // How long ago we fired the gun the last time.

    private GreenfootImage rocket = new GreenfootImage("zareln.png");    
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int ammo = 0;
    private int damage = 10;
    private static double speed = 2;
    private static double attackrange = 30;
    private static double[] dhmult = new double[]{0.5, 0.75, 1, 1.5, 3};
    private static double[] dsmult = new double[]{0.8, 0.8, 1, 1.25, 1.5};
    /**
     * Initilise this rocket.
     */
    public Zombie()
    {
        reloadDelayCount = 5;
        rocket.scale(45, 45);
        setImage(rocket);
        setRotation(180);
        setSpeed(speed);
        setTeam("zombie");
        startHealth(200);
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void act()
    {
        processSounds();
        if(getWorld().gameStatus().equals("lose")){
            applyeffects();
            applyphysics();
            feast();
            return;
        }
        super.act();
    }
    public void processSounds(){
        //when sound cooldown 0, groan and set cooldown to new random value
    }

    /**
     * Check whether there are any key pressed and react to them.
     */
    public void behave() 
    {
        reloadDelayCount++;
        double monangle = face(getTarget(), canMove());
        //setRotation(getRotation()-1);
        ammo++;
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else{
            if(ammo>reloadtime&&canAttack()){
                attack();
                ammo = 0;
            }
        }
    }
    public void attack(){
        damage(getTarget(), damage);
    }
    public void feast() 
    {
        reloadDelayCount++;
        double monangle = face(getTarget(), canMove());
        //setRotation(getRotation()-1);
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
    }
    //ovveride this
    public int getXP(){
        return 100;
    }
    public boolean wasInOriginalWave(){
        return true;
    }
    public void die(GridObject killer){
        if(killer.isAggroTowards(this)){
            Beeper bullet = new Beeper(getXP());
            getWorld().addObject (bullet, getRealX(), getRealY());
            getWorld().increaseScore(getXP());
        }
        //if(wasInOriginalWave())getWorld().getGame().getSpawner().wavehealth--;
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
            if(!isAggroTowards(e)||!e.canDetect()){
                continue;
            }
            if(distanceTo(e)<max||res==null){
                res = e;
                max = distanceTo(e);
            }
        }
        return res;
    }
    public void explodeFleshConfetti(int amt){
        for(int i = 0; i < Greenfoot.getRandomNumber(amt)+amt; i++){
            ZFleshConfetti fc = new ZFleshConfetti(Greenfoot.getRandomNumber(360), this);
            getWorld().addObject(fc, getRealX(), getRealY());
        }
    }
    public void startHealth(int amt){
        super.startHealth((int)(amt*dhmult[getWorld().currentDiff()]));
    }
    public void walk(double ang, double multiplier){
        super.walk(ang, multiplier*dsmult[getWorld().currentDiff()]);
    }
    public String getName(){
        return "Zombie";
    }
}
