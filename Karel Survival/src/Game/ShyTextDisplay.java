package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)


import java.awt.Graphics;

/**
 * ShyTextDisplay that displays a text and number.
 * 
 * @author Michael Kolling
 * @version 1.0.1
 */
public class ShyTextDisplay extends TextDisplay
{
    //private TextDisplay renderer;
    int cooldown = 0;
    public ShyTextDisplay(String text, int size, Color c)
    {
        super(text, size, c);
        setVisible(false);
    }
    
    public void setVisible(boolean b){
        if(b){
            super.setVisible(b);
            cooldown = 600;
        }else{
            super.setVisible(b);
        }
    }
    public void act(){
        super.act();
        if(cooldown>=0)cooldown--;
        if(cooldown==0){
            setVisible(false);
        }
    }
}
