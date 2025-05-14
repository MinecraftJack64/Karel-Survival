package com.karel.game.ui.bars;
import java.util.ArrayList;

import com.karel.game.ui.Overlay;
import com.raylib.Color;
import static com.raylib.Raylib.*;

/**
 * Write a description of class StatusBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StatusBar extends Overlay
{
    private Color bgColor = BLACK, barColor = GREEN;
    private int size = 0;
    private int value, max;
    private int width, height;
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
    }
    public StatusBar(int value, int max, int size, int height, Color c)
    {
        this(value, max, size, height);
        barColor = c;
    }
    public void setValue(int val){
        value = val;
    }
    public void setMax(int val){
        max = val;
    }
    public void setColor(Color c){
        barColor = c;
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
    /*
    protected void updateImage()
    {
        GreenfootImage image = getImage();
        image.clear();
        image.setColor(bgColor);
        image.fillRect(0, 0, width, height);
        image.setColor(barColor);
        image.fillRect(0, 0, width*value/max, height);
        image.setColor(bgColor);
        for(int i: phases){
            image.fillRect(width*i/max-1, 0, 2, height);
        }
    }
    TODO*/
    public void render(){
        //TODO
        /*drawRectangle(getRealX(), getRealY(), size, height, bgColor);
        drawRectangle(getRealX(), getRealY(), size*getPerc(), height, barColor);
        for(int i: phases){
            drawRectangle(getRealX()+size*i/max-1, getRealY(), 2, height, bgColor);
        }*/
    }
    public double getBottom(){
        return size+getRealY();
    }
    public void divideIntoPhases(int numphases){
        int interval = getMax()/numphases;
        for(int i = 1; i < numphases; i++){
            phases.add(i*interval);
        }
    }
    public void clearPhases(){
        phases.clear();
    }
}
