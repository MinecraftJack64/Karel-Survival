package com.karel.game.particles;

import com.karel.game.Greenfoot;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.collectibles.WeaponFrag;

public class WeaponFragComponent extends GridObject{
    private WeaponFrag center;
    private GridObject finalTarget;
    public WeaponFragComponent(WeaponFrag center){
        this.center = center;
        setImage("Objects/WeaponFrags/"+Greenfoot.getRandomNumber(8)+".png");
    }
    public void update(){
        super.update();
        if(center!=null&&(!center.isInWorld()||center.collected())){
            finalTarget = center.getCollector();
            center = null;
        }
        if(finalTarget!=null){
            move(face(finalTarget, false), 9);
            if(distanceTo(finalTarget)<9){
                die();
                getWorld().removeObject(this);
            }
        }
        else if(distanceTo(center)>30){
            move(face(center, false), 9);
        }
    }
}
