package com.karel.game.gridobjects.gridentities.zombies.tractorbeam;

import com.karel.game.Sounds;
import com.karel.game.ZombieClass;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;

/**
 * Write a description of class TractorBeamZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TractorBeamZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.support, ZombieClass.controller};
    public String getStaticTextureURL(){
        return "tractorzareln.png";
    }
    private static double attackrange = 700;
    public TractorBeamZombie()
    {
        scaleTexture(60, 60);
        setSpeed(1);
        startHealth(300);
    }
    public void behave()
    {
        double monangle = face(getTarget(), canMove());
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else{
            fire();
        }
    }
    private void fire() 
    {
        if (canAttack()){
            explodeOn(800, "enemy", (g)->{
                if(Math.abs(face(g, false)-getRotation())<40){
                    g.pullTowards(this, 1);
                    if(distanceTo(g)<200)damage(g, 2);
                }
            }, null);
            Sounds.play("tractor");
        }
    }
    @Override
    public int getXP(){
        return 1000;
    }
    @Override
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    public String getName(){
        return "Tractor Beam Zombie";
    }
}
