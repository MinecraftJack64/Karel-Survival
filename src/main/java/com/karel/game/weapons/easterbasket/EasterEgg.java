package com.karel.game.weapons.easterbasket;

import com.karel.game.Bullet;
import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Sounds;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class EasterEgg extends Bullet
{
    private boolean attemptedspawn = false, willspawn;
    
    public EasterEgg(double rotation, boolean chance, GridObject source)
    {
        super(rotation, source);
        if(chance){
            setImage("Weapons/easterbasket/proj.png");
            scaleTexture(30);
        }else{
            setImage("Weapons/easterbasket/proj"+Greenfoot.getRandomNumber(4)+".png");
            scaleTexture(25);
        }
        setRotation(getRotation()-90);
        setLife(25);
        setSpeed(13);
        setDamage(100);
        willspawn = chance;
    }
    
    /**
     * The bullet will damage asteroids if it hits them.
     */
    
    public void doHit(GridEntity targ){
        Sounds.play("eggcrack");
        if(willspawn){
            Chick spawn = new Chick(1, (GridEntity)getSource());
            getWorld().addObject(spawn, getX(), getY());
        }
        attemptedspawn = true;
        super.doHit(targ);
    }
    public void die(){
        if(willspawn&&Greenfoot.getRandomNumber(2)==0&&!attemptedspawn){
            Chick spawn = new Chick(0.5, (GridEntity)getSource());
            getWorld().addObject(spawn, getX(), getY());
        }
        super.die();
    }
}
