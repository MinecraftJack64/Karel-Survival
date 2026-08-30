package com.karel.game.gridobjects.gridentities.zombies.watermelon;

import com.karel.game.ArmorShield;
import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.particles.MelonExplosion;
import com.karel.game.shields.ShieldID;
import com.raylib.Texture;

/**
 * Write a description of class WatermelonZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WatermelonZombie extends Zombie
{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.assault, ZombieClass.tank};
    private static final int gunReloadTime = 2;
    public String getStaticTextureURL(){return "melonzareln.png";}

    private Texture rocket = Greenfoot.loadTexture(spriteOrigin()+"WatermelonZombie/0.png");
    private Texture rocket2 = Greenfoot.loadTexture(spriteOrigin()+"melonzareln.png");
    private double ammo = 0;
    private static double speed = 2;
    private static final int ult = 750;
    private int ultcharge = 750;
    private int wave = 0;
    private boolean inShieldPhase = true;
    private ShieldID wmshield = new ShieldID(this);

    private int animFrame;
    public WatermelonZombie()
    {
        setImage(rocket2);
        setSpeed(speed);
        startHealth(300);
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 450;
    }
    public void behave(){
        if(!hasShield(wmshield)){
            if(ultcharge>=ult&&canAttack()&&(getPercentHealth()<0.5||distanceTo(getTarget())>70)){
                fireUlt();
            }
        }
        if(hasShield(wmshield)){
            double ang = face(getTarget(), canMove());
            walk(ang, 1);
            if(distanceTo(getTarget())<40){//unaffected by canAttack()
                hit(getShield().getHealth(), this);//intended
                damage(getTarget(), 200);
            }
        }else{
            double ang = face(getTarget(), canMove());
            if(distanceTo(getTarget())>250){
                walk(ang, 1);
            }
            if(ammo>=gunReloadTime&&canAttack()){
                ammo = 0;
                fire();
            }else{
                ammo+=getReloadMultiplier();
            }
        }
        if(inShieldPhase&&!hasShield(wmshield)){
            inShieldPhase = false;
            setSpeed(2);
            setImage(rocket2);
            for(int i = 0; i < 360; i+=60){
                MelonExplosion me = new MelonExplosion(i+getRotation()+Greenfoot.getRandomNumber(80)-40);
                addObjectHere(me);
            }
        }
    }
    public void animate(){
        if(inShieldPhase){
            setImage("WatermelonZombie/"+(animFrame/3)+".png");
            animFrame++;
            if(animFrame>8){
                animFrame = 0;
            }
        }
        super.animate();
    }
    @Override
    public boolean prioritizeTarget(){
        return hasShield(wmshield);
    }
    public void fireUlt(){
        heal(this, (int)(0.40*(getMaxHealth()-getHealth())));//heal 40% of missing health
        applyShield(new ArmorShield(wmshield, 800));
        setSpeed(4);
        inShieldPhase = true;
        setImage(rocket);
        ultcharge = 0;
    }
    public void fire(){
        ZWatermelonSeed bullet = new ZWatermelonSeed(getRotation()+10*Math.sin(wave*Math.PI/4), this);
        addObjectHere(bullet);
        wave = (wave+1)%8;
    }
    public void notifyDamage(GridEntity source, int amt){
        if(source!=this)ultcharge = Math.min(ultcharge+amt, ult);
    }
    
    public String getName(){
        return "Watermelon Zombie";
    }
    @Override
    public String getZombieID(){
        return "watermelon";
    }
}
