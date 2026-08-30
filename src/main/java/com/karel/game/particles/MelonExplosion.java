package com.karel.game.particles;

/*
 * Effect of melon shield breaking created by WatermelonZombie
 * TODO: move to KActor so it doesn't get saved, or mark as non saving
 */
public class MelonExplosion extends ProjectileParticle{
    double direction;
    private int life;
    public MelonExplosion(double direction){
        setImage("Projectiles/Bullets/melon.png");
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
