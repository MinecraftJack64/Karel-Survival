package com.karel.game.weapons.lanternlobber;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Dragon extends Bullet
{
    DragonFood bait;
    public Dragon(double rotation, DragonFood bait, GridObject source)
    {
        super(rotation, source);
        setSpeed(10);
        setNumTargets(-1);
        this.bait = bait;
    }
    public void applyPhysics(){
        setLife(2);
        setRotation(getDirection());
        System.out.println(face(bait, false)+" "+getRotation());
        if(getFacingOffset(bait)<0){
            setDirection(getDirection()-10);
        }else{
            setDirection(getDirection()+10);
        }
        super.applyPhysics();
    }
}