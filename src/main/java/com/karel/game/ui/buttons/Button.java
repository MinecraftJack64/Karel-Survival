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
    private int shrinkAmount = 5;
    private String text = "";
    private int textOffsetY = 0;
    private boolean showText = true, showBackground = true;
    private boolean cancelClickOnUnhover = true;
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
    public String getText(){
        return text;
    }
    public void setTextOffsetY(int offsetY){
        textOffsetY = offsetY;
    }
    public void setShowText(boolean showText){
        this.showText = showText;
    }
    public void setShowBackground(boolean s){
        this.showBackground = s;
    }
    public void setCancelClickOnUnhover(boolean b){
        cancelClickOnUnhover = b;
    }
    public int getState(){
        return state;
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
                if(nstate!=0&&Game.isMouseDown()){
                    if(nstate==1)onPress();
                    nstate = 2;
                }else if(!Game.isMouseDown()){
                    nstate = 1;
                }else{
                    nstate = 0;
                }
            }else{
                if(!cancelClickOnUnhover&&nstate==2&&Game.isMouseDown())nstate = 2;
                else nstate = 0;
            }
        }else{
            nstate = -1;
        }
        if(nstate!=state){
            state = nstate;
        }
        if(state>0){
            Game.disableGameInputFlag = true;
        }
    }
    public boolean isMouseOver(){
        double mxx = getWorld().getMouseX()-getX()+width/2, myy = getWorld().getMouseY()-getY()+height/2;
        return (mxx>=0&&mxx<=width)&&(myy>=0&&myy<=height);
    }
    public void setColor(Color c){
        barColor = c;
        update();
    }
    public void click(){
        //notify observer
    }
    public void onPress(){
        //WHen button is first clicked on
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
        int shrink = state==2?shrinkAmount:0;
        if(showBackground)Raylib.drawRectangle(renderTransformX((int)(getX()-width/2+shrink)), renderTransformY((int)(getY()-height/2+shrink)), renderOriginX(width-shrink*2), renderTransformY(height-shrink*2), c);
        int x = renderTransformX((int)getX()-Raylib.measureText(text, size)/2);
        int y = renderTransformY((int)getY()-Raylib.getFontDefault().getBaseSize()*(size/10)/2-textOffsetY);
        if(showText) Raylib.drawText(text, x, y, (int)(size*getWorld().getScreenScale()), textColor);
    }
    public double getBottom(){
        return height+getY();
    }
    public int getSizeWidth(){
        return width;
    }

    public int getSizeHeight(){
        return height;
    }

    public void setActive(boolean s){
        active = s;
    }
}
