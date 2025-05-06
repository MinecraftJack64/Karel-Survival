package Game;
import greenfoot.*;
import java.util.ArrayList;

/**
 * Write a description of class StatusBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StatusBar extends Overlay
{
    private Color bgColor = Color.BLACK, barColor = Color.GREEN;
    private int size = 0;
    private int value, max;
    private int width, height;
    private int stringLength;
    protected ArrayList<Integer> phases = new ArrayList<Integer>();

    public StatusBar()
    {
        this(100, 100, 40, 5);
    }
    
    public StatusBar(int value, int max, int size, int height)
    {
        this.max = max;
        this.value = value;
        this.width = size;
        this.height = height;
        setImage(new GreenfootImage(width, height));
        updateImage();
    }
    public StatusBar(int value, int max, int size, int height, Color c)
    {
        this(value, max, size, height);
        barColor = c;
        updateImage();
    }
    
    public void act() {
        if(needsupdate){
            updateImage();
        }
        needsupdate = false;
    }
    private boolean needsupdate = false;
    public void update(){
        needsupdate = true;
    }
    public void setValue(int val){
        value = val;
        update();
    }
    public void setMax(int val){
        max = val;
        update();
    }
    public void setColor(Color c){
        barColor = c;
        update();
    }
    public Color getColor(){
        return barColor;
    }
    public int getValue()
    {
        return value;
    }
    public int getMax(){
        return max;
    }
    public double getPerc(){
        return value*1.0/max;
    }
    public boolean isFull(){
        return value>=max;
    }
    /**
     * Make the image
     */
    protected void updateImage()
    {
        GreenfootImage image = getImage();
        image.clear();
        image.setColor(bgColor);
        image.fillRect(0, 0, width, height);
        image.setColor(barColor);
        image.fillRect(0, 0, width*value/max, height);
        image.setColor(bgColor.brighter());
        for(int i: phases){
            image.fillRect(width*i/max-1, 0, 2, height);
        }
    }
    public double getBottom(){
        return size+getRealY();
    }
    public void divideIntoPhases(int numphases){
        int interval = getMax()/numphases;
        for(int i = 1; i < numphases; i++){
            phases.add(i*interval);
        }
        update();
    }
    public void clearPhases(){
        phases.clear();
        update();
    }
}
