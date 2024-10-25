import greenfoot.*;

/**
 * Write a description of class FakePlayer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FakePlayer extends GridObject
{
    private GreenfootImage rocket = new GreenfootImage("kareln.png");
    public FakePlayer(){
        this.rocket.scale(90, 90);
        this.setImage(this.rocket);
    }
    
}
