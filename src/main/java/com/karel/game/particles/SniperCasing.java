package com.karel.game.particles;

/*
 * Effect representing the bullet casing ejected by a MarksmanZombie.
 * TODO: Like MelonExplosion, to be unsaveable
 */
public class SniperCasing extends ProjectileParticle{
    double direction;
    private int life;
    public SniperCasing(double direction){
        setImage("Projectiles/Bullets/casing.png");
        this.direction = direction;
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
