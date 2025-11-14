package com.karel.game.weapons.chameleon;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Sounds;
import com.karel.game.gridobjects.collectibles.Collectible;

/**
 * a collectible dropped by the Chameleon weapon when the player kills a zombie by pulling them, changes the Chameleon's color when collected by the player
 * 
 * @author MinecraftJack64
 * @version 1.0
 */
public class ChameleonOrb extends Collectible
{
    private int healing = 200;
    private int color;
    private Chameleon mycham;
    public ChameleonOrb(Chameleon mycham, int color)
    {
        this.mycham = mycham;
        this.color = color;
        setCooldown(15);
        setImage("lightKaro.png");
        tintPlayer();
    }
    public void tintPlayer(){
        switch(color){
            case 0:
                setTint(255, 0 ,0);
                break;
            case 1:
                setTint(255, 128, 0);
                break;
            case 2:
                setTint(255, 255, 0);
                break;
            case 3:
                setTint(0, 255, 0);
                break;
            case 4:
                setTint(0, 0, 255);
                break;
            case 5:
                setTint(128, 0, 255);
                break;
        }
    }
    public GridObject getTarget(){
        return mycham.getHolder();
    }
    public void collect(GridObject targ){
        mycham.notifyColorChange(color, healing, (GridEntity)targ);
        super.collect(targ);
        Sounds.play("ChameleonOrb.collect");
    }
}
