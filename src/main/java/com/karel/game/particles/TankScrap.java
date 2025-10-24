package com.karel.game.particles;

import com.karel.game.GridObject;

public class TankScrap extends GridObject{
    double direction;
    private int life;
    public TankScrap(double direction){
        setImage("Projectiles/Bullets/scrap.png");
        this.direction = direction;
        scaleTexture(30);
        life = 8;
    }
    public void update(){
        life--;
        setRotation(getRotation()+30);
        move(direction, 15);
        setOpacity(getOpacity()-20);
        super.update();
        if(life<=0){
            die();
            getWorld().removeObject(this);
        }
    }
}
