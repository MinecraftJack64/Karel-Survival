import greenfoot.*;
import java.util.List;

/**
 * Write a description of class ExplodingZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ExplodingZombie extends Zombie
{
    private static final int gunReloadTime = 5;         // The minimum delay between firing the gun.

    private int reloadDelayCount;               // How long ago we fired the gun the last time.

    private GreenfootImage rocket = new GreenfootImage("tntzareln.png");    
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int ammo = 0;
    private int damage = 400;
    /**
     * Initilise this rocket.
     */
    public ExplodingZombie()
    {
        reloadDelayCount = 5;
        rocket.scale(45, 45);
        setImage(rocket);
        setRotation(180);
        setSpeed(4);
        startHealth(100);
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        reloadDelayCount++;
        double monangle = face(getTarget(), canMove());
        //setRotation(getRotation()-1);
        ammo++;
        if(distanceTo(getTarget())>55)walk(monangle, 1);
        else{
            if(canAttack())hit(damage, this);
        }
    }

    
    //ovveride this
    public int getXP(){
        return 200;
    }

    public void die(GridObject source){
        try{
            //explode if not stunned
            List<GridEntity> l = getObjectsInRange(60, GridEntity.class);
            addObjectHere(new Explosion(1));
            super.die(source);
            for(GridEntity g:l){
                damage(g, damage);
            }
            Sounds.play("explode");
        }catch(IllegalStateException e){
            //
        }
    }
    public String getName(){
        return "Exploding Zombie";
    }
}
