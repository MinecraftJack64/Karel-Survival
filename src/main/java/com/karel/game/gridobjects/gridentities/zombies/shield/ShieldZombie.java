package com.karel.game.gridobjects.gridentities.zombies.shield;

import com.karel.game.ArmorShield;
import com.karel.game.Greenfoot;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.weapons.ShieldID;
import com.raylib.Texture;
/**
 * A zombie with a shield
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ShieldZombie extends Zombie
{
    public String getStaticTextureURL(){return "shieldzareln.png";}
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.meatshield};
    private Texture rocket = Greenfoot.loadTexture(spriteOrigin()+"shieldzareln.png");
    private Texture rocket2 = Greenfoot.loadTexture(spriteOrigin()+"zareln.png");
    private static double speed = 2;
    //ShieldBar shieldBar;
    private boolean inShieldPhase = true;
    private ShieldID shieldid = new ShieldID(this);
    /**
     * Initilise this rocket.
     */
    public ShieldZombie()
    {
        setImage(rocket);
        setSpeed(speed);
        startHealth(200);
        applyShield(new ArmorShield(shieldid, 300));
    }
    
    @Override
    public int getXP(){
        return 150;
    }
    public void behave(){
        if(inShieldPhase&&!hasShield(shieldid)){
            inShieldPhase = false;
            setImage(rocket2);
        }
        super.behave();
    }

    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    
    public String getName(){
        return "Shield Zombie";
    }
}
