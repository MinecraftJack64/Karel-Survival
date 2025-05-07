package com.karel.game;

/**
 * An explosion. It starts by expanding and then collapsing. 
 * The explosion will explode other obejcts that the explosion intersects.
 * 
 * @author Poul Henriksen
 * @version 1.0.1
 */
public class Explosion extends GridObject
{
    /** How many images should be used in the animation of the explostion */
    private final static int IMAGE_COUNT= 12;
    
    /** 
     * The images in the explosion. This is static so the images are not
     * recreated for every object (improves performance significantly).
     */
    private GreenfootImage[] images;
    
    /** Current size of the explosion */
    private int imageNo = 0;
    private double size = 0;
    
    /** How much do we increment the index in the explosion animation. */
    private int increment=1;
    
    /**
     * Create a new explosion.
     */
    public Explosion(double size) 
    {
        this.size = size;
        initializeImages();
        setImage(images[0]);
        //Greenfoot.playSound("MetalExplosion.wav");
    }
    public Explosion(){
        this(10);
    }
    
    /** 
     * Create the images for explosion.
     */
    public void initializeImages() 
    {
        if(images == null) {
            GreenfootImage baseImage = new GreenfootImage("explosion-big.png");
            images = new GreenfootImage[IMAGE_COUNT];
            for (int i = 0; i < IMAGE_COUNT; i++)
            {
                int size = (int)(this.size*i+1) * ( baseImage.getWidth()/ IMAGE_COUNT );
                images[i] = new GreenfootImage(baseImage);
                images[i].scale(size, size);
            }
        }
    }
    
    /**
     * Explode!
     */
    public void kAct()
    { 
        setImage(images[imageNo]);

        imageNo += increment;
        if(imageNo >= IMAGE_COUNT) {
            increment = -increment;
            imageNo += increment;
        }
        
        if(imageNo < 0) {
            getWorld().removeObject(this);
        }
    }
}