package com.karel.game.particles;
import java.util.ArrayList;

import com.karel.game.FatalPoisonEffect;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Hitter;

/**
 * A proton wave that expands and destroys things in its path.
 * 
 * @author Michael Kolling
 * @version 0.1
 */
public class ProtonWave extends Hitter
{
    /** The damage this wave will deal */
    
    /** How many images should be used in the animation of the wave */
    private static final int NUMBER_IMAGES= 60;
    
    /** 
     * The images of the wave. This is static so the images are not
     * recreated for every object (improves performance significantly).
     */
    private static GreenfootImage[] images;
    private static final int damage = 600;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int life = 60;
    private int frame = 0;
    private boolean radiate;
    
    /**
     * Create a new proton wave.
     */
    static 
    {
        initializeImages();
    }
    
    /** 
     * Create the images for expanding the wave.
     */
    public static void initializeImages() 
    {
        if(images == null) 
        {
            GreenfootImage baseImage = new GreenfootImage("wave.png");
            images = new GreenfootImage[NUMBER_IMAGES];
            int i = 0;
            while (i < NUMBER_IMAGES) 
            {
                int size = (int)((i+1) * ( baseImage.getWidth() / NUMBER_IMAGES )*1.3);
                images[i] = new GreenfootImage(baseImage);
                images[i].scale(size, size);
                i++;
            }
        }
    }
    
    public ProtonWave(GridObject source, boolean radiate)
    {
        super(source);
        //addForce(new Vector(rotation, 15));
        frame = 0;
        setImage(images[frame]);
        setDamage(damage);
        setNumTargets(-1);
        setMultiHit(false);
        setAggression(true);
        this.radiate = radiate;
    }
    
    /**
     * The bullet will damage asteroids if it hits them.
     */
    public void applyPhysics()
    {
        if(life <= 0) {
            getWorld().removeObject(this);
            die();
        } 
        else {
            life--;
            checkHit();
            setDamage(getDamage()-10);
            setImage(images[frame]);
            frame++;
        }
    }
    public void kAct(){
        super.kAct();
        applyPhysics();
    }
    
    public void doHit(GridEntity g){
        super.doHit(g);
        if(radiate){
            g.applyEffect(new FatalPoisonEffect(5, 3, this));
        }
    }
    public boolean covertDamage(){
        return true;
    }
}
