package com.karel.game.weapons.snakecharmer;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

public class CharmNote extends Bullet {
    private static final int speed = 12, zaglife = 20;
    private boolean zigZag; //shot to left?
    private int damage = 150;
    public CharmNote(double rotation, GridObject source, boolean zigZag){
        super(rotation, source);
        setLife(zaglife*3);
        setSpeed(speed);
        setDamage(0);
        setNumTargets(5);
        this.zigZag = zigZag;
    }
    public void applyPhysics(){
        super.applyPhysics();
        if(getLife()%zaglife==0){
            if((getLife()/zaglife)%2==(zigZag?0:1)){
                setDirection(getDirection()+90);
            }else{
                setDirection(getDirection()-90);
            }
        }
    }
    public void doHit(GridEntity targ){
        explodeOn(40, damage);
        damage-=30;
    }
    public static double zagRange(){
        return speed*zaglife;
    }
}
