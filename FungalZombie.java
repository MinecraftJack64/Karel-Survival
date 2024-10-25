import greenfoot.*;
import java.util.List;

/**
 * Write a description of class FungalZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FungalZombie extends Zombie
{
    private static final int gunReloadTime = 120;         // The minimum delay between firing the gun.
    private static final int passiveReloadTime = 4; 
    private int reloadDelayCount;               // How long ago we fired the gun the last time.
    private int passivereloadDelayCount;
    private GreenfootImage rocket = new GreenfootImage("sporezareln.png");    
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private int ammo = 0;
    //private int damage = 400;
    /**
     * Initilise this rocket.
     */
    public FungalZombie()
    {
        reloadDelayCount = gunReloadTime;
        passivereloadDelayCount = passiveReloadTime;
        rocket.scale(45, 45);
        setImage(rocket);
        setRotation(180);
        setSpeed(0.5);
        startHealth(1700);
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        reloadDelayCount++;
        passivereloadDelayCount++;
        double monangle = face(getTarget(), canMove());
        //setRotation(getRotation()-1);
        ammo++;
        if(reloadDelayCount>=gunReloadTime&&canAttack()){
            attack();
            reloadDelayCount = 0;
        }
        if(passivereloadDelayCount>=passiveReloadTime){
            passiveattack();
            passivereloadDelayCount = 0;
        }
        if(distanceTo(getTarget())>200)
            walk(monangle, 1);
    }
    //ovveride this
    public int getXP(){
        return 750;
    }

    public void attack(){
        //explode if not stunned
        explodeOn(225, (g)->{
            if(isAlliedWith(g)){//
                //g.applyeffect(new DamagePercentageEffect(1.5, 120));
                g.applyeffect(new SpeedPercentageEffect(1.5, 120));
            }else if(isAggroTowards(g)){
                g.applyeffect(new PoisonEffect((int)(75+(225-distanceTo(g))), 30, 4, this));
                g.hit(50, this);
            }
        }, null);//replace with better explosion later
        Sounds.play("sporespray");
    }
    public void passiveattack(){
        explodeOn(250, "enemy", (g)->{g.applyeffect(new SpeedPercentageEffect(distanceTo(g)/400+0.3, 4));}, null);
    }
    public String getName(){
        return "Fungal Zombie";
    }
}
