package com.karel.game.gridobjects.gridentities.zombies;

import com.karel.game.GridObject;
public class ZGenericBullet extends ZBullet
{
    
    public ZGenericBullet(int dmg, double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Projectiles/Bullets/jokerball.png");
        setSpeed(13);
        setLife(50);
        setDamage(dmg);
    }
    
    public void animate(){
        setRotation(getRotation()+20);
        super.animate();
    }
}
