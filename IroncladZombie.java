import greenfoot.*;
import java.util.List;

/**
 * Write a description of class IroncladZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class IroncladZombie extends Zombie
{
    private static final int gunReloadTime = 160;         // The minimum delay between firing the gun.

    private int reloadDelayCount;               // How long ago we fired the gun the last time.

    private GreenfootImage rocket = new GreenfootImage("gunzareln.png");    
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int ammo = 0;
    private static double attackrange = 900;
    /**
     * Initilise this rocket.
     */
    public IroncladZombie()
    {
        reloadDelayCount = 5;
        rocket.scale(60, 60);
        setImage(rocket);
        setRotation(180);
        setSpeed(1);
        startHealth(4500);
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
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else{
            walk(monangle, 0.4);
            fire();
        }
    }
    public boolean canBePulled(){
        return false;
    }
    public void die(GridObject source){
        try{
            //explode if not stunned
            addObjectHere(new RocketZombie(50, 0));
            super.die(source);
            explodeOn(100, 500);
            Sounds.play("explode");
        }catch(IllegalStateException e){
            //
        }
    }
    /**
     * Check whether there are any key pressed and react to them.
     */
    private void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()){
            ZBigBullet bullet = new ZBigBullet (getRealRotation(), this);
            getWorld().addObject (bullet, getRealX(), getRealY());
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
        }
    }
    //ovveride this
    public int getXP(){
        return 1000;
    }
    
    public String getName(){
        return "Ironclad Zombie";
    }
}
