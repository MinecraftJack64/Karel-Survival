package com.karel.game.gridobjects.gridentities.zombies.mimic;

import com.karel.game.GridEntity;
import com.karel.game.weapons.Weapon;
import com.karel.game.Player;
import com.karel.game.gridobjects.hitters.Bullet;
import com.karel.game.ItemHolder;

public class Mimicker extends Bullet {
    private Weapon weapon;
    public Mimicker(GridEntity t, MimicZombie mz){
        super(0, mz);
        setSpeed(12);
        setSelfHarm(true);
        setAggression(false);
        try{
            weapon = t instanceof Player p?(p.getHeldItem() instanceof Weapon w?w:null):null;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void applyPhysics(){
        setDirection(face(getSource(), true));
        setLife(2);
        super.applyPhysics();
    }
    public void doHit(GridEntity targ){
        if(targ==getSource()){
            if(targ instanceof MimicZombie mz){
                try{
                    weapon = weapon.getClass().getDeclaredConstructor(ItemHolder.class).newInstance(mz.getHand());
                }catch(Exception e){
                    e.printStackTrace();
                }
                mz.notifyHit(weapon);
            }
        }
        super.doHit(targ);
    }
}
