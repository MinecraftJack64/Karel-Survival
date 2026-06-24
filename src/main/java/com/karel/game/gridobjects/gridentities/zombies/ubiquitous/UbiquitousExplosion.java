package com.karel.game.gridobjects.gridentities.zombies.ubiquitous;

import com.karel.game.particles.Explosion;

public class UbiquitousExplosion extends Explosion {
    int frame = 0;
    public UbiquitousExplosion(double size) 
    {
        setImage("button-red.png");
        scaleTexture((int)size*2);
        setOpacity(0);
        //Greenfoot.playSound("MetalExplosion.wav");
    }
    public UbiquitousExplosion(){
        this(10);
    }
    public void applyPhysics(){
        frame++;
        setOpacityPercent(0.25/(Math.abs(3-frame)+1));
        if(frame>6){
            die();
        }
    }
}
