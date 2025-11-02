package com.karel.game.weapons.jackolantern;

import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Hitter;

public class SmashedJack extends Hitter {
    private int ammo, cooldown; // 30
    private boolean upgraded;
    public SmashedJack(GridObject source, boolean upgraded){
        super(source);
        ammo = 1;
        this.upgraded = upgraded;
    }
    public SmashedJack(GridObject source, int a){
        super(source);
        ammo = a;
    }
    public void update(){
        super.update();
        setRotation(getSource().getRotation());
        cooldown--;
        if(cooldown<=0){
            cooldown = 30;
            ammo--;
            //shoot wave
            PumpkinWave center = new PumpkinWave(getRotation(), this);
            addObjectHere(center);
            for(int i = -9; i < 9; i+=3){
                PumpkinWave w = new PumpkinWave(getRotation()+i, this);
                w.setHitStory(center.getHitStory());
                addObjectHere(w);
            }
            if(upgraded){
                addObjectHere(new PumpkinSeeds(getSource()));
            }
            if(ammo<=0){
                die();
            }
        }
    }
    public boolean covertDamage(){
        return true;
    }
    public void die(){
        super.die();
        getWorld().removeObject(this);
    }
}
