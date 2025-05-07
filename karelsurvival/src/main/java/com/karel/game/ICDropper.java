package com.karel.game;
import java.util.List;

/**
 * Used by Flash Drive weapon
 * 
 * @author Poul Henriksen
 */
public class ICDropper extends Dropper
{
    public ICDropper(double rotation, double targetdistance, double height, GridEntity source)
    {
        super(rotation, targetdistance, height, new IntegratedCircuit(source), source);
    }
}
