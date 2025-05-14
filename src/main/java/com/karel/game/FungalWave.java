package com.karel.game;
import java.util.ArrayList;

/**
 * A proton wave that expands and destroys things in its path.
 * 
 * @author Michael Kolling
 * @version 0.1
 */
public class FungalWave extends Hitter
{
    /** The damage this wave will deal */
    
    /** How many images should be used in the animation of the wave */
    private static final int NUMBER_IMAGES= 30;
    
    /** 
     * The images of the wave. This is static so the images are not
     * recreated for every object (improves performance significantly).
     */
    private static GreenfootImage[] images;
    private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int life = 30;
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
                int size = (int)((i+1) * ( baseImage.getWidth()*0.7 / NUMBER_IMAGES )*1.3);
                images[i] = new GreenfootImage(baseImage);
                images[i].scale(size, size);
                i++;
            }
        }
    }
    
    public FungalWave(FungalZombie source)
    {
        super(source);
        //addForce(new Vector(rotation, 15));
        frame = 0;
        setImage(images[frame]);
        setDamage(damage);
        setNumTargets(-1);
        setMultiHit(false);
        setAggression(true);
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
        ((FungalZombie)getSource()).notifyHit(g);
    }
}
