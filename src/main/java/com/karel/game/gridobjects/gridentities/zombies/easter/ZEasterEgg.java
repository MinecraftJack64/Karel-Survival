package com.karel.game.gridobjects.gridentities.zombies.easter;

import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Sounds;
import com.karel.game.gridobjects.gridentities.zombies.ZBullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZEasterEgg extends ZBullet
{
    private boolean willspawn;
    
    public ZEasterEgg(double rotation, GridObject source)
    {
        super(rotation, source);
        willspawn = Greenfoot.getRandomNumber(3)==0;
        if(willspawn){
            setImage("Weapons/easterbasket/proj.png");
            scaleTexture(30);
        }else{
            setImage("Weapons/easterbasket/proj"+Greenfoot.getRandomNumber(4)+".png");
            scaleTexture(25);
        }
        setRotation(getRotation()-90);
        setLife(25);
    }
    
    /**
     * The bullet will damage asteroids if it hits them.
     */
    
    public void doHit(GridEntity targ){
        Sounds.play("eggcrack");
        if(willspawn){
            ZombieChick spawn = new ZombieChick(this, (GridEntity)getSource());
            getWorld().addObject(spawn, getX(), getY());
        }
        super.doHit(targ);
    }
}
