/**
 * Write a description of class Collectible here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Collectible extends GridObject
{
    private double range = 40;//how far away this collectible has to be to move towards collector
    private double speed = 9;//how fast this collectible will move towards its collector
    public void setRange(double r){
        range = r;
    }
    public double getRange(){
        return range;
    }
    public void setSpeed(double r){
        speed = r;
    }
    public double getSpeed(){
        return speed;
    }
    public GridObject getTarget(){
        return getWorld().getPlayer();
    }
    public boolean isActive(){
        return true;
    }
    public void kAct()
    {
        //if(!getWorld().gameStatus().equals("running"))return;
        double targang = face(getTarget(), false);
        double monangle = targang;
        //setRotation(monangle);
        if(distanceTo(getTarget())<getRange()&&isActive())move(monangle, getSpeed());
        if(distanceTo(getTarget())<getSpeed()/2+1&&isActive())collect(getTarget());
    }
    public void collect(GridObject t){
        die();
    }
    public void die(){
        getWorld().removeObject(this);
    }
}
