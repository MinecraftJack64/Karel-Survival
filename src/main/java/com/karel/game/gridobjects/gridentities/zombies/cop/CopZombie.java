package com.karel.game.gridobjects.gridentities.zombies.cop;

import com.karel.game.GridObject;
import com.karel.game.Sounds;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;

public class CopZombie extends Zombie {
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.controller};
    private static final int gunReloadTime = 125;         // The minimum delay between firing the gun.

    private double reloadDelayCount;               // How long ago we fired the gun the last time.
    private double handcuffAmmo;
    private double batonAmmo;
    private TaserNode prong;
    private GridObject baton, handcuffs;

    public String getStaticTextureURL(){return "copzareln.png";}
    private static double attackrange = 400, retreatrange = 390;
    private static double meleerange = 100;
    /**
     * Initilise this rocket.
     */
    public CopZombie()
    {
        reloadDelayCount = 5;
        setSpeed(4);
        startHealth(400);
    }
    public void behave()
    {
        double monangle = face(getTarget(), canMove());
        if(readyToFire()){
            reloadDelayCount+=getReloadMultiplier();
            if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
            else if(distanceTo(getTarget())<retreatrange){fire();walk(monangle, -1);}
            else{
                fire();
            }
        }else{
            if(distanceTo(getTarget())>meleerange-10)walk(monangle, 1);
        }
        if(distanceTo(getTarget())<meleerange){
            if(handcuffAmmo>150&&!readyToFire()){
                handcuffAmmo = 0;
                handcuffs = addObjectHere(new Handcuffs(getRotation(), this));
            }
            else if(batonAmmo>=30&&!readyToFire()){
                batonAmmo = 0;
                baton = addObjectHere(new Baton(getRotation(), this));
            }
        }
        batonAmmo++;
        handcuffAmmo++;
    }
    private boolean readyToFire() {
        return (prong==null||!prong.isInWorld())&&(baton==null||!baton.isInWorld())&&(handcuffs==null||!handcuffs.isInWorld());
    }
    public void fire() 
    {
        if (reloadDelayCount>=gunReloadTime&&canAttack()){
            TaserNode tn = new TaserNode(getRotation()+4, this);
            addObjectHere(tn);
            TaserNode tn2 = new TaserNode(getRotation()-4, this);
            addObjectHere(tn2);
            tn.setOther(tn2);
            tn2.setOther(tn);
            prong = tn;
            Sounds.play("gunshoot");
            reloadDelayCount = 0;
        }
    }
    @Override
    public int getXP(){
        return 50;
    }

    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    
    public String getName(){
        return "Cop Zombie";
    }
    @Override
    public String getZombieID(){
        return "cop";
    }
}
