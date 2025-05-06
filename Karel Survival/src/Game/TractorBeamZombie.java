package Game;
 
import greenfoot.*;
import java.util.List;

/**
 * Write a description of class TractorBeamZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TractorBeamZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.support, ZombieClass.controller};
    private GreenfootImage rocket = new GreenfootImage("gunzareln.png");    
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int ammo = 0;
    private static double attackrange = 700;
    /**
     * Initilise this rocket.
     */
    public TractorBeamZombie()
    {
        rocket.scale(60, 60);
        setImage(rocket);
        setRotation(180);
        setSpeed(1);
        startHealth(300);
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        double monangle = face(getTarget(), canMove());
        //setRotation(getRotation()-1);
        ammo++;
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else{
            fire();
        }
    }
    /**
     * Check whether there are any key pressed and react to them.
     */
    private void fire() 
    {
        if (canAttack()){
            explodeOn(800, "enemy", (g)->{
                if(Math.abs(face(g, false)-getRotation())<40){
                    g.pullTowards(this, 1);
                    if(distanceTo(g)<200)damage(g, 2);
                }
            }, null);
            Sounds.play("gunshoot");
        }
    }
    //ovveride this
    public int getXP(){
        return 1000;
    }
    @Override
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    public String getName(){
        return "Tractor Beam Zombie";
    }
}
