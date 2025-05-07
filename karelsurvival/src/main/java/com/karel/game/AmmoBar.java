package com.karel.game;

/**
 * Write a description of class AmmoBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AmmoBar extends StatusBar
{
    public AmmoBar(int ammo, int max, int size, int height)
    {
        super(ammo, max, size, height, Color.ORANGE.darker());
    }
    public AmmoBar(int ammo, int max, int size, int height, int phases)
    {
        super(ammo, max, size, height, Color.ORANGE.darker());
        divideIntoPhases(phases);
    }
    public void disable(){
        setColor(Color.RED);
    }
    public void reset(){
        setColor(Color.ORANGE.darker());
    }
    public void setValue(int amt){
        super.setValue(amt);
        if(amt>getMax()){
            setColor(Color.BLUE.brighter());
        }else{
            setColor(Color.ORANGE.darker());
        }
    }
    /*public void update(){
        if(!getColor().equals(Color.ORANGE.darker()))setColor(Color.ORANGE.darker());
        else super.update();
    }*/
}
