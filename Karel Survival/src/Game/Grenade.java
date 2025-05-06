package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Grenade extends FlyingRock
{
    private int life = 45;
    public Grenade(double rotation, double targetdistance, double height, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        setRange(100);
        setDamage(250);
        setCheckHitMode(2);
        setDieOnHit(false);
    }
    public double getGravity(){
        return 2;
    }
    public void kAct(){
        super.kAct();
        life--;
        if(life<=0){
            die();
        }
    }
    public void die(){
        for(int i = 0; i <= 360; i+=30){
            GrenadeFragment wb = new GrenadeFragment(i+getDirection(), this);
            addObjectHere(wb);
        }
        addObjectHere(new Explosion(getRange()/60));
        super.die();
    }
}
