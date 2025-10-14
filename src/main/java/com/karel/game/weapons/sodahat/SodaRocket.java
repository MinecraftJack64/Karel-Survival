package com.karel.game.weapons.sodahat;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

public class SodaRocket extends Bullet {
    private boolean createPuddle;
    public SodaRocket(double dir, GridObject source, boolean isUpgrade){
        super(dir, source);
        setSpeed(15);
        setLife(25);
        setDamage(0);
        createPuddle = isUpgrade;
    }
    public void die(){
        explodeOn(100, 250);
        if(createPuddle)addObjectHere(new SodaPuddle(50, 100, 90, this));
        super.die();
    }
}
