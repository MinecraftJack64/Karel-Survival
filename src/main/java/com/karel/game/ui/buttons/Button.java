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
    private Color barColor = Raylib.GRAY, barDisabledColor = Raylib.LIGHTGRAY, barHoverColor = Raylib.DARKGRAY;
    private int width, height;
    private String text = "";
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
        this.text = text;
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
        }
    }
    public boolean isMouseOver(){
        double mxx = getWorld().getMouseX()-getRealX()+width/2, myy = getWorld().getMouseY()-getRealY()+height/2;
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
        Color c = barColor;
        if(state<0){
            c = barDisabledColor;
        }else if(state>0){
            c = barHoverColor;
        }
        int size = width/5;
        Raylib.drawRectangle(renderTransformX((int)(getRealX()-width/2)), renderTransformY((int)(getRealY()-height/2)), renderOriginX(width), renderTransformY(height), c);
        int x = renderTransformX((int)getRealX()-Raylib.measureText(text, size)/2);
        int y = renderTransformY((int)getRealY()-Raylib.getFontDefault().getBaseSize()*(size/10)/2);
        Raylib.drawText(text, x, y, (int)(size*getWorld().getScreenScale()), textColor);
    }
    public double getBottom(){
        return height+getRealY();
    }
    public void setActive(boolean s){
        active = s;
    }
}
