package com.karel.game.ui.buttons;

import com.karel.game.Sounds;
import com.karel.game.ui.Overlay;
import com.karel.game.Game;
import com.raylib.Color;
import com.raylib.Raylib;

/**
 * Write a description of class Button here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Button extends Overlay
{
    private Color barColor = Raylib.GRAY;
    private int size = 0;
    private int width, height;
    private String text;
    private Color textColor = Raylib.GREEN;
    private int state = 0;//-1 - inactive, 0 - normal, 1 - mouse over, 2 - mouse clicked
    private boolean active = true;
    public Button()
    {
        this(80, 40);
    }
    
    public Button(int size, int height)
    {
        this.width = size;
        this.height = height;
    }
    public Button(int size, int height, String text, Color c)
    {
        this.width = size;
        this.height = height;
    }
    public Button(int size, int height, Color c)
    {
        this(size, height);
        barColor = c;
    }
    public void setText(String s){
        text = s;
    }
    
    public void update() {
        //test if mouse over
        int nstate = state;
        if(active){
            if(nstate==2&&!Game.isMouseDown()){
                Sounds.play("click");
                click();
                nstate = 1;
            }
            if(isMouseOver()){
                nstate = 1;
                if(Game.isMouseDown()){
                    nstate = 2;
                }
            }else{
                nstate = 0;
            }
        }else{
            nstate = -1;
        }
        if(nstate!=state){
            state = nstate;
            update();
        }
    }
    public boolean isMouseOver(){
        double mxx = Game.getMouseX()-getRealX()+width/2, myy = Game.getMouseY()-getRealY()+height/2;
        //System.out.println(mxx+" "+myy);
        return (mxx>=0&&mxx<=width)&&(myy>=0&&myy<=height);
    }
    public void setColor(Color c){
        barColor = c;
        update();
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
        /*GreenfootImage image = getImage();
        image.clear();
        if(state<0){
            image.setColor(barColor.brighter());
        }else if(state>0){
            image.setColor(barColor.darker());
        }else{
            image.setColor(barColor);
        }
        if(state==2){
            image.fillRect(5, 5, width-10, height-10);
        }else{
            image.fillRect(0, 0, width, height);
        }
        TODO*/
    }
    public double getBottom(){
        return size+getRealY();
    }
    public void setActive(boolean s){
        active = s;
    }
}
