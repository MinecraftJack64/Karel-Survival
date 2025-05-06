package Game;
/**
 * Write a description of class Arc here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Arc  
{
    private double v, r, a, t;
    public Arc(double distance, double height, double gravity){
        a = gravity;//positive
        v = Math.sqrt(2*a*height);
        t = 2*v/a;
        r = distance/t;
    }
    public double getHeight(double f){
        return -0.5*a*Math.pow(f, 2)+v*f;
    }
    public double getDistance(double f){
        return f*r;
    }
    public double getRate(){
        return r;
    }
    public double getDuration(){
        return t;
    }
    public boolean isDone(double f){
        return f==t;
    }
    public double percentDone(double f){
        return t>0?f/t:1;
    }
}
