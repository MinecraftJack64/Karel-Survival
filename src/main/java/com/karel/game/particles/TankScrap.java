package com.karel.game.particles;

/*
 * Effect representing scrap ejected by IroncladZombie.
 * TODO: to be a similar type similar to SniperCasing
 */
public class TankScrap extends ProjectileParticle{
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
            getWorld().removeObject(this);
        }
    }
}
