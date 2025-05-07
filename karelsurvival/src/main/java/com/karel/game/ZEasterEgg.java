package com.karel.game;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ZEasterEgg extends ZBullet
{
    /** The damage this bullet will deal */
    private static final int damage = 200;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int life = 25;
    
    public ZEasterEgg(double rotation, GridObject source)
    {
        super(rotation, source);
        setLife(25);
    }
    
    /**
     * The bullet will damage asteroids if it hits them.
     */
    
    public void doHit(GridEntity targ){
        Sounds.play("eggcrack");
        if(Greenfoot.getRandomNumber(3)==0){
            ZombieChick spawn = new ZombieChick(this, (GridEntity)getSource());
            getWorld().addObject(spawn, getRealX(), getRealY());
        }
        super.doHit(targ);
    }
}
