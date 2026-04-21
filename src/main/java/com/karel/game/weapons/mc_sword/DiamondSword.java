package com.karel.game.weapons.mc_sword;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Melee;
import com.karel.game.effects.BurnEffect;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class DiamondSword extends Melee
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int life = 15;
    private double degtotarg, radius;
    private boolean clockwise;
    private int enchantment;
    
    public DiamondSword(double rotation, double strength, int enchantment, boolean dir, GridObject source)
    {
        super(rotation, source);
        setImage("Weapons/blade/proj.png");
        scaleTexture(70);
        setSpeed(15);
        setLife(life);
        setDamage((int)(300+strength*50)+(enchantment==1?75:0));
        setNumTargets(-1);
        degtotarg = rotation+(dir?180:0);
        this.enchantment = enchantment;
        clockwise = dir;
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
            if(clockwise)degtotarg+=12;
            else degtotarg-=12;
            checkHit();
        }
    }
    public void doHit(GridEntity targ){
        if(enchantment==2){
            targ.applyEffect(new BurnEffect(50, 30, 2, this));
        }else if(enchantment==3){
            targ.knockBack(getRotation(), 50, 30, this);
        }
        super.doHit(targ);
    }
}
