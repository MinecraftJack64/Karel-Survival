package com.karel.game;
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
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.meatshield};
    private int reloadDelayCount;               // How long ago we fired the gun the last time.

    public String getStaticTextureURL(){return "zareln.png";}
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int ammo = 0;
    private static double speed = 2;
    private GridEntity target;
    private static double[] dhmult = new double[]{0.5, 0.75, 1, 1.5, 2};//difficulty health multiplier
    private static double[] dsmult = new double[]{0.8, 0.8, 1, 1.25, 1.3};//difficulty speed multiplier
    /**
     * Initilise this rocket.
     */
    public Zombie()
    {
        reloadDelayCount = 5;
        scaleTexture(45, 45);
        setSpeed(speed);
        setTeam("zombie");
        startHealth(200);
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void update()//TODO: fix logic
    {
        processSounds();
        if(getWorld().gameStatus().equals("lose")){
            applyEffects();
            applyPhysics();
            feast();
            return;
        }
        super.update();
        clearTarget();
    }
    public void processSounds(){
        //when sound cooldown 0, groan and set cooldown to new random value
    }
    public void clearTarget(){
        target = null;
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
        if(distanceTo(getTarget())>defaultRange())walk(monangle, 1);
        else{
            if(ammo>defaultReloadTime()&&canAttack()){
                attack();
                ammo = 0;
            }
        }
    }
    public void attack(){
        damage(getTarget(), defaultDamage());
    }
    public int defaultDamage(){
        return 10;
    }
    public int defaultReloadTime(){
        return 5;
    }
    public int defaultRange(){
        return 30;
    }
    public void feast() 
    {
        reloadDelayCount++;
        double monangle = face(getTarget(), canMove());
        //setRotation(getRotation()-1);
        if(distanceTo(getTarget())>defaultRange())walk(monangle, 1);
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
            Game.increaseScore(getXP());
        }
        //if(wasInOriginalWave())getWorld().getGame().getSpawner().wavehealth--;
        super.die(killer);
    }
    public boolean prioritizeTarget(){
        return false;
    }
    // cache target for the current update cycle
    public boolean useCache(){
        return true;
    }
    public GridEntity getTarget(){
        if(target!=null&&useCache()){//cache target
            return target;
        }
        if(prioritizeTarget()&&getWorld().getPlayer().canDetect()&&isAggroTowards(getWorld().getPlayer())){
            return getWorld().getPlayer();
        }
        GridEntity candidate = getNearestTarget();
        if(candidate == null){
            candidate = getWorld().getPlayer();
        }
        target = candidate;
        return candidate;//use this for now
    }
    /*public GridEntity getNearestTarget(){
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
    }*/
    public void explodeFleshConfetti(int amt){
        for(int i = 0; i < Greenfoot.getRandomNumber(amt)+amt; i++){
            ZFleshConfetti fc = new ZFleshConfetti(Greenfoot.getRandomNumber(360), this);
            getWorld().addObject(fc, getRealX(), getRealY());
        }
    }
    public void startHealth(int amt){
        super.startHealth((int)(amt*dhmult[Game.currentDiff()]));
    }
    public void walk(double ang, double multiplier){
        super.walk(ang, multiplier*dsmult[Game.currentDiff()]);
    }
    public void setImage(String path){
        super.setImage("GridEntities/Zombies/"+path);
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    public String getName(){
        return "Zombie";
    }
}
