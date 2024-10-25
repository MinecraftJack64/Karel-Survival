import greenfoot.*;
import java.util.List;

/**
 * Write a description of class HardHatZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HardHatZombie extends Zombie
{
    private static final int gunReloadTime = 200;         // The minimum delay between firing the gun.
    private int reloadDelayCount;               // How long ago we fired the gun the last time.
    private GreenfootImage rocket = new GreenfootImage("constructionzareln.png");    
    private GreenfootImage rocket2 = new GreenfootImage("hurtconstructionzareln.png");
    private int ammo = 0;
    //private int damage = 400;
    private boolean inShieldPhase = true;
    /**
     * Initilise this rocket.
     */
    public HardHatZombie()
    {
        reloadDelayCount = gunReloadTime;
        rocket.scale(45, 45);
        rocket2.scale(45, 45);
        setImage(rocket);
        setRotation(180);
        setSpeed(1);
        startHealth(750);
        applyShield(new ArmorShield(this, 750));
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        if(inShieldPhase&&!hasShield()){
            inShieldPhase = false;
            setImage(rocket2);
        }
        reloadDelayCount++;
        double monangle = face(getTarget(), canMove());
        //setRotation(getRotation()-1);
        ammo++;
        if(reloadDelayCount>=gunReloadTime&&canAttack()){
            attack();
            reloadDelayCount = 0;
        }
        if(distanceTo(getTarget())>200)
            walk(monangle, 1);
    }
    //ovveride this
    public int getXP(){
        return 550;
    }

    public void attack(){
        //explode if not stunned
        explodeOn(225, (g)->{
            if(isAlliedWith(g)){//
                int health = Math.min(300, Math.min(g.getMaxHealth(), g.getHealth()*2));//shield is 300 health by default unless zombie has less max health or health
                g.applyShield(new ArmorShield(g, health));
            }else if(isAggroTowards(g)){
                g.hit(200, this);
            }
        });
        Sounds.play("hardhatattack");
    }
    
    public String getName(){
        return "Hard Hat Zombie";
    }
}
