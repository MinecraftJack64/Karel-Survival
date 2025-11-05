package com.karel.game.weapons.flashdrive;

import com.karel.game.Dropper;
import com.karel.game.GridEntity;

/**
 * Used by Flash Drive weapon
 * 
 * @author Poul Henriksen
 */
public class ICDropper extends Dropper
{
    public ICDropper(double rotation, double targetdistance, double height, GridEntity source, boolean upgraded)
    {
        super(rotation, targetdistance, height, new IntegratedCircuit(source, upgraded), source);
    }
}
