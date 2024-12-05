/**
 * Write a description of class Puddle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Puddle extends GridObject
{
    private double range = 40;//how large the puddle is
    private int interval = 9;//how often this puddle does its thing
    private int effect = 0;//how much damage or healing this does
    public void setRange(double r){
        range = r;
    }
    public double getRange(){
        return range;
    }
    public void setInterval(int r){
        interval = r;
    }
    public int getInterval(){
        return interval;
    }
    public GridObject getTarget(){
        return getWorld().getPlayer();
    }
    public void kAct()
    {
        //
    }
    public void die(){
        getWorld().removeObject(this);
    }
}
