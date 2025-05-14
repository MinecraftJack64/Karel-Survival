package com.karel.game;
import java.util.List;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class MolotovCocktail extends FlyingRock
{
    public MolotovCocktail(double rotation, double targetdistance, double height, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        setRange(100);
        setDamage(200);
    }
    public double getGravity(){
        return 1;
    }
    public void die(){
        addObjectHere(new FirePuddle(this));
        super.die();
    }
}
