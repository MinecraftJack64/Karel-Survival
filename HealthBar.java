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
    public HealthBar(int max, int size, int height, GridEntity of)
    {
        super(max, max, size, height);
        myGE = of;
    }
    public void setValue(int val){
        if(val<0)super.setValue(0);
        else super.setValue(val);
        while(phases.size()>0&&getValue()<phases.get(phases.size()-1)){
            phases.remove(phases.size()-1);
            update();
        }
        double perc = getPerc();
        if(perc>1){
            setColor(Color.BLUE);
        }else
            setColor(new Color(perc>=0.5?(int)(255*(1-perc)*2):255, perc<=0.5?(int)(255*(perc)*2):255, 0));
    }
    public void act(){
        super.act();
        if(myGE!=null){setRealLocation(myGE.getRealX(), myGE.getRealY()-40, myGE.getRealHeight());if(myGE.isDead())getWorld().removeObject(this);}
    }
}