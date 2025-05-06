package Game;
import greenfoot.*;
import java.util.ArrayList;

/**
 * Write a description of class HealthBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HealthBar extends StatusBar
{
    GridEntity myGE;
    public HealthBar(int max, int size, int height)
    {
        super(max, max, size, height);
    }
    public HealthBar(int max, int size, int height, Color c)
    {
        super(max, max, size, height, c);
    }
    public HealthBar(int max, int size, int height, GridEntity of)
    {
        super(max, max, size, height);
        myGE = of;
    }
    public HealthBar(int max, int size, int height, Color c, GridEntity of)
    {
        super(max, max, size, height, c);
        myGE = of;
    }
    public void setValue(int val){
        if(val<0)super.setValue(0);
        else super.setValue(val);
    }
    public void act(){
        super.act();
        if(myGE!=null){
            if(myGE.isDead())getWorld().removeObject(this);
        }
    }
    public boolean isInGridWorld(){
        return true;
    }
}
