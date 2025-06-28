package com.karel.game.ui.bars;
import java.util.ArrayList;

import com.karel.game.ui.Overlay;
import com.raylib.Color;
import com.raylib.Raylib;
import com.raylib.Rectangle;
import com.raylib.Vector2;

/**
 * Write a description of class StatusBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StatusBar extends Overlay
{
    private Color bgColor = Raylib.BLACK, barColor = Raylib.GREEN;
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
        /*drawRectangle(getX(), getY(), size, height, bgColor);
        drawRectangle(getX(), getY(), size*getPerc(), height, barColor);
        for(int i: phases){
            drawRectangle(getX()+size*i/max-1, getY(), 2, height, bgColor);
        }*/
        //System.out.println(this+" "+(int)getX()+" "+(int)getY()+" "+size+" "+height+" "+bgColor);
        Raylib.drawRectanglePro(new Rectangle((float)renderTransformX((int)getX()), (float)renderTransformY((int)(getY()-getHeight())), renderOriginX(width), renderTransformY(height)), new Vector2(renderOriginX(width/2), renderTransformY(height/2)), (float)getRotation(), bgColor);
        Raylib.drawRectanglePro(new Rectangle((float)renderTransformX((int)getX()), (float)renderTransformY((int)(getY()-getHeight())), renderOriginX((int)(width*getPerc())), renderTransformY(height)), new Vector2(renderOriginX(width/2), renderTransformY(height/2)), (float)getRotation(), barColor);
        for(int i: phases){
            Raylib.drawRectanglePro(new Rectangle((float)(renderTransformX((int)getX()+width*i/max-1)), (float)renderTransformY((int)getY()), 2, renderTransformY(height)), new Vector2(renderOriginX(width/2), renderTransformY(height/2)), (float)getRotation(), bgColor);
        }
    }
    public double getBottom(){
        return height+getY();
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
