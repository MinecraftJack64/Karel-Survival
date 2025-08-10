package com.karel.game.gridobjects.gridentities.zombies.boid;

import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import java.util.List;
import com.karel.game.ZombieClass;
/**
 * Write a description of class BoidZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BoidZombie extends Zombie
{
    private static final ZombieClass[] classes = new ZombieClass[]{ZombieClass.meatshield};
    private int babies = 0; // how many babies this boid can spawn
    private int ammo = 0;
    private int motherCooldown = 100; // how long until this boid becomes a mother
    private int kamikazeCooldown = Greenfoot.getRandomNumber(200)+200; // how long until this boid will attack the target
    private int nonMotherCooldown = 100; // how long until this boid becomes a non-mother if it no longer encounters other non-mother boids
    private int detectionRange = 300;
    private double cohesion = 0.05; //0 to 1, how much the boid will try to stay close to other boids
    private double separation = 0.8; //0 to 1, how much the boid will try to stay away from other boids
    private double alignment = 0.2; //0 to 1, how much the boid will try to align its direction with other boids
    private double speedX, speedY; //speed in the X and Y directions
    private double targetResolve = 0.9; //how much the boid will try to resolve its target's position
    private double resolve = 0.2; //how much the boid will reduce its own speed
    private boolean isMother = false; //if this is a mother boid, it will not interact with other non-mother boids
    public String getStaticTextureURL(){return "zareln.png";}
    /**
     * Initilise this rocket.
     */
    public BoidZombie(boolean isMother, int babies)
    {
        this.isMother = isMother;
        if(isMother)this.babies = babies;
        startHealth(isMother?200:50);
        setRotation(Greenfoot.getRandomNumber(360));
        setSpeed(3);
        speedX = Greenfoot.getRandomNumber(3) - 1.5;
        speedY = Greenfoot.getRandomNumber(3) - 1.5;
        if(isMother){
            setSpeed(3.5);
            detectionRange = 500;
            setImage("heraldzareln.png");
        }
    }
    public BoidZombie() {
        this(true);
    }
    public BoidZombie(boolean isMother){
        this(isMother, Greenfoot.getRandomNumber(5)+10);
    }
    public void behave(){
        if(babies>0&&canAttack()&&ammo>5){
            spawnBaby();
            ammo = 0;
        }
        if(ammo<=5){
            ammo++;
        }
        if(distanceTo(getTarget())<30){
            explodeOn(40, 75);
            die(this);
            return;
        }
        List<GridEntity> nearbyEntities = getGEsInRange(detectionRange);
        //nearbyEntities.clear();
        double sumX = 0, sumY = 0, sumSpeedX = 0, sumSpeedY = 0;
        int count = 0;
        boolean encounteredMother = false, encounteredNonMother = false;
        for (GridEntity entity : nearbyEntities) {
            if (entity instanceof BoidZombie boid&&isAlliedWith(boid)) {
                if(isMother&&!boid.isMother()){
                    encounteredNonMother = true;
                    continue; //don't interact with other non-mothers
                }
                double dX = boid.getX() - getX();
                double dY = boid.getY() - getY();
                sumX += dX;
                sumY += dY;
                sumSpeedX += boid.getSpeedX()-speedX;
                sumSpeedY += boid.getSpeedY()-speedY;
                //Non-mothers affected by mothers more
                if(!isMother&&boid.isMother()){
                    encounteredMother = true;
                    sumSpeedX += boid.getSpeedX()-speedX;
                    sumSpeedY += boid.getSpeedY()-speedY;
                }
                if(boid.isKamikaze()&&!isKamikaze()){
                    kamikazeCooldown = Greenfoot.getRandomNumber(200)+200; //if we encounter a kamikaze boid, we wait longer
                }
                if(!isKamikaze()){
                    speedX += dX/distanceTo(boid)*(0-separation);
                    speedY += dY/distanceTo(boid)*(0-separation);
                    count++;
                }
            }
        }
        if(count>0&&!isKamikaze()){
            speedX += sumX * cohesion / count;
            speedY += sumY * cohesion / count;
            speedX += sumSpeedX * alignment / count;
            speedY += sumSpeedY * alignment / count;
        }
        //trueTargetResolve transitions from positive to negative as the boid gets closer to the target from 150 distance to 100 distance
        if(isMother||encounteredMother){
            double trueTargetResolve = targetResolve*Math.max(-1, Math.min(1, (distanceTo(getTarget())-125)/25.0));
            if(!isMother){
                trueTargetResolve*=0.75; //non-mothers are less aggressive
                if(isKamikaze()){
                    trueTargetResolve = 1; //attack if kamikaze
                }
            }
            double targetX = getTarget().getX() - getX();
            double targetY = getTarget().getY() - getY();
            speedX += targetX * trueTargetResolve / distanceTo(getTarget());
            speedY += targetY * trueTargetResolve / distanceTo(getTarget());
        }
        if(!isMother){
            motherCooldown--;
            if(motherCooldown <= 0){
                kamikazeCooldown +=1; //don't want mother to kamikaze
                nonMotherCooldown = Greenfoot.getRandomNumber(100)+100;
                isMother = true;
                setSpeed(3.5);
                setImage("heraldzareln.png");
            }
        }else{
            if(encounteredNonMother){
                nonMotherCooldown = Greenfoot.getRandomNumber(10)+90; //reset cooldown if we encountered a non-mother
            }else{
                nonMotherCooldown--;
                if(nonMotherCooldown <= 0&&!isKamikaze()){
                    isMother = false;
                    motherCooldown = Greenfoot.getRandomNumber(100)+100;
                    setSpeed(3);
                    setImage("zareln.png");
                }
            }
        }
        if(kamikazeCooldown > 0&&!isMother){
            kamikazeCooldown--;
        }
        if(encounteredMother){
            motherCooldown = 100; //reset cooldown if we encountered a mother
        }
        //Move
        if (canMove()) {
            double angle = (Math.atan2(speedY, speedX)+90) * 180 / Math.PI;
            double d = Math.sqrt(speedX * speedX + speedY * speedY);
            setRotation(angle);
            walk(angle, d);
            speedX = resolve * (speedX*getSpeed()/d-speedX);
            speedY = resolve * (speedY*getSpeed()/d-speedY);
        }
    }
    public void spawnBaby() {
        if (babies <= 0) return;
        BoidZombie baby = new BoidZombie(Greenfoot.getRandomNumber(5) == 0, babies-Greenfoot.getRandomNumber(3));
        getWorld().addObject(baby, getX()+Greenfoot.getRandomNumber(50)-25, getY()+Greenfoot.getRandomNumber(50)-25);
        babies--;
    }
    public boolean isKamikaze() {
        return kamikazeCooldown <= 0;
    }
    public boolean isMother() {
        return isMother;
    }
    public double getSpeedX() {
        return speedX;
    }
    public double getSpeedY() {
        return speedY;
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 500;
    }
    public String getName(){
        return isMother()?"Mother Boid Zombie":"Boid Zombie";
    }
}
