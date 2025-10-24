package com.karel.game.gridobjects.gridentities.zombies.cop;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Melee;
import com.karel.game.effects.SilenceEffect;

public class Handcuffs extends Melee
{
    private int life = 15;
    private double degtotarg, radius;
    
    public Handcuffs(double rotation, GridObject source)
    {
        super(rotation, source);
        setImage("Projectiles/Melee/handcuffs.png");
        scaleTexture(70);
        setSpeed(15);
        setLife(life);
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
    public void doHit(GridEntity targ){
        targ.applyEffect(new SilenceEffect(150, this));
        life = 0;
    }
}
