package com.karel.game.weapons.lymphcannon;
import java.util.ArrayList;

import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class LymphCannon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LymphCannon extends Weapon
{
    private static final int gunReloadTime = 20;//100 damage default, 75 damage weak, 150 damage strong
    private double reloadDelayCount;
    private static final int ult = 550;//7 shots including default
    private String target;
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
    public void notifyHit(String targ){
        target = targ;
    }
    public void fireUlt(){
        CellTurret bullet = new CellTurret(getHand().getTargetX(), getHand().getTargetY(), target, getHolder());
        getHolder().addObjectHere(bullet);
        mycells.add(bullet);
        target = null;
        Sounds.play("lassoshoot");
    }
    public void onGadgetActivate(){
        for(CellTurret c: mycells){
            getHolder().addObjectHere(new Cytokine(c, target));
        }
        setGadgetTimer(180);
    }
    public int defaultGadgets(){
        return 1;
    }
    public int getUlt(){
        return ult;
    }
    public void reload(double r){
        reloadDelayCount+=r;
        updateAmmo(Math.min(gunReloadTime, (int)reloadDelayCount));
        super.reload(r);
    }
    public void update(){
        super.update();
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
        newAmmo(gunReloadTime, (int)reloadDelayCount);
    }
    public String getName(){
        return "Lymph Cannon";
    }
    public int getRarity(){
        return 2;
    }
}






