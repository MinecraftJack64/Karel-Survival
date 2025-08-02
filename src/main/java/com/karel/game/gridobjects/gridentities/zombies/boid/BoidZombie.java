package com.karel.game.gridobjects.gridentities.zombies.boid;

import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import java.util.List;
/**
 * Write a description of class BoidZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BoidZombie extends Zombie
{
    private int detectionRange = 300;
    private double cohesion = 0.15; //0 to 1, how much the boid will try to stay close to other boids
    private double separation = 0.25; //0 to 1, how much the boid will try to stay away from other boids
    private double alignment = 0.05; //0 to 1, how much the boid will try to align its direction with other boids
    private double speedX, speedY; //speed in the X and Y directions
    private double targetResolve = 0.9; //how much the boid will try to resolve its target's position
    private double resolve = 0.2; //how much the boid will reduce its own speed
    public String getStaticTextureURL(){return "zareln.png";}
    /**
     * Initilise this rocket.
     */
    public BoidZombie()
    {
        startHealth(250);
        setRotation(Greenfoot.getRandomNumber(360));
        setSpeed(2);
        speedX = Greenfoot.getRandomNumber(3) - 1.5;
        speedY = Greenfoot.getRandomNumber(3) - 1.5;
    }
    public void behave(){
        if(distanceTo(getTarget())<30){
            explodeOn(40, 30);
            die(this);
            return;
        }
        List<GridEntity> nearbyEntities = getGEsInRange(detectionRange);
        double sumX = 0, sumY = 0, sumSpeedX = 0, sumSpeedY = 0;
        int count = 0;
        for (GridEntity entity : nearbyEntities) {
            if (entity instanceof BoidZombie boid) {
                double dX = boid.getX() - getX();
                double dY = boid.getY() - getY();
                sumX += dX;
                sumY += dY;
                sumSpeedX += boid.getSpeedX()-speedX;
                sumSpeedY += boid.getSpeedY()-speedY;
                speedX += dX/distanceTo(boid)*(0-separation);
                speedY += dY/distanceTo(boid)*(0-separation);
                count++;
            }
        }
        if(count>0){
            speedX += sumX * cohesion / count;
            speedY += sumY * cohesion / count;
            speedX += sumSpeedX * alignment / count;
            speedY += sumSpeedY * alignment / count;
        }
        if(distanceTo(getTarget())<detectionRange){
            double targetX = getTarget().getX() - getX();
            double targetY = getTarget().getY() - getY();
            speedX += targetX * targetResolve / distanceTo(getTarget());
            speedY += targetY * targetResolve / distanceTo(getTarget());
        }
        //Move
        if (canMove()) {
            double angle = Math.atan2(speedY, speedX);
            double d = Math.sqrt(speedX * speedX + speedY * speedY);
            face(speedX, speedY, true);
            walk(angle, d);
            speedX = resolve * (speedX*getSpeed()/d-speedX);
            speedY = resolve * (speedY*getSpeed()/d-speedY);
        }
    }
    public double getSpeedX() {
        return speedX;
    }
    public double getSpeedY() {
        return speedY;
    }
    //ovveride this
    public int getXP(){
        return 500;
    }
    public String getName(){
        return "Boid Zombie";
    }
}
