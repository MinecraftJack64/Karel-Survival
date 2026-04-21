package com.karel.game.weapons.mc_sword;

import com.karel.game.GridEntity;

public class EnderPearl extends com.karel.game.gridobjects.hitters.FlyingProjectile{
    private boolean endermite;
    public EnderPearl(double rotation, double targetdistance, double height, com.karel.game.GridObject source, boolean endermite){
        super(rotation, targetdistance, height, source);
        this.endermite = endermite;
    }
    public void die(){
        getSource().pullTo(getX(), getY());
        explodeOnAll(50, 50);
        if(endermite){
            addObjectHere(new Endermite((GridEntity)getSource()));
        }
        super.die();
    }
}
