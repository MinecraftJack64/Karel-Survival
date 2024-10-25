import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)


import java.awt.Graphics;

/**
 * Counter that displays a text and number.
 * 
 * @author Michael Kolling
 * @version 1.0.1
 */
public class Counter extends TextDisplay
{
    
    private int value = 0;
    private int target = 0;
    private String prefix;
    //private TextDisplay renderer;

    public Counter()
    {
        this("", 20);
    }

    public Counter(String prefix, int size)
    {
        super(prefix, size);
        this.prefix = prefix;
        setText(prefix+value);
    }
    
    public void act() {
        if(Math.abs(value-target)<5){
            value = target;
            setText(prefix+value);
        }
        if(value < target) {
            value+=5;
            setText(prefix+value);
        }
        else if(value > target) {
            value-=5;
            setText(prefix+value);
        }
        super.act();
    }
    public void setValue(int value){
        this.value = target = value;
    }
    public void setTarget(int score)
    {
        target = score;
    }

    public int getValue()
    {
        return value;
    }
}
