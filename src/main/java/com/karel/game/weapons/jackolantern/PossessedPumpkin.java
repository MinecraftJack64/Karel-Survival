package com.karel.game.weapons.jackolantern;

import com.karel.game.GridObject;
import com.karel.game.effects.PullEffect;
import com.karel.game.gridobjects.hitters.Hitter;

public class PossessedPumpkin extends Hitter {
    private int ammo, cooldown; // 15
    private int rot;
    private boolean upgraded;
    public PossessedPumpkin(GridObject source, boolean upgraded){
        super(source);
        ammo = 12;
        this.upgraded = upgraded;
    }
    public void update(){
        super.update();
        if(getNearestTarget()!=null)face(getNearestTarget(), true);
        cooldown--;
        if(cooldown<=0){
            cooldown = 15;
            ammo--;
            //shoot wave
            PumpkinGhost center = new PumpkinGhost(getRotation()+rot, this);
            addObjectHere(center);
            if(upgraded){
                PumpkinGhost center2 = new PumpkinGhost(getRotation()+rot+180, this);
                addObjectHere(center2);
            }
            rot+=120;
            if(ammo<=0){
                die();
            }
        }
    }
    public void notifyWorldAdd(){
        super.notifyWorldAdd();
        explodeOnEnemies(200, (e)->{
            e.applyEffect(new PullEffect(face(e, false), 20, 5, this));
        });
    }
    public void die(){
        super.die();
        getWorld().removeObject(this);
    }
}
