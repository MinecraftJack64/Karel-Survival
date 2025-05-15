package com.karel.game;
import java.util.ArrayList;

/**
 * Write a description of class LymphCannon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LymphCannon extends Weapon
{
    private static final int gunReloadTime = 20;//100 damage default, 75 damage weak, 150 damage strong
    private int reloadDelayCount;
    private static final int ult = 550;//7 shots including default
    private Class target;
    ArrayList<CellTurret> mycells = new ArrayList<CellTurret>();
    public void fire(){//one full ammo deals 350 damage
        if (reloadDelayCount >= gunReloadTime) 
        {
            Antibody bullet = new Antibody(getHand().getTargetRotation(), target, this, getHolder());
            getHolder().addObjectHere(bullet);
            //bullet.move ();
            Sounds.play("fireworkshoot");
            reloadDelayCount = 0;
        }
    }
    public void notifyHit(Class targ){
        target = targ;
    }
    public void fireUlt(){
        CellTurret bullet = new CellTurret(getHand().getTargetX(), getHand().getTargetY(), target, getHolder());
        getHolder().addObjectHere(bullet);
        mycells.add(bullet);
        target = null;
        Sounds.play("lassoshoot");
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        reloadDelayCount++;
        updateAmmo(Math.min(gunReloadTime, reloadDelayCount));
        for(int i = mycells.size()-1; i >= 0; i--){
            if(!mycells.get(i).isDead()){
                if(mycells.get(i).readyToTransform(target)){
                    if(!getHand().isMoving()){
                        mycells.get(i).setNewTarget(target);
                    }
                }
            }else{
                mycells.remove(i);
            }
        }
    }
    public LymphCannon(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        newAmmo(gunReloadTime, reloadDelayCount);
    }
    public String getName(){
        return "Lymph Cannon";
    }
    public int getRarity(){
        return 2;
    }
}






