package com.karel.game.ui.text;


import com.raylib.Color;

public class FadeInTextDisplay extends TextDisplay
{
    private int speed = 5; // Speed of fade-in effect
    private int op = 0;
    public FadeInTextDisplay(String text, int size, Color c)
    {
        super(text, size, c);
        getColor().setA((byte)op);
        speed = 3;
    }
    public FadeInTextDisplay(String text, int size, Color c, int speed)
    {
        this(text, size, c);
        this.speed = speed;
    }
    public void update(){
        super.update();
        if(op < 255){
            op+=speed;
            if(op>255){
                op = 255;
            }
            getColor().setA((byte)op);
        }
    }
}
