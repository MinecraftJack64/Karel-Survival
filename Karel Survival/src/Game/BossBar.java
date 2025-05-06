package Game;
import greenfoot.*;

/**
 * Write a description of class BossBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BossBar extends LifeBar
{
    GridEntity myGE;
    public BossBar(int max, int size, int height)
    {
        super(max, size, height);
    }
    public BossBar(int max, int size, int height, GridEntity of)
    {
        super(max, size, height);
        myGE = of;
    }
    public void act(){
        if(myGE!=null&&myGE.isDead())getWorld().gameUI().removeBossBar();
        else{
            super.act();
        }
    }
    public String getLabel(){
        return myGE.getName();
    }
    public boolean isInGridWorld(){
        return false;
    }
}
