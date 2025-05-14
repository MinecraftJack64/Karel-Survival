package com.karel.game;


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
        int d = Math.abs(value-target);
        int inc = 0;
        if(d<5){
            inc = 1;
        }else if(d<=100){
            inc = 5;
        }else if(d<=1000){
            inc = 50;
        }else{
            inc = (int)Math.pow(10, Math.log10(d));
        }
        if(value < target) {
            value+=inc;
            setText(prefix+value);
        }
        else if(value > target) {
            value-=inc;
            setText(prefix+value);
        }
        super.act();
    }
    public void setValue(int value){
        this.value = target = value;
        setText(prefix+value);
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
