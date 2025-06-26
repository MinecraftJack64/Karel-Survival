package com.karel.game.gridobjects.gridentities.zombies.arson;

import com.karel.game.FlyingRock;
import com.karel.game.GridObject;
import com.karel.game.particles.FlameTrail;

public class MolotovCocktail extends FlyingRock
{
    public MolotovCocktail(double rotation, double targetdistance, double height, GridObject source)
    {
        super(rotation, targetdistance, height, source);
        setImage("Projectiles/Throws/molotov.png");
        scaleTexture(30);
        setRange(100);
        setDamage(200);
    }
    public void animate(){
        super.animate();
        setRealRotation(getRealRotation() + 30);
    }
    public void applyPhysics(){
        FlameTrail ft = new FlameTrail(1);
        addObjectHere(ft);
        ft.setRealHeight(getRealHeight());
        super.applyPhysics();
    }
    public double getGravity(){
        return 1;
    }
    public void die(){
        addObjectHere(new FirePuddle(this));
        super.die();
    }
}
