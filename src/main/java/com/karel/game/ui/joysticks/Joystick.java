package com.karel.game.ui.joysticks;

import com.karel.game.Greenfoot;
import com.karel.game.ui.Overlay;
import com.raylib.Texture;

public class Joystick extends Overlay {
    private int size;
    private String type;
    private boolean shiftCenter = true; // if this joystick will recenter if activated
    private boolean isPressed;
    private double angle, distance;
    private double thumbSize;
    private Texture thumbTexture;
    private boolean hovered;
    private double oldX, oldY;
    private boolean unlocked;
    private boolean isActive = true;
    public Joystick(String typeid, int size){
        this.size = size;
        this.type = typeid;
        thumbSize = size*2;
        thumbTexture = Greenfoot.loadTexture("Controls/"+type+"-stick.png");
        setImage("Controls/"+type+".png");
        scaleTexture(size*2);
        setOpacity(70);
    }
    public void update(){
        super.update();
        if(!isPressed){
            if(Greenfoot.isMouseDown()&&isActive&&hovered&&size>=distanceTo(getWorld().getMouseX(), getWorld().getMouseY())){
                onPress();
            }
        }else{
            if(!Greenfoot.isMouseDown()||!isActive){
                onUnpress();
            }
        }
        if(!Greenfoot.isMouseDown()&&!isPressed)hovered = size>=distanceTo(getWorld().getMouseX(), getWorld().getMouseY());
        if(isPressed){
            setOpacity(127);
            //determine the branching of the input
            angle = face(getWorld().getMouseX(), getWorld().getMouseY(), false)-90;
            double realDistance = distanceTo(getWorld().getMouseX(), getWorld().getMouseY());
            distance = Math.min(size, realDistance);
            if(realDistance>size&&unlocked){
                branchOut(this, angle, realDistance-distance);
            }
        }else{
            setOpacity(70);
            angle = 0;
            distance = 0;
        }
    }
    public void onPress(){
        isPressed = true;
        oldX = getX();
        oldY = getY();
        if(shiftCenter){
            setLocation(getWorld().getMouseX(), getWorld().getMouseY());
        }
    }
    public void onUnpress(){
        isPressed = false;
        setLocation(oldX, oldY);
    }
    public void setLocked(boolean locked){
        unlocked = !locked;
    }
    public void setShiftCenter(boolean shift){
        shiftCenter = shift;
    }
    public double getPercentDistance(){
        return distance/size;
    }
    public double getAngle(){
        return angle;
    }
    public boolean isPressed(){
        return isPressed;
    }
    public void render(){
        super.render();
        renderTexture(thumbTexture, getX()+getBranchX(angle, distance), getY()+getBranchY(angle, distance), thumbSize, thumbSize, 0, isPressed?255:127);
    }
    public void setActive(boolean f){
        isActive = f;
    }
}
