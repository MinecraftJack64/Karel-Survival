package com.karel.game.ui.buttons;

import com.karel.game.Greenfoot;
import com.raylib.Texture;

/**
 * Write a description of class Button here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ImageButton extends Button
{
    private String text = "";
    private Texture image;
    private double imageScale = 1.0; // 1 means the image covers the button exactly
    public ImageButton(int size, int height, String text)
    {
        super(size, height);
        setImageURL(text);
    }
    public ImageButton(int size, int height, String text, String tooltip)
    {
        this(size, height, text);
        if(tooltip != null && !tooltip.isEmpty()){
            setText(tooltip);
            setTextOffsetY(height/2+10);
        }
    }
    public void setImageURL(String s){
        text = s;
        image = Greenfoot.loadTexture(text);
    }
    public void click(){
        //notify observer
        System.out.println("Button clicked");
    }

    /**
     * Make the image
     */
    public void render()
    {
        setShowText(getState()>0);
        super.render();
        int width = getSizeWidth();
        int height = getSizeHeight();
        //System.out.println(image.toString()+getX()+" "+getY()+" "+(int)(width * imageScale)+" "+(int)(height * imageScale));
        renderTexture(image, getX(), getY(), (int)(width * imageScale), (int)(height * imageScale), getRotation(), 255);
    }
}
