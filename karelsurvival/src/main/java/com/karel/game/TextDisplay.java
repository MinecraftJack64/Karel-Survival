package com.karel.game;


import java.awt.Graphics;

/**
 * TextDisplay that displays a text and number.
 * 
 * @author Michael Kolling
 * @version 1.0.1
 */
public class TextDisplay extends Overlay
{
    private Color textColor = Color.GREEN;
    private int size = 0;
    private String text;
    private int stringLength;
    public static Color nothing = new Color(0,0,0,0);

    public TextDisplay()
    {
        this("", 20);
    }
    
    public TextDisplay(String text, int size)
    {
        this.text = text;
        this.size = size;
        updateStringLength();

        newImage();

        updateImage();
    }
    public TextDisplay(String text, int size, Color c)
    {
        textColor = c;
        this.text = text;
        this.size = size;
        updateStringLength();

        newImage();

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
        GreenfootImage image = getImage();
        return image.getColor();
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
        setImage(new GreenfootImage(text, size, textColor, nothing));
    }
    private void updateStringLength(){
        stringLength = (text.length()+1) * 10*size/20;
    }
    private GreenfootImage newImage(){
        setImage(new GreenfootImage(stringLength, size));
        GreenfootImage image = getImage();
        image.setColor(textColor);
        image.setFont(new Font(size));
        return image;
    }
    public double getBottom(){
        return size+getRealY();
    }
}
