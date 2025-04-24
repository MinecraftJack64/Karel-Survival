import greenfoot.*;

/**
 * Write a description of class WaveBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WaveBar extends LifeBar
{
    GridEntity myGE;
    public WaveBar(int max, int size, int height)
    {
        super(max, size, height);
    }
    public WaveBar(int max, int size, int height, GridEntity of)
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
    public boolean isInGridWorld(){
        return false;
    }
}
