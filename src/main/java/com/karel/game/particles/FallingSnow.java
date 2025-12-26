package com.karel.game.particles;

import com.karel.game.Greenfoot;
import com.karel.game.KActor;

public class FallingSnow extends KActor
{
    private double direction, speed, fall, rotSpeed;
    private int cooldown;
    public FallingSnow() 
    {
        setImage("white-draught.png");
        scaleTexture(Greenfoot.getRandomNumber(5)+5);
        setHeight(1200);
        rotSpeed = Greenfoot.getRandomNumber(5)*(Greenfoot.getRandomNumber(2)==1?1:-1);
        fall = Greenfoot.getRandomNumber(4)+2;
        newDirection();
    }
    public void update(){
        super.update();
        setHeight(getHeight()-fall);
        move(direction, speed);
        cooldown--;
        if(cooldown==0){
            newDirection();
        }
        setRotation(getRotation()+rotSpeed);
        if(getHeight()<=0){
            getWorld().removeObject(this);
        }
    }
    public void newDirection(){
        cooldown = Greenfoot.getRandomNumber(55)+20;
        direction = Greenfoot.getRandomNumber(360);
        speed = Greenfoot.getRandomNumber()*2+0.5;
    }
    
}
