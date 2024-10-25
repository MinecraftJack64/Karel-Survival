import greenfoot.*;

/**
 * Write a description of class ShieldBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ShieldBar extends StatusBar
{
    GridEntity myGE;
    public ShieldBar(int max, int size, int height)
    {
        super(max, max, size, height);
    }
    public ShieldBar(int max, int size, int height, GridEntity of)
    {
        super(max, max, size, height, Color.BLUE.brighter());
        myGE = of;
    }
    public void setValue(int val){
        if(val<=0)getWorld().removeObject(this);
        else super.setValue(val);
        setColor(Color.BLUE);
    }
    public void act(){
        super.act();
        if(myGE!=null){setRealLocation(myGE.getRealX(), myGE.getRealY()-50, myGE.getRealHeight());if(myGE.isDead())getWorld().removeObject(this);}
    }
}
