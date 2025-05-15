package com.karel.game.ui.text;


import com.raylib.Color;
import com.raylib.Raylib;

import com.karel.game.ui.Overlay;

/**
 * TextDisplay that displays a text and number.
 * 
 * @author Michael Kolling
 * @version 1.0.1
 */
public class TextDisplay extends Overlay
{
    private Color textColor = Raylib.GREEN;
    private int size = 0;
    private String text;
    public static Color nothing = new Color((byte)0,(byte)0,(byte)0,(byte)0);

    public TextDisplay()
    {
        this("", 20);
    }
    
    public TextDisplay(String text, int size)
    {
        this.text = text;
        this.size = size;

        updateImage();
    }
    public TextDisplay(String text, int size, Color c)
    {
        textColor = c;
        this.text = text;
        this.size = size;

        updateImage();
    }
    
    public void update() {
        //TODO
    }
    public void setText(String ttxt){
        text = ttxt;
        update();
    }
    public String getText()
    {
        return text;
    }
    public void setColor(Color textColor){
        this.textColor = textColor;
        update();
    }
    public Color getColor(){
        return textColor;
    }

    /**
     * Make the image
     */
    private void updateImage()
    {
        /*GreenfootImage image = getImage();
        updateStringLength();
        if(image.getWidth()!=stringLength){
            image = newImage();
        }
        image.clear();
        image.drawString(text, 0, image.getFont().getSize());*/
        //setImage(new GreenfootImage(text, size, textColor, nothing));
        //TODO
    }
    public double getBottom(){
        return size+getRealY();
    }
}
