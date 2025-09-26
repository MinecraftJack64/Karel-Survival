package com.karel.game.gridobjects.gridentities.zombies.cop;
import com.karel.game.GridObject;
import com.karel.game.Melee;

public class Baton extends Melee
{
    private int life = 15;
    private double degtotarg, radius;
    
    public Baton(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Weapons/blade/proj.png");
        scaleTexture(70);
        setSpeed(15);
        setLife(life);
        setDamage(250);
        setNumTargets(-1);
        degtotarg = rotation+180;
        this.radius = 110;
    }
    
    public void applyPhysics(){
        if(life <= 0) {
            expire();
        } 
        else {
            life--;
            double centerx = getSource().getX(), centery = getSource().getY();
            setLocation(centerx+radius*Math.cos(degtotarg*Math.PI/180), centery+radius*Math.sin(degtotarg*Math.PI/180));
            setRotation(degtotarg+45);
            degtotarg+=12;
            checkHit();
        }
    }
}
