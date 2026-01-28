package com.karel.game.weapons.lanternlobber;

import com.karel.game.GridObject;

public class DragonFood extends GridObject {
    public DragonFood(){
        setImage("karelnOff.png");
        scaleTexture(50, 50);
    }
    public void die(){
        super.die();
        getWorld().removeObject(this);
    }
}
