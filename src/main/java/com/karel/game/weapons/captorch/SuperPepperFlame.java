package com.karel.game.weapons.captorch;

import com.karel.game.Bullet;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.PoisonEffect;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class SuperPepperFlame extends Bullet
{
    private CapsaicinTorch torch;
    
    public SuperPepperFlame(double rotation, GridObject source, CapsaicinTorch toNotify)
    {
        super(rotation, source);
        setImage("Weapons/captorch/projUlt.png");
        scaleTexture(50);
        setRealRotation(getRealRotation()-180);
        setNumTargets(-1);
        torch = toNotify;
    }
    
    public void doHit(GridEntity targ){
        targ.applyEffect(new PoisonEffect(1,1,7,this));
        super.doHit(targ);
        torch.notifyHit();
    }
}
