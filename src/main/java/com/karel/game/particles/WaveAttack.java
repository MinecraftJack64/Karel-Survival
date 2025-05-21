package com.karel.game.particles;
import com.karel.game.GridObject;
import com.karel.game.Hitter;

/**
 * A proton wave that expands and destroys things in its path.
 * 
 * @author Michael Kolling
 * @version 0.1
 */
public class WaveAttack extends Hitter
{
    private int life = 60;
    private int frame = 1;
    private double maxRadius = 120;
    
    public WaveAttack(GridObject source)
    {
        super(source);
        //addForce(new Vector(rotation, 15));
        frame = 0;
        setImage("wave.png");
        scaleTexture(0, 0);
        setNumTargets(-1);
        setMultiHit(false);
        setAggression(true);
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
            getWorld().removeObject(this);
            die();
        } 
        else {
            life--;
            double size = (frame/life) * maxRadius;
            scaleTexture((int)size, (int)size);
            setRange((int)size);//TODO separate
            checkHit();
            frame++;
        }
    }
    public void update(){
        applyPhysics();
    }
}
