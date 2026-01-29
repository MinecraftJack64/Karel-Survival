package com.karel.game.weapons.lanternlobber;

import com.karel.game.GridObject;

public class DragonFood extends GridObject {
    private boolean onGround;
    public DragonFood(){
        setImage("karelnOff.png");
        scaleTexture(50, 50);
    }
    public boolean dropped(){
        return onGround;
    }
    public void drop(){
        onGround = true;
    }
    public void die(){
        super.die();
        getWorld().removeObject(this);
    }
}
