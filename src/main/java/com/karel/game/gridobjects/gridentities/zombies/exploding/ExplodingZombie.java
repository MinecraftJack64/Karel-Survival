package com.karel.game.gridobjects.gridentities.zombies.exploding;

import com.karel.game.GridObject;
import com.karel.game.effects.BurnEffect;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.gridobjects.gridentities.zombies.arson.FirePuddle;
import com.karel.game.particles.Explosion;

/**
 * Write a description of class ExplodingZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ExplodingZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.assault};
    public String getStaticTextureURL(){return "tntzareln.png";}
    private int damage = 400;
    public ExplodingZombie()
    {
        setSpeed(4);
        startHealth(125);
    }
    public void behave()
    {
        double monangle = face(getTarget(), canMove());
        if(distanceTo(getTarget())>55)walk(monangle, 1);
        else{
            if(canAttack())kill(this);
        }
    }

    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 200;
    }
    @Override
    public boolean prioritizeTarget(){
        return true;
    }
    public void die(GridObject source){
        try{
            //explode if not stunned
            super.die(source);
            explodeOn(60, (e)->{
                damage(e, damage);
                e.knockBack(face(e, false), 50, 30, this);
            }, new Explosion(1));
            if(hasEffect(BurnEffect.class)){
                addObjectHere(new FirePuddle(this));
            }
            playSound("Zombies/exploding/explode.wav");
        }catch(Exception e){
        }
    }
    public String getName(){
        return "Exploding Zombie";
    }
}
