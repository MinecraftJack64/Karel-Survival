package com.karel.game.particles;
import com.karel.game.GridObject;

/**
 * A proton wave that expands and destroys things in its path.
 * 
 * @author Michael Kolling
 * @version 0.1
 */
public class Wave extends GridObject
{
    private int life = 60;
    private int frame = 1;
    private double maxRadius = 250;
    
    public Wave()
    {
        //addForce(new Vector(rotation, 15));
        frame = 0;
        setImage("wave.png");
        scaleTexture(1, 1);
    }

    public void setLife(int life)
    {
        this.life = life;
    }
    public int getLife()
    {
        return life;
    }
    public void setMaxRadius(double maxRadius)
    {
        this.maxRadius = maxRadius;
    }
    public double getMaxRadius(){
        return maxRadius;
    }
    
    /**
     * The bullet will damage asteroids if it hits them.
     */
    public void applyPhysics()
    {
        if(frame>life) {
            die();
        } 
        else {
            double size = (frame*1.0/life) * maxRadius;
            if(size>0)scaleTexture((int)size, (int)size);
            frame++;
        }
    }
    public void die(){
        getWorld().removeObject(this);
        super.die();
    }
    public void update(){
        applyPhysics();
    }
}
